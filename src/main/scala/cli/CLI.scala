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
  def nextCommand(map: CommandMap, tokenList: List[String]) : CommandMap = {
    //def isCommand(userArg: String) = (userArg(0) == "-")
    tokenList match {
      case Nil => map
      case "map" :: filename :: tail => nextCommand(map ++ Map('map -> filename), tokenList.drop(1).tail)
//      case List("reduce", files) => nextCommand(map ++ Map('reduce -> files))
      case option :: tail =>   println("ERROR" + option)
        sys.exit(1)

    }
  }

  val commands = nextCommand(Map(), argList)
  println(commands('map))
}
