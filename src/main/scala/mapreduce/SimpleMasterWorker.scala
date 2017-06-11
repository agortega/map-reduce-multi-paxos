package mapreduce

import java.io.{File, FileReader}
import java.util.concurrent.atomic.AtomicInteger

import scala.collection.immutable.Vector
import akka.actor._


sealed trait MapperEvent
case class MapComplete(jid: AtomicInteger) extends MapperEvent

/**
  * Created by Ortega on 6/10/17.
  */
class SimpleMasterWorker {
  val numberOfWorkers = 2 //TODO: Config.read
  val isComplete = false
  val eventIsComplete = false
//  val futures = workers.map()
//  def map()

//  spawnWorkers()


  val testfile1 = "words1.txt"

//  def mapUsingAWordStream(filename)  = {
    //val wordStream: Stream[String]  = scala.io.Source.fromFile(new File(filename)).getLines.toStream
    //scala.io.Source.fromFile(new File(filename)).getLines.toStream.foldLeft(Map.empty[String,])
//  }


//  def map()

  private def chunk(spout: Stream[String]): Stream[Seq[String]] = {
    val (head, tail) = spout.span(_.nonEmpty)
    head.toSeq #:: chunk(tail.tail)
  }

  def mapToWorkers(filename: String) = {
    val system = ActorSystem("map")
    val reader =  actorOf[Mapgit Reader].start
//    val workers = Vector.fill(numberOfWorkers)(system.actorOf(new MapWorker(reader)
  }

//  def splitFile(filename: String): {
//    https://stackoverflow.com/questions/6751463/iterate-over-lines-in-a-file-in-parallel-scala
//    val workers = Vector(numberOfWorkers)
//  }

  def map(filename: String) = {
//    val wordStream: Stream[String]  = scala.io.Source.fromFile(new File(filename)).getLines.toStream
//    chunk(wordStream).iterat
  }
  //  def connect()

  //  def listenOnSocket()


}
