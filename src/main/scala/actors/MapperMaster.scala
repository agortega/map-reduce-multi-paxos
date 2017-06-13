//package actors
//
//import akka.actor.{Actor, ActorRef, Props}
//
//import scala.collection.immutable.Vector
//import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}
//
//class MapperMaster(numWorkers: Int, nmbrOfWords: Int, listener: ActorRef) extends Actor {
//  val workerRouter = context.actorOf(
//    Props[actors.BackendMappersListener]
//  )
//  val workerRouters = {
//    val routees = Vector.fill(numWorkers) {
//      // inject the WorkerActor implementation through Scaldi
//      val r = context.actorOf(Props[BackendMappersListener])
//      context watch r
//      ActorRefRoutee(r)
//    }
//    Router(RoundRobinRoutingLogic(), routees)
//  }
//
//  def receive = {
//    case s: StartMasterMessage =>
//      val urls = masterProcessor.getNewBatches()
//      numPendingWorks = urls.size
//      for (url <- urls) {
//        val msg = WorkerMessage(url, target)
//        workerRouters.route(msg, self)
//      }
//    case WorkerAckMessage(url, result) =>
//      completeWork()
//    case WorkerErrorMessage(url, error) =>
//      // log error
//      completeWork()
//  }
//
//}
