import engine._
import gui._
import Category._

object Main extends App{
  val tracker = Tracker()
  tracker.addExpense(2, null, House)
  tracker.addExpense(500, null)
  println(tracker.expenses)
}
