package deployment

import actors.Master
import akka.actor.{ActorSystem, Props}


class MapRun extends App {
  val system = ActorSystem("Map")
  system.actorOf(Props[Master], "masta")


}
