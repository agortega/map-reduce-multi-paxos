package actors

import akka.actor.{Actor, ActorLogging}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import cli.{MapMessage1, MapMessage2, ReduceMessage}
import com.typesafe.scalalogging.LazyLogging

import scala.util.Try

class BackendMappersListener extends Actor with LazyLogging {

  val cluster = Cluster(context.system)

  // subscribe to cluster changes, re-subscribe when restart
  override def preStart(): Unit = {cluster.subscribe(self, classOf[MemberEvent])}
  override def postStop(): Unit = cluster.unsubscribe(self)

  def receive = {
    case msg: String => logger.info(s"msg")
    case map: Array[String] => logger.info(s"Map Function2")
    case MapMessage1(msg) => {
      val mappedByUniqueWords = msg.groupBy(identity).mapValues(_.size).toSeq.sortBy(- _._2)
      sender ! mappedByUniqueWords
      logger.info(mappedByUniqueWords.mkString)
    }
    case MapMessage2(msg) => {

    }
    case ReduceMessage(msg1: String, msg2: String) => {
      var mapDrain2: Map[String, Int] = Map.empty //immutable
      var map: Map[String, Int] = Map.empty //immutable
      val noWhiteSpace = msg1.replaceAll(",\\b", "\n")
      val linesForWords = noWhiteSpace.split("\n")
      for (line <- linesForWords) {
        val wordKey = line.split("\\s+")
        map += wordKey(0) -> Try(wordKey(1).toInt).getOrElse(0)
      }

      val orderedBufferMap = map.toSeq.sortBy(-_._2)

      mapDrain2 = map ++ mapDrain2.map { case (k, v) => k -> (v + map.getOrElse(k, 0)) }
      sender ! mapDrain2
    }

    //    case state: CurrentClusterState =>
//      log.info("Current members: {}", state.members.mkString(", "))
//    case MemberUp(member) =>
//      log.info("Member is Up: {}", member.address)
//    case UnreachableMember(member) =>
//      log.info("Member detected as unreachable: {}", member)
//    case MemberRemoved(member, previousStatus) =>
//      log.info("Member is Removed: {} after {}",
//        member.address, previousStatus)
//    case _: MemberEvent => logger.debug(s"MapID: ${this.cluster.selfUniqueAddress}This is very convenient ;-)")
    // ignore
  }
}
