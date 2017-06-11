import akka.actor.{ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

object GateWay extends App {
  val system = ActorSystem("Gateway")
//  system.actorOf(Props[Master], "masta")
}




//object GateWay extends App {
//
//  val mapperPorts = Seq("5001", "5002")
//  val reducerPorts = Seq("5003")
//  val host = "127.0.0.1"
//  val port = if (args.isEmpty) "0" else args(0)
//  val config = ConfigFactory.parseString(
//    s"""akka.remote.netty.tcp.port=$port
//       |akka.remote.netty.tcp.hostname=$host
//     """.stripMargin).
////    withFallback(ConfigFactory.parseString("akka.cluster.roles = [backend]")).
//    withFallback(ConfigFactory.load("mapcluster.conf"))
//
//  val system = ActorSystem("Mappers", config)
//
//  // create one worker provider per node
////  system.actorOf(Props[MasterMapperNode], name = "masterMapperNode")
//
//// def initializeMappers(ports: Seq[String]) = {
////   ports foreach {
////     port => // Override default ports for remote servers
////      val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").
////        withFallback(ConfigFactory.load())
////   }
////  }
//
//}