package mapreduce

import akka.actor.Actor
import akka.actor.Props
import akka.actor.{Address, AddressFromURIString}
import akka.remote.routing.RemoteRouterConfig
import akka.routing.RoundRobinPool


object Master {
  case class MapAcknowledgement(from: String)
  case class MapCompletePartial(from: String, fileCreated: String)
}

/**
  * Created by angel on 6/11/17.
  */
class Master extends Actor{

  val numberOfMapperIntances = ???
  numberOfMapperIntances = 2

  val mapper = context.actorOf(Props[Mapper].withRouter(
    RoundRobinRouter(numberOfMapperIntances)),name = "mappers")

  val addresses = Seq(
    Address("akka.tcp", "remotemappers", "localhost", 5001),
    Address("akka.tcp", "remotemappers", "localhost", 5002)
//    AddressFromURIString("akka.tcp://othersys@anotherhost:1234"))
  val routerRemote = system.actorOf(
    RemoteRouterConfig(RoundRobinPool(5), addresses).props(Props[Echo]))

  import Master._
  def receive: Receive = {
    case MapAcknowledgement(mapperID) => println(s"Mapper received job: $mapperID")
    case MapCompletePartial(mapperID, fileCreated) => println(s"Mapped $mapperID created file named $fileCreated")
  }

}
