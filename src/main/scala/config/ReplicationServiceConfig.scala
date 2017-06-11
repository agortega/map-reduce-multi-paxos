package config

/**
  * Created by Ortega on 6/10/17.
  */
class ReplicationServiceConfig {

  val shards = Seq(
    "localhost:5010",
    "localhost:5009",
    "localhost:5008"
  )
}
