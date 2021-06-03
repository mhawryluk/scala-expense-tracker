package gui

import engine.{Statistics, Tracker}

import java.awt.Color
import scala.swing.{Dimension, GridPanel, Label}

class StatPanel extends GridPanel(5, 1){
  background = new Color(0xd6e2e9)
  contents += new Label("StatPanel")

  preferredSize = new Dimension(400, 800)
  val balance: BigDecimal = Tracker.getSum()
  val expenseSum: BigDecimal = Tracker.getSum(Tracker.expenses)

  val expenseMap =  Statistics.mapExpenses("2000-01-01", "2021-12-30") // TODO get start and end date
  var expenseValues: String = ""

  for ((k,v) <- expenseMap){
    val entry : String = k.toString + " : \t" + v.toString() + "(" + Statistics.getPercent(v,expenseSum) + ") \n"
    expenseValues = expenseValues.concat(entry)
  }
  println(expenseValues)

  contents += new Label("Balance: " + balance.toString())
  // TODO choose category and date
  contents += new Label("Incomes statistics: ")
  // TODO display all category income
  // contents += new Label(expenseValues)
}
