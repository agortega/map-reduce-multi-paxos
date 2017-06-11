//import akka.actor._
//import akka.routing.{ActorRefRoutee, Broadcast, RoundRobinPool}
//
//class Master extends Actor {
//
//  //  val accumulator = context.actorOf(Props[Accumulator], "accumulator")
//
//  var router = Vector.fill(2) {
//    val mappersR = context.actorOf(Props[Counter])
//    context watch mappersR
//    ActorRefRoutee(mappersR)
//  }
//
//  def receive = {
//    case w: Work =>
//      router.route(w, sender())
//    case Terminated(a) =>
//    router = router.removeRoutee(a)
//    val r = context.actorOf(Props[Worker])
//    context watch r
//    router = router.addRoutee(r)
//  }
//}
//
//class Mappers extends Actor {
//  context.actorOf(Props[Mapper], name = "m1")
//  context.actorOf(Props[Mapper], name = "m2")
//
//}