import engine.ExpenseCategory._
import engine.IncomeCategory._
import engine._
import gui._

object Main extends App{
//  val tracker = Tracker()
//  tracker.addEntry("-2.78", category=House)
//  tracker.addEntry("-500.99", category=OtherExpense)
//  tracker.addEntry("-0.88", category=OtherExpense)
//  tracker.addEntry("500", category=OtherIncome)
//  println(tracker.expenses)
//  println(tracker.incomes)
//
//  println(tracker.getBetweenDates("2021-01-11", "2021-05-27"))
//  println(tracker.getSum())
//  println(tracker.getFromCategories(Set(OtherExpense, OtherIncome)))
//
//  tracker.removeEntry(1)
//  println(tracker.expenses)
//  println(tracker.incomes)
//  println(tracker.entries)

//  Tracker.saveToJson(tracker, "data/entries2.json")

  val gui = new MainWindow
  gui.visible = true

}
