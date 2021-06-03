
import engine._
import gui._

object Main extends App{
  Tracker.readFromJson()
  val gui = new MainWindow
  gui.visible = true
}
