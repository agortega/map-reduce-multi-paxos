package server

/*  Generic utilities  */

/**
  * Custom debugger for developer to test programs
  */
object Debugger {
  var isActive = true

  def debugln(o : Object) {
    if (this isActive)
      System.err.println (o) ;
  }
}
import java.net.InetSocketAddress

import Debugger._
import java.nio.channels.{Selector, ServerSocketChannel}
import scala.util.continuations._
/**
  * Created by angel on 6/9/17.
  */
object SimplerServer extends App{
  debugln("Starting my server...")


//  val server = ServerSocketChannel.open()
  /**
    * Port to listen on
    */
//  server.socket().bind(new InetSocketAddress(5000)) //TODO: Verify nonbinding with replicated servers
//  server.configureBlocking(false)

//  import scala.collection.concurrent.
//  import scala.collection.concurrent.BlockingQueue
  import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}

  /**
    * event queue
    */
  type Task = () => Any
  val queue = new LinkedBlockingQueue[Task]
//  while (true) reset {
//    val server = new java.net.ServerSocket(5001)
//    val socket = server.accept()
//    server.close()
//
////    suspend()
////      buffer = byteBuffer
//
//  }
//  def suspend() = shift { k: (Task) => queue = k :: queue}

//  reset
//  val selector =

//  val system = listen ==> connect ==> process ==> reply
//  val pipeline = request ==>  ==>

}
