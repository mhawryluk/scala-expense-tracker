package gui

import engine.ExpenseCategory.ExpenseCategory
import engine.IncomeCategory.IncomeCategory
import engine.{Statistics, Tracker}

import java.awt.Color
import scala.collection.mutable.ListBuffer
import scala.swing.{Button, Dimension, GridPanel, Label, ListView}

class StatPanel extends GridPanel(4, 2) with EntryPanel {
  background = new Color(0xA5C8E8)
  preferredSize = new Dimension(500, 900)
  private var balance = Tracker.getSum()
  private var expenseSum = Tracker.getSum(Tracker.expenses)
  private var incomeSum = Tracker.getSum(Tracker.incomes)

  private var expenseMap: Map[ExpenseCategory, BigDecimal] = Map()
  private val expenseList: ListBuffer[String] = ListBuffer()
  private val expenseListView = new ListView[String]()
  expenseListView.background = background

  private var incomeMap: Map[IncomeCategory, BigDecimal] = Map()
  private val incomeList: ListBuffer[String] = ListBuffer()
  private val incomeListView = new ListView[String]()
  incomeListView.background = background

  private val balanceLabel = new Label(balance.toString())

  contents += new Label("Balance: ")
  contents += balanceLabel
  contents += new Label("Expense statistics: ")
  contents += new Label("Income statistics: ")
  contents += expenseListView
  contents += incomeListView

  contents += Button("Show expense chart") {
    val pieChart = new PieChart()
    pieChart.showExpenses(expenseMap)
    pieChart.visible = true
    pieChart.size = new Dimension(500, 500)
  }
  contents += Button("Show income chart") {
    val pieChart = new PieChart()
    pieChart.showIncomes(incomeMap)
    pieChart.visible = true
    pieChart.size = new Dimension(500, 500)
  }

  def update(): Unit = {
    balance = Tracker.getSum(Tracker.getBetweenLocalDates(startDate, endDate)(Tracker.getFromCategories(categories)()))

    expenseSum = Tracker.getSum(Tracker.getBetweenLocalDates(startDate, endDate)(Tracker.expenses))
    incomeSum = Tracker.getSum(Tracker.getBetweenLocalDates(startDate, endDate)(Tracker.incomes))
    balanceLabel.text = balance.toString

    expenseMap = Statistics.mapExpenses(startDate, endDate)
    updateExpenseList()
    expenseListView.listData = expenseList

    incomeMap = Statistics.mapIncomes(startDate, endDate)
    updateIncomeList()
    incomeListView.listData = incomeList
  }

  def updateEntryList(list: ListBuffer[String], map: Map[_, BigDecimal], sum: BigDecimal): Unit = {
    list.clear()
    for ((k, v) <- map) {
      val entry: String = v.toString + " (" + Statistics.getPercent(v, sum) + "): " + k.toString
      list += entry
    }
  }

  def updateExpenseList(): Unit = updateEntryList(expenseList, expenseMap, expenseSum)
  def updateIncomeList(): Unit = updateEntryList(incomeList, incomeMap, incomeSum)
}

