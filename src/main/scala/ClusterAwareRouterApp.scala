import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

/**
  * Created by angel on 6/11/17.
  */
object ClusterAwareRouterApp extends App {
//  val ports = Seq(5001, 5002)
  var port = 5001
//  if (!args.isEmpty && (args(0).equals("node-1")))  port = 2551
//  if (!args.isEmpty && (args(0).equals("node-2")))  port = 2552

  val system = ActorSystem.create("ClusterSystem", ConfigFactory.
    parseString(s"ClusterAwareRouter.akka.remote.netty.tcp.port=${port}").
    withFallback(ConfigFactory.load())
    .getConfig("ClusterAwareRouter"))
}
