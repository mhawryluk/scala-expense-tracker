import engine._
import gui._
import Category._

object Main extends App{
  val tracker = Tracker()
  tracker.addExpense("2.78", category=House)
  tracker.addExpense("500.99")
  tracker.addExpense("0.88")
  println(tracker.expenses)

  println(tracker.getFromDate("2021-01-11", "2021-02-21"))
  println(tracker.getTotalExpenseAmount())
  println(tracker.getFromCategory(Other))

  tracker.removeExpense(1)
  println(tracker.expenses)
}
