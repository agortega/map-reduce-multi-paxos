package server

import Debugger._
import java.net.{InetSocketAddress, ServerSocket, Socket}
import java.nio.channels.ServerSocketChannel
import scala.util.continuations._

/**
  * Created by angel on 6/10/17.
  */
class SimplestServer {
  val validPorts = Seq(5000,5001,5002,5003,5004,5005,5006,5007,5008,5009,5010)
  val leader = new InetSocketAddress("localhost", 5000)
  val portListener = ServerSocketChannel.open()
  val server = portListener.bind(leader)
  server.configureBlocking(false)

//  while (true) reset {
//    val
//  }

  def start() = {
    debugln("")
  }

  def init() = {

  }
}
