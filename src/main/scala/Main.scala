import engine.ExpenseCategory._
import engine.IncomeCategory._

import engine._
import gui._

object Main extends App{
  Tracker.readFromJson()
//  Tracker.addEntry("-2.78", category=House)
//  Tracker.addEntry("-500.99", category=OtherExpense)
//  Tracker.addEntry("-0.88", category=OtherExpense)
//  Tracker.addEntry("500", category=Work)
//  println(Tracker.expenses)
//  println(Tracker.incomes)
//  println(Tracker.entries)

//  println(Tracker.getBetweenDates("2021-01-11", "2021-05-27"))
//  println(Tracker.getSum())
//  println(Tracker.getFromCategories(Set(OtherExpense, OtherIncome)))

//  Tracker.removeEntry(1)
//  println(tracker.expenses)
//  println(tracker.incomes)
//  println(tracker.entries)

  Tracker.saveToJson("data/entries2.json")

  val gui = new MainWindow
  gui.visible = true
}
