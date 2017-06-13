import actors.BackendMappersListener
import com.typesafe.config.ConfigFactory
import akka.actor.{ActorRef, ActorSystem, Props}

object SimpleClusterApp {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty)
      startup(Seq("5002", "5003"))
    else
      startup(args)
  }

  def startup(ports: Seq[String]) = {
    var mapsystems = List[ActorSystem]()
    var mapperslist = List[ActorRef]()
    ports foreach { port =>
      // Override the configuration of the port
      val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").
        withFallback(ConfigFactory.parseString("akka.cluster.roles = [mappersbackend]")).
        withFallback(ConfigFactory.load("mapcluster.conf"))

      // Create an Akka system
      var system = ActorSystem("MappersCluster", config)
      // Create an actor that handles cluster domain events
      val mappers = system.actorOf(Props[BackendMappersListener], name = "mappersbackend")
      mapperslist = mappers :: mapperslist
      mapsystems = system :: mapsystems
//      return mappers
//      Thread.sleep(1000)
//      mappers ! "HEY"
//      mapsystem = system
    }
    (mapsystems, mapperslist)
  }
}

class SimpleClusterApp {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty)
      startup(Seq("5002", "5003"))
    else
      startup(args)
  }

  def startup(ports: Seq[String]): Unit = {
    ports foreach { port =>
      // Override the configuration of the port
      val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").
        withFallback(ConfigFactory.parseString("akka.cluster.roles = [mappersbackend]")).
        withFallback(ConfigFactory.load("mapcluster.conf"))

      // Create an Akka system
      val system = ActorSystem("MappersCluster", config)
      // Create an actor that handles cluster domain events
      val mappers = system.actorOf(Props[BackendMappersListener], name = "mappersbackend")

      Thread.sleep(1000)
      mappers ! "HEY"
    }
  }
}

