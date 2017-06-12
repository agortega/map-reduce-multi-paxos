package cli

import java.io.File

import akka.stream._
import akka.stream.scaladsl._
import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.util.ByteString

import scala.concurrent._
import scala.concurrent.duration._
import java.nio.file.Paths

import akka.stream.scaladsl.Tcp.{IncomingConnection, ServerBinding}
import java.net.InetSocketAddress

import com.typesafe.config.ConfigFactory

import scala.io.Source
import scala.util.Try

object Main extends App {
  val commands: Array[String] = args
  val host = "127.0.0.1"
  val port = 5002

  /**
    * Data Processing Calls
//    */
  args match {
    case Array("map", filename: String) => {
      //      val commands = args
      val words = Source.fromFile(filename).getLines().mkString
      val wordStream: Stream[String] = Source.fromFile(new File(filename)).getLines.toStream
      //      val wordStream2: Stream[String]  = scala.io.Source.fromFile(new File(filename)).getLines.toStream
      ////      val words2 = scala.io.Source.fromFile(new File(filename)).getLines.toStream.foldLeft(Map.empty[String,Int])
      //      val config = ConfigFactory.load("mapcluster.conf")
      //      val app1 = ActorSystem("MappersCluster", config.getConfig("akka").withFallback(config))
      ////      SimpleStart
      ////      val app2 = ActorSystem("MyApp2",
      ////        config.getConfig("myapp2").withOnlyPath("akka").withFallback(config))
      ////      val actorSystem = ActorSystem("MappersCluster")
      ////      actorSystem.actorOf(Props[BackendMappersListener]) ! "start"
    }
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

