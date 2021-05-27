import engine.ExpenseCategory._
import engine.IncomeCategory._
import engine._
import gui._

object Main extends App{
  val tracker = Tracker()
  tracker.addEntry("2.78", category=House)
  tracker.addEntry("500.99", category=OtherExpense)
  tracker.addEntry("0.88", category=OtherExpense)
  tracker.addEntry("500", category=OtherIncome)
  println(tracker.expenses)
  println(tracker.incomes)

  println(tracker.getFromDate("2021-01-11", "2021-05-27"))
  println(tracker.getTotalExpenseAmount())
  println(tracker.getFromCategory(OtherExpense))

  tracker.removeExpense(1)
  println(tracker.expenses)

  val gui = new MainWindow
  gui.visible = true

}
