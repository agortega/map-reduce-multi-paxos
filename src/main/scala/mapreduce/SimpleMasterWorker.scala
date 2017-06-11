package mapreduce

import scala.io.Source
import java.io.{File, FileReader, PrintWriter}
import java.util.concurrent.atomic.AtomicInteger

import scala.collection.immutable.Vector
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import server.Debugger._

sealed trait MapperEvent
case class MapComplete(jid: AtomicInteger) extends MapperEvent

/**
  * Created by Ortega on 6/10/17.
  */
class SimpleMasterWorker {
  val numberOfWorkers = 2 //TODO: Config.read
  val isComplete = false
  val eventIsComplete = false
  val testfile1 = "words1.txt"


//  def map()

  def splitFile() = {
    debugln("Split file operation is starting...")
    Source.fromFile(new File("/home/angel/Courses/cs171/distributed-systems-cs171-proj3/words.txt")).getLines.foldLeft(Map.empty[String, PrintWriter]) {
      case (printers, line) =>
        val id = line.split(" ").head
        val printer = printers.getOrElse(id, new PrintWriter(new File(s"/tmp/log_$id")))
        printer.println(line)
        printers.updated(id, printer)
    }.values.foreach(_.close)
  }


  def mapToWorkers(filename: String) = {
    val system = ActorSystem("map")
//    val reader =  system.actorOf(Props[FileReader](new MapWorker))
      //[Mapgit Reader].start
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

class FileReaderActor(filename: String) extends Actor {
//  def readFile
  override def receive: Receive = ???
}

//class DictionaryActor(filename: String) extends Actor {
////  def getLine: Option[String] = io.Source.fromFile(filename)
//  // flatmap is lazy, so lines are not entered by in memory
//  def getLine = io.Source.fromFile(filename).getLines.flatMap(_.split("\\W+")).toStream.foreach{
//    line =>
//      context.actorOf(Props[StringCounterActor]) ! ProcessStringMsg(line)
//      totalLines += 1
//  }
//
//  val words = input.flatMap(line => line.split(" "))
//
//  override def receive: Receive = ???
//}
