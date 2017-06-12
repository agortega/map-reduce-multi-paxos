package cli

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

object API extends App {
  val commands = args.toList
  val host = "127.0.0.1"
  val port = 5002
//  val address = new  InetSocketAddress(host,port)
//  val connections: Source[IncomingConnection, Future[ServerBinding]] =
//    Tcp().bind(host, port)
//  connections runForeach { connection =>
//    println(s"New connection from: ${connection.remoteAddress}")
//
//    val echo = Flow[ByteString]
//      .via(Framing.delimiter(
//        ByteString("\n"),
//        maximumFrameLength = 256,
//        allowTruncation = true))
//      .map(_.utf8String)
//      .map(_ + "!!!\n")
//      .map(ByteString(_))
//
//    connection.handleWith(echo)
//  }
//
//  source.runForeach(i => println(i))(materializer)

  /**
    * Data Processing Calls
    */
  commands match {
    case List("map", _) => {
      ???
      val config = ConfigFactory.load("mapcluster.conf")
      val app1 = ActorSystem("MappersCluster", config.getConfig("akka").withFallback(config))
//      val app2 = ActorSystem("MyApp2",
//        config.getConfig("myapp2").withOnlyPath("akka").withFallback(config))
//      val actorSystem = ActorSystem("MappersCluster")
//      actorSystem.actorOf(Props[BackendMappersListener]) ! "start"
    }
    case List("replicate", _*) => ???
    case List("stop") => ???
    case List("resume") => ???
  }

  /**
    * Data Query Calls
    */
  commands match {
    case List("total", _*) => ???
    case List("print") => ???
    case List("merge", _, _) => ???
  }

}

