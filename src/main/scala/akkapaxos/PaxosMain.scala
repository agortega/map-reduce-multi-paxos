//package akkapaxos
//
//import akka.actor.{ActorSystem, Props}
//
//
//
///**
//  * Created by angel on 6/11/17.
//  */
//object PaxosMain {
//
//
//  case class Config(roleName: String, id: Int, config: File, iface: String)
//
//  def main(args: Array[String]) {
//    val parsedArgs: Config = getConfig(args)
//    val groups = parseGroupsConfig(parsedArgs.config)
//    val processGroupAddress = groups(parsedArgs.roleName).getAddress
//    val processRolePort = groups(parsedArgs.roleName).getPort
//    val iface = parsedArgs.iface
//    val inputValues = io.Source.stdin.getLines //scala.io.Source.fromFile("values").getLines()
//
//
//    //    println("here are the inputs ")
//    //    inputValues.foreach { println }
//
//    val paxosSystem = ActorSystem("paxos-broadcast")
//    val communicationManager = paxosSystem.actorOf(
//      CommunicationManager.props(processGroupAddress, processRolePort, groups, iface), "comm-manager" )
//
//    val participant = parsedArgs.roleName match {
//      case "client" => paxosSystem.actorOf(
//        Client.props(parsedArgs.id, communicationManager, inputValues.toList),
//        parsedArgs.roleName + "-" + parsedArgs.id)
//      case "proposer" => paxosSystem.actorOf(
//        Proposer.props(parsedArgs.id, communicationManager),
//        parsedArgs.roleName + "-" + parsedArgs.id)
//      case "acceptor" => paxosSystem.actorOf(
//        Acceptor.props(parsedArgs.id, communicationManager),
//        parsedArgs.roleName + "-" + parsedArgs.id)
//      case "learner" => paxosSystem.actorOf(
//        Learner.props(parsedArgs.id, communicationManager),
//        parsedArgs.roleName + "-" + parsedArgs.id)
//      case other => throw new RuntimeException("Invalid process role: " + other)
//    }
//  }
//  val paxosSystem = ActorSystem("paxos-broadcast")
//  val communicationManager = paxosSystem.actorOf(CommunicationManager.props(processGroupAddress, processRolePort, groups, iface), "comm-manager" )
//}
