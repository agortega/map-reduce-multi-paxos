package cli

import java.io.File

import akka.stream._
import akka.stream.scaladsl._
import akka.{Done, NotUsed}
import akka.actor.{ActorSystem, Props}
import akka.util.ByteString

import scala.concurrent._
import scala.concurrent.duration._
import java.nio.file.Paths

import akka.stream.scaladsl.Tcp.{IncomingConnection, ServerBinding}
import java.net.InetSocketAddress

import actors.BackendMappersListener
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

//case class MapMessage2(msg2: List[String])


object Main extends App {
  val commands: Array[String] = args
  val host = "127.0.0.1"
  val port = 5001
  val mapperPort1 = 5002
  val mapperPort2 = 5003
  val reducerPort1 = 5004

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
  val mappers2 = system1.actorOf(Props[BackendMappersListener], name = "mapper2")


  val config3 = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$reducerPort1").
    withFallback(ConfigFactory.parseString("akka.cluster.roles = [reducerbackend]")).
    withFallback(ConfigFactory.load("mapcluster.conf"))
  var system3 = ActorSystem("MappersCluster", config3)
  val reducer1 = system1.actorOf(Props[BackendMappersListener], name = "reducer1")

  // Create an actor that handles cluster domain events
  //      SimpleStart
  //      val app2 = ActorSystem("MyApp2",
  //        config.getConfig("myapp2").withOnlyPath("akka").withFallback(config))
  //      val actorSystem = ActorSystem("MappersCluster")
  //      actorSystem.actorOf(Props[BackendMappersListener]) ! "start"
  /**
    * Data Processing Calls
//    */
  commands match {
    case Array("map", filename: String) => {
      //      val commands = args
      val words = Source.fromFile(filename).getLines().mkString
      val wordStream: Stream[String] = Source.fromFile(new File(filename)).getLines.toStream
      val wordStream2: Stream[String]  = scala.io.Source.fromFile(new File(filename)).getLines.toStream
      val words1 = words.split("\\s+").toArray
      val splitFileLength = words1.length/2
      val groupedWords = words1.grouped(words1.length/2).toList
      val mapper1Words = groupedWords.head
      val mapper2Words = groupedWords.last
//      val msg1 = MapMessage1(mapper1Words)
      val unique1 = mapper1Words.groupBy(identity).mapValues(_.length).toSeq.sortBy(- _._2)
      val unique2 = mapper2Words.groupBy(identity).mapValues(_.length).toSeq.sortBy(- _._2)
//      val unique: Int = mapper1Words.groupBy(identity).mapValues(_.size)

      println("File1 Unique: " +unique1)
      println(unique2)

      Thread.sleep(1000)
//      val reponseFromMapper1 = mappers1 ! msg1

      mappers1 ! MapMessage1(mapper1Words)
      mappers2 ! MapMessage1(mapper2Words)

//      println(words1.le)
//      words1.foreach(println)

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
      println(orderUniqueMap)
      reducer1 ! ReduceMessage2(fileReadList)

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

