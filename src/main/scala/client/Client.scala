package client


sealed trait Event
case class BlockingClientEvent() extends Event
/**
  * Created by AngelOrtega on 6/4/2017.
  */
trait Client {

}
