package mapreduce

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created by angel on 6/11/17.
  */
object MasterMapGateway extends App with MasterMapGateway {
  val mapperPorts = Seq("5001", "5002")

  spawnMappers(mapperPorts)

}

trait MasterMapGateway {

  /**
    * Here we spawn the backend mappers for our processes
    * @param ports
    */
  def spawnMappers(ports: Seq[String]) = {
    ports foreach {
      port =>
        val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port")
          .withFallback(ConfigFactory.parseString("akka.cluster.roles = [mapper-slaves]"))
          .withFallback(ConfigFactory.load())

        val mapperSystem = ActorSystem("ClusterSystem", config)
        mapperSystem.actorOf(Props[MapperBackend], name = "mapper-slaves")
      //        mapperSystem.actorOf(Props[MapperListener], name = "mapperListener")
      //        mapperSystem.actorOf(Props[SimpleClusterListener], name = "clusterListener")

    }
  }

//  def main(args: Array[String]): Unit = {
//    // Override the configuration of the port when specified as program argument
//    val port = if (args.isEmpty) "0" else args(0)
//    val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").
//      withFallback(ConfigFactory.parseString("akka.cluster.roles = [backend]")).
//      withFallback(ConfigFactory.load())
//
//    val system = ActorSystem("ClusterSystem", config)
//    system.actorOf(Props[TransformationBackend], name = "backend")
//  }
}
