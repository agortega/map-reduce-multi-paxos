
import akka.actor._
import scala.concurrent.duration._
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest._

import scala.util.Random

class MapperSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
    with WordSpecLike with MustMatchers with BeforeAndAfterAll {

    def this() = this(ActorSystem("MapperSpec"))

//    val wordCount = ???
    val nbrOfMappers = 2

    override def afterAll() {
      TestKit.shutdownActorSystem(system)
    }


    s"all $nbrOfMappers mappers" must {
      s"receive disjoint set of words from a word file" in {

        val mappers = (for (i <- 0 until nbrOfMappers) yield system.actorOf(Props[BackendMappersListener], name = s"mapper-$i")).toSet


      }
    }
}



