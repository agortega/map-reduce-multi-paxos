import actors.EchoActor
import akka.actor.{Actor, ActorSystem, Address, Deploy, Props}
import akka.remote._
import akka.routing.{FromConfig, RandomPool}
import com.typesafe.config.ConfigFactory
//import akka.testkit.{ AkkaSpec, ImplicitSender }




object Main extends App {
  // conf, must be in filepath for calling class
  val system = ActorSystem.create("Router", ConfigFactory.load().getConfig("RouterExample"))
  val randomRouter = system.actorOf(Props[EchoActor].withRouter(FromConfig()), name = "RandomPoolActor")
  1 to 10 foreach {
    i => randomRouter ! i
  }
//  val config = ConfigFactory.load()
//  val system = ActorSystem("sampleActorSystem",
//    config.getConfig("akka").withFallback(config))
//  val actor = system.actorOf(Props[SampleActor], "sampleActor")
//  actor ! "Pretty slick"
//
//  val addressTwo = Address("akka.tcp", "sys", "127.0.0.1", 5003) // this gives the same
//  val ref = system.actorOf(Props[SampleActor].
//    withDeploy(Deploy(scope = RemoteScope(addressTwo))))
//  ref ! "HELLO"
//  val path = ref.path
//  println(path.address)

}

class SampleActor extends Actor {
  def receive = { case _ => sender() ! self }
}

class Deprecated {
  def randomRouter(_system: ActorSystem = ActorSystem("Router")) = _system.actorOf(Props[EchoActor].withRouter(RandomPool(1)), name = "RandomPoolActor")
}

//def remoteConfig(port: Int) = ConfigFactory.parseString(s"""
//akka {
//  actor.provider = "akka.remote.RemoteActorRefProvider"
//  remote {
//    enabled-transports = ["akka.remote.netty.tcp"]
//    netty.tcp {
//      hostname = "127.0.0.1"
//      port = $port
//    }
//  }
//}
//  """)
//
//def remotingSystem(name: String, port: Int) = ActorSystem(name, remotingConfig(port))
