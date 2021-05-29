package gui

import engine.{Statistics, Tracker}

import scala.swing.{Dimension, GridPanel, Label, MainFrame}

class StatisticWindow extends MainFrame{
  title = "Statistics"
  preferredSize = new Dimension(400, 800)
  val balance = Tracker.getSum()
  val expenseSum = Tracker.getSum(Tracker.expenses)

  val expenseMap =  Statistics.mapExpenses("2000-01-01", "2021-12-30") // TODO get start and end date
  var expenseValues : String = ""

  for ((k,v) <- expenseMap){
    val entry : String = k.toString + " : \t" + v.toString() + "(" + Statistics.getPercent(v,expenseSum) + ") \n"
    expenseValues = expenseValues.concat(entry)
  }
  println(expenseValues)


  contents = new GridPanel(5,1){
    contents += new Label("Balance: " + balance.toString())
    // TODO choose category and date
    contents += new Label("Incomes statistics: ")
    // TODO display all category income
   // contents += new Label(expenseValues)
  }


}
