import engine._
import gui._
import Category._

object Main extends App{
  val tracker = Tracker()
  tracker.addExpense(Expense(2, null, House))
  println(tracker.expenses)
}
