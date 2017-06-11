package mapreduce

import akka.actor.Actor
import akka.cluster.Cluster

/**
  * Created by angel on 6/11/17.
  */
class MapperBackend extends Actor {

  val cluster = Cluster(context.system)

  def receive = ???
//  {
//    case
//    case TransformationJob(text) => sender() ! TransformationResult(text.toUpperCase)
//    case state: CurrentClusterState =>
//      state.members.filter(_.status == MemberStatus.Up) foreach register
//    case MemberUp(m) => register(m)
//  }
}
