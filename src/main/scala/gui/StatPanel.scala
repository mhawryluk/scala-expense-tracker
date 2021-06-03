package gui

import engine.ExpenseCategory.ExpenseCategory
import engine.IncomeCategory.IncomeCategory
import engine.{Statistics, Tracker}

import java.awt.Color
import java.time.LocalDate
import scala.collection.mutable.ListBuffer
import scala.swing.event.SelectionChanged
import scala.swing.{ComboBox, Dimension, GridPanel, Label, ListView}

class StatPanel extends GridPanel(7, 2){
  background = new Color(0xd6e2e9)
  preferredSize = new Dimension(500, 900)
  val balance = Tracker.getSum()
  val expenseSum = Tracker.getSum(Tracker.expenses)
  val incomeSum = Tracker.getSum(Tracker.incomes)

  var startDate = LocalDate.of(2000, 1,1)
  var endDate = LocalDate.of(2021, 12, 30)

  var expenseMap : Map[ExpenseCategory, BigDecimal] = Map()
  var expenseList : ListBuffer[String] = ListBuffer()
  var expenseListView = new ListView[String]()

  var incomeMap : Map[IncomeCategory, BigDecimal] = Map()
  var incomeList : ListBuffer[String] = ListBuffer()
  var incomeListView = new ListView[String]()

  contents += new Label("Balance: ")
  contents += new Label(balance.toString())
  contents += new Label("Expense statistics: ")
  contents += expenseListView
  contents += new Label("Income statistics: ")
  contents += incomeListView


  def updateStatistics(): Unit ={
    // TODO update balance
    // TODO update income and expense sum
    println("Update statistics")

    // TODO check if startDate < endDate with exception
    expenseMap = Statistics.mapExpenses(startDate, endDate)
    updateExpenseList()
    expenseListView.listData = expenseList
    println(expenseMap)

    incomeMap = Statistics.mapIncomes(startDate, endDate)
    updateIncomeList()
    incomeListView.listData = incomeList
    println(incomeMap)
  }

  def updateExpenseList(): Unit = {
    expenseList.clear()
    for ((k,v) <- expenseMap){
      val entry : String = v.toString + " (" + Statistics.getPercent(v, expenseSum) + "): " + k.toString
      expenseList += entry
    }
  }

  def updateIncomeList(): Unit = {
    incomeList.clear()
    for ((k,v) <- incomeMap){
      val entry : String = v.toString + " (" + Statistics.getPercent(v, incomeSum) + "): " + k.toString
      incomeList += entry
    }
  }

  def changeStartDate(date: String): Unit ={
    startDate = LocalDate.parse(date)
    updateStatistics()
  }
  def changeEndDate(date: String): Unit ={
    endDate = LocalDate.parse(date)
    updateStatistics()
  }


}

