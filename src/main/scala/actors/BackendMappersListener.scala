package actors

import akka.actor.Actor
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import com.typesafe.scalalogging.LazyLogging

class BackendMappersListener extends Actor with LazyLogging {

  val cluster = Cluster(context.system)

  // subscribe to cluster changes, re-subscribe when restart
  override def preStart(): Unit = {cluster.subscribe(self, classOf[MemberEvent])}
  override def postStop(): Unit = cluster.unsubscribe(self)

  def receive = {
    case msg: String => logger.info(s"A Backend Cluster node just received a message!")
//    case state: CurrentClusterState =>
//      log.info("Current members: {}", state.members.mkString(", "))
//    case MemberUp(member) =>
//      log.info("Member is Up: {}", member.address)
//    case UnreachableMember(member) =>
//      log.info("Member detected as unreachable: {}", member)
//    case MemberRemoved(member, previousStatus) =>
//      log.info("Member is Removed: {} after {}",
//        member.address, previousStatus)
    case _: MemberEvent => logger.debug(s"MapID: ${this.cluster.selfUniqueAddress}This is very convenient ;-)")
    // ignore
  }
}
