package cli

import actors.BackendMappersListener
import akka.actor.{ActorSystem, Props}

object API extends App {
  val commands = args.toList

  /**
    * Data Processing Calls
    */
  commands match {
    case List("map", _) => {
      ???
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

