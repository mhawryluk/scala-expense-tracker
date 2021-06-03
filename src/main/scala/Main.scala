
import engine._
import gui._

object Main extends App{
  Tracker.readFromJson()
  val gui = MainWindow
  gui.visible = true
}
