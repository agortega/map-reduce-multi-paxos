package actors

import java.io._
import java.util.concurrent.atomic.AtomicInteger

import akka.actor.{Actor, ActorLogging}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import cli._
import com.typesafe.scalalogging.LazyLogging

import scala.io.Source
import scala.util.Try

class PaxosListener(nodeID: Int, port: Int) extends Actor with LazyLogging {
  val ID: Int = nodeID
  val PORT: Int = port
  val cluster = Cluster(context.system)

  // subscribe to cluster changes, re-subscribe when restart
  override def preStart(): Unit = {cluster.subscribe(self, classOf[MemberEvent])}
  override def postStop(): Unit = cluster.unsubscribe(self)

  override def receive: Receive = {
    case ReplicateMessage1(filename: String, msg1: Array[Byte]) => {
      val byteArray = msg1
      val fileDescription = filename + s"copy_nodeID=${this.ID}"
      val bufferOut = new BufferedOutputStream(new FileOutputStream(fileDescription))
      bufferOut.write(byteArray)
      sender ! fileDescription
      logger.info(s"PORT${this.cluster.selfUniqueAddress}\tSuccessfully Created File Log: " + fileDescription)
    }
  }


}
