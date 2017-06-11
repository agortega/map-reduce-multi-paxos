package actors

import akka.actor.{Actor, ActorLogging}

/**
  * Created by angel on 6/11/17.
  */
class EchoActor extends Actor with ActorLogging {
  def receive: Receive = {
    case message =>
      log.info("Received Message {} in Actor {}", message, self.path.name)
  }

}
