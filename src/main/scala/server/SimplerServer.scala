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


  val server = ServerSocketChannel.open()
  server.socket().bind(new InetSocketAddress(5000)) //TODO: Verify nonbinding with replicated servers
  server.configureBlocking(false)
  reset {
    
  }
//  reset
//  val selector =


//  val system = listen ==> connect ==> process ==> reply

//  val pipeline = request ==>  ==>

}
