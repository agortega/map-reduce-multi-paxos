package mainrunners

object PaxosMain extends App {
  // val N_shards = 3
  val shardSystem: ActorSystem = {
      val config = ConfigFactory.parseString("akka.cluster.roles=[multipaxos]").withFallback(clusterConfig)
      ActorSystem("MultiPaxosSystem", config)
  }
  def configure(config) = {
    ???
  }
  def startBackend(port: Int, role: String): Unit = {
    val conf = ConfigFactory.parseString(s"akka.cluster.roles=[$role]").
      withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)).
      withFallback(ConfigFactory.load())
    val system = ActorSystem("ClusterSystem", conf)
  }
}



object MultiPaxosClusterConfig extends ClusterConfig {
  val seed = s"akka.tcp://LeaderActorSpec@localhost:5005"
  val node1 = role("node1")
  val node2 = role("node2")
  val node3 = role("node3")

  nodeConfig(node1)(ConfigFactory.parseString(config(5005, seed)))
  nodeConfig(node2)(ConfigFactory.parseString(config(5006, seed)))
  nodeConfig(node3)(ConfigFactory.parseString(configNoSeeds(5007)))
}

trait ClusterConfig {

  def config(port: Int, seed: String) =
    s"""
      akka {
        actor {
          provider = "akka.cluster.ClusterActorRefProvider"
        }
        remote {
          netty.tcp {
            host = 'localhost'
            port = $port
          }
        }
        cluster {
          seed-nodes = [
            "$seed"
          ]
        }
      }"""

  def configNoSeeds(port: Int) =
    s"""
      akka {
        actor {
          provider = "akka.cluster.ClusterActorRefProvider"
        }
        remote {
          netty.tcp {
            host = 'localhost'
            port = $port
          }
        }
      }"""

}
