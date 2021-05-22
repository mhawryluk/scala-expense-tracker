package engine

import engine.Category._

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar

class Tracker() {
  private val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
  var expenses: List[Expense] = List[Expense]()
  var currentExpenseId: Int = 0
  private var budget: Int = -1

  def addExpense(amount: String, date: Any, category: Category = Other, description: String = ""): Unit = {
    var parsedDate: LocalDate = null

    date match {
      case str: String => parsedDate = LocalDate.parse(str)
      case localDate: LocalDate => parsedDate = localDate
      case null => parsedDate = getCurrentDate
    }

    expenses ::= Expense(currentExpenseId, BigDecimal(amount), parsedDate, category, description)
    currentExpenseId += 1
  }

  def getCurrentDate: LocalDate = {
    val now = Calendar.getInstance
    val str = dateFormat.format(now.getTime)
    LocalDate.parse(str)
  }

  def removeExpense(id: Int): Unit = {
    expenses = expenses.filterNot(expense => expense.id == id)
  }

  def getTotalExpenseAmount(expenses: List[Expense] = expenses): BigDecimal = {
    expenses.foldLeft(BigDecimal("0"))(_ + _.amount)
  }

  def getFromCategory(category: Category, expenses: List[Expense] = expenses): List[Expense] = {
    expenses.filter(expense => expense.category == category)
  }

  def getFromDate(fromStr: String, toStr: String, expenses: List[Expense] = expenses): List[Expense] = {
    val from: LocalDate = LocalDate.parse(fromStr)
    val to: LocalDate = LocalDate.parse(toStr)

    expenses.filter(expense => expense.date.isAfter(from)
      && expense.date.isBefore(to) || expense.date.isEqual(from)
      || expense.date.isEqual(to))
  }

  def setBudget(_budget: Int): Unit = {
    budget = _budget
  }
}

object Tracker {
  def apply(): Tracker = {
    new Tracker()
  }
}
