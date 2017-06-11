import mapreduce.SimpleMasterWorker

object Main extends App {
  val master = new SimpleMasterWorker
  master.splitFile()
}
