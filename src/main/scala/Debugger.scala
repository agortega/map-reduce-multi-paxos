/**
  * Created by angel on 6/11/17.
  */
object Debugger {
  var isActive = true

  def debugln(o : Object) {
    if (this isActive)
      System.err.println (o) ;
  }
}
