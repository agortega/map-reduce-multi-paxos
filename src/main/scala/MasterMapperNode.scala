import akka.actor.{Actor, ActorLogging, ActorRef, DeadLetter}
import akka.cluster.ClusterEvent.{InitialStateAsEvents, MemberEvent, UnreachableMember}
import akka.cluster.{Cluster, Member}

class MasterMapperNode extends Actor with ActorLogging {
  val mapperNodes = Cluster(context.system)
//  val serverMember: Option[Member] = None

//  val status: ActorStatus = ???
//  sealed trait Status
//  case class Initialized(msg: String) extends Status
//
//  var mapperStatus: Map[ActorRef, Status] = Map.empty[ActorRef, Status]
//
//  override def preStart(): Unit = {
//    super.preStart()
//    mapperNodes.subscribe(self, initialStateMode = InitialStateAsEvents , classOf[MemberEvent], classOf[UnreachableMember])
//    context.system.eventStream.subscribe(self, classOf[DeadLetter])
//  }



  def receive: Actor.Receive = {
    case _ => sender() ! self
  }
}
