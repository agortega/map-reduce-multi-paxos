//package cli

/**
  * Created by AngelOrtega on 6/4/2017.
  */
object CLI extends App with DataProcessingAPI{
  //stdin >> read ==> parse ==> send ==>
  //val usage = ""
  if (args.length <= 0) println("ERROR CLI")
  val argList = args.toList

  type CommandMap = Map[Symbol, Any]
  //type MapCommand = Seq[String]
  def nextCommand(map: CommandMap, tokenList: List[String]) : CommandMap = {
    //def isCommand(userArg: String) = (userArg(0) == "-")
    tokenList match {
      case Nil => map
      //case "map" :: value :: tail => nextCommand(map ++ Map('filename -> value.toString
      case "map" ::  tail => nextCommand(map ++ Map('filename -> string), tokenList.tail)
      case option :: tail =>  { println("ERROR" + option)
        sys.exit(1)
      }
    }
  }

  val commands = nextCommand(Map(), argList)
  println(commands)
}
