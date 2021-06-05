package gui

import engine.AllCategory.All
import engine.ExpenseCategory.ExpenseCategory
import engine.IncomeCategory.IncomeCategory
import engine.{AllCategory, Category, Statistics, Tracker}

import java.awt.Color
import java.time.LocalDate
import scala.collection.mutable.ListBuffer
import scala.swing.{Dimension, GridPanel, Label, ListView}

class StatPanel extends GridPanel(3, 2) {
  background = new Color(0xd6e2e9)
  preferredSize = new Dimension(500, 900)
  var balance = Tracker.getSum()
  var expenseSum = Tracker.getSum(Tracker.expenses)
  var incomeSum = Tracker.getSum(Tracker.incomes)

  var startDate = LocalDate.of(2000, 1, 1)
  var endDate = LocalDate.of(2021, 12, 30)

  var expenseMap: Map[ExpenseCategory, BigDecimal] = Map()
  var expenseList: ListBuffer[String] = ListBuffer()
  var expenseListView = new ListView[String]()
  expenseListView.background = new Color(0xd6e2e9)

  var incomeMap: Map[IncomeCategory, BigDecimal] = Map()
  var incomeList: ListBuffer[String] = ListBuffer()
  var incomeListView = new ListView[String]()
  incomeListView.background = new Color(0xd6e2e9)

  val balanceLabel = new Label(balance.toString())

  contents += new Label("Balance: ")
  contents += balanceLabel
  contents += new Label("Expense statistics: ")
  contents += expenseListView
  contents += new Label("Income statistics: ")
  contents += incomeListView

  var category: AnyRef = All

  def updateStatistics(): Unit = {
    println("Update statistics")
    if (category!=All) balance = Tracker.getSum(Tracker.getBetweenLocalDates(startDate, endDate, Tracker.getFromCategories(Set(category))))
    else balance = Tracker.getSum(Tracker.getBetweenLocalDates(startDate, endDate))

    expenseSum = Tracker.getSum(Tracker.getBetweenLocalDates(startDate, endDate, Tracker.expenses))
    incomeSum = Tracker.getSum(Tracker.getBetweenLocalDates(startDate, endDate, Tracker.incomes))
    balanceLabel.text = balance.toString

    expenseMap = Statistics.mapExpenses(startDate, endDate)
    updateExpenseList()
    expenseListView.listData = expenseList

    incomeMap = Statistics.mapIncomes(startDate, endDate)
    updateIncomeList()
    incomeListView.listData = incomeList
  }

  def updateExpenseList(): Unit = {
    expenseList.clear()
    for ((k, v) <- expenseMap) {
      val entry: String = v.toString + " (" + Statistics.getPercent(v, expenseSum) + "): " + k.toString
      expenseList += entry
    }
  }

  def updateIncomeList(): Unit = {
    incomeList.clear()
    for ((k, v) <- incomeMap) {
      val entry: String = v.toString + " (" + Statistics.getPercent(v, incomeSum) + "): " + k.toString
      incomeList += entry
    }
  }

  def changeStartDate(date: String): Unit = {
    startDate = LocalDate.parse(date)
    updateStatistics()
  }

  def changeEndDate(date: String): Unit = {
    endDate = LocalDate.parse(date)
    updateStatistics()
  }

  def updateCategory(cat: AnyRef): Unit = {
    category = cat
    updateStatistics()
  }


}

