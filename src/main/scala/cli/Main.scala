package cli

import java.io.File

import akka.stream._
import akka.stream.scaladsl._
import akka.{Done, NotUsed}
import akka.actor.{ActorSystem, Props}
import akka.util.ByteString

import scala.concurrent._
import scala.concurrent.duration._
import java.nio.file.{Files, Paths}

import akka.stream.scaladsl.Tcp.{IncomingConnection, ServerBinding}
import java.net.InetSocketAddress

import actors.{BackendMappersListener, PaxosListener}
import com.typesafe.config.ConfigFactory

import scala.collection.immutable.ListMap
import scala.collection.mutable
import scala.collection.parallel.immutable
import scala.io.Source
import scala.util.Try

case class MapMessage1(msg1: Array[String])
case class MapMessage2(msg2: Array[String])
case class ReduceMessage(msg1: String, msg2: String)
case class ReduceMessage2(files: List[String])
case class ReplicateMessage1(filename: String, msg1: Array[Byte])


//case class MapMessage2(msg2: List[String])


object Main extends App {
  val commands: Array[String] = args
  val host = "127.0.0.1"
  val port = 5001
  val mapperPort1 = 5002
  val mapperPort2 = 5003
  val reducerPort1 = 5004
  val paxosPort1 = 5005
  val paxosPort2 = 5006
  val paxosPort3 = 5007

  // TODO: PUT
  val config1 = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$mapperPort1").
    withFallback(ConfigFactory.parseString("akka.cluster.roles = [mappersbackend]")).
    withFallback(ConfigFactory.load("mapcluster.conf"))
  var system1 = ActorSystem("MappersCluster", config1)
  val mappers1 = system1.actorOf(Props[BackendMappersListener], name = "mapper1")

  val config2 = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$mapperPort2").
    withFallback(ConfigFactory.parseString("akka.cluster.roles = [mappersbackend]")).
    withFallback(ConfigFactory.load("mapcluster.conf"))
  var system2 = ActorSystem("MappersCluster", config2)
  val mappers2 = system2.actorOf(Props[BackendMappersListener], name = "mapper2")


  val config3 = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$reducerPort1").
    withFallback(ConfigFactory.parseString("akka.cluster.roles = [reducerbackend]")).
    withFallback(ConfigFactory.load("mapcluster.conf"))
  var system3 = ActorSystem("ReducersCluster", config3)
  val reducer1 = system3.actorOf(Props[BackendMappersListener], name = "reducer1")

  val config4 = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$paxosPort1").
    withFallback(ConfigFactory.parseString("akka.cluster.roles = [paxosbackend]")).
    withFallback(ConfigFactory.load("mapcluster.conf"))
  var system4 = ActorSystem("PaxosCluster", config4)
  val paxosNode1 = system4.actorOf(Props(new PaxosListener(1,paxosPort1)), name = "paxosnode1")

  val config5 = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$paxosPort2").
    withFallback(ConfigFactory.parseString("akka.cluster.roles = [paxosbackend]")).
    withFallback(ConfigFactory.load("mapcluster.conf"))
  var system5 = ActorSystem("PaxosCluster", config5)
  val paxosNode2 = system5.actorOf(Props(new PaxosListener(2,paxosPort1)), name = "paxosnode2")

  val config6 = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$paxosPort3").
    withFallback(ConfigFactory.parseString("akka.cluster.roles = [paxosbackend]")).
    withFallback(ConfigFactory.load("mapcluster.conf"))
  var system6 = ActorSystem("PaxosCluster", config6)
  val paxosNode3 = system6.actorOf(Props(new PaxosListener(3,paxosPort1)), name = "paxosnode3")

  /**
    * Data Processing Calls
//    */
  commands match {
    case Array("map", filename: String) => {
      //      val commands = args
      val wordsAsBigString = Source.fromFile(filename).getLines().mkString
      val wordsTokens = wordsAsBigString.split("\\s+").toArray

      val groupedWords = wordsTokens.grouped(wordsTokens.length/2).toList

      val mapper1Words = groupedWords.head
      val mapper2Words = groupedWords.last

      Thread.sleep(1000)
      mappers1 ! MapMessage1(mapper1Words)
      mappers2 ! MapMessage1(mapper2Words)

      val unique1 = mapper1Words.groupBy(identity).mapValues(_.length).toSeq.sortBy(- _._2)
      val unique2 = mapper2Words.groupBy(identity).mapValues(_.length).toSeq.sortBy(- _._2)

      println(s"Reading $filename\nmapped to::\t$unique1")

    }
    case Array("reduce", files @ _*) => {
      var fileReadList = List[String]()
      var mapDrain: Map[String, Int] = Map.empty //immutable
      var map: Map[String, Int] = Map.empty //immutable
      for (filename <- files) {
        val mappedWords1: String = Source.fromFile(filename).getLines().mkString
        val noWhiteSpace = mappedWords1.replaceAll(",\\b", "\n")
        fileReadList = fileReadList ++ List[String](noWhiteSpace)
        val linesForWords = noWhiteSpace.split("\n")
        for (line <- linesForWords) {
          val wordKey = line.split("\\s+")
          map += wordKey(0) -> Try(wordKey(1).toInt).getOrElse(0)
        }

        val orderedBufferMap = map.toSeq.sortBy(-_._2)

        mapDrain = map ++ mapDrain.map { case (k, v) => k -> (v + map.getOrElse(k, 0)) }

      }
      val orderUniqueMap = mapDrain.toSeq.sortBy(-_._2)
      println(s"Reduced to $orderUniqueMap")
      reducer1 ! ReduceMessage2(fileReadList)

    }

    case Array("replicate", filename: String) => {
      //      val commands = args
      val wordsAsBigString = Source.fromFile(filename).getLines().mkString
      val wordsTokens = wordsAsBigString.split("\\s+").toArray
      val byteArray: Array[Byte] = Files.readAllBytes(Paths.get(filename))

      Thread.sleep(1000)
      paxosNode1 ! ReplicateMessage1(filename, byteArray)
      paxosNode2 ! ReplicateMessage1(filename, byteArray)
      paxosNode3 ! ReplicateMessage1(filename, byteArray)

//      val unique1 = mapper1Words.groupBy(identity).mapValues(_.length).toSeq.sortBy(-_._2)
//      val unique2 = mapper2Words.groupBy(identity).mapValues(_.length).toSeq.sortBy(-_._2)

//      println("File1 Unique: " + unique1)
//      println(unique2)
      println("FIN - Used replicate command on Paxos")
    }



    case _ => println("error")
//    case s@ List("reduce", _*)=> {
//      s.last
//      ???
//  }
//      _.foreach()
//      val words = Source.fromFile(filename).getLines().mkString

    //    case List("replicate", _*) => ???
    //    case List("stop") => ???
    //    case List("resume") => ???
  }

  /**
    * Data Query Calls
    */
//  commands match {
//    case List("total", _*) => ???
//    case List("print") => ???
//    case List("merge", _, _) => ???
//  }

}

