/**
  * Created by AngelOrtega on 6/9/2017.
  */
type BallotNumber = Seq[(Int, Int)]
type AcceptNum = Seq[(Int, Int)]
type AcceptVal = Int

sealed trait Message { }
case object PrepareMessage extends Message
case object AcceptMessage extends Message
//case object

/**
  * Make use of case class becaused they are immutable and work well for pattern matching
  *
  */
sealed trait Event
case class Login(user: String) extends Event
