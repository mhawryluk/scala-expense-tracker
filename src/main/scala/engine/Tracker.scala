package engine

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import Category._

class Tracker() {
  var expenses: List[Expense] = List[Expense]()
  private var budget: Int = -1
  var currentExpenseId: Int = 0
  private val dateFormat = new SimpleDateFormat("yyyy-MM-dd")

  def addExpense(amount: String, date: Any, category: Category=Other, description: String=""): Unit ={
    var parsedDate: LocalDate = null

    date match {
      case str: String => parsedDate = LocalDate.parse(str)
      case localDate: LocalDate => parsedDate = localDate
      case null => parsedDate = getCurrentDate
    }

    expenses ::= Expense(currentExpenseId, BigDecimal(amount), parsedDate, category, description)
    currentExpenseId += 1
  }

  def removeExpense(id: Int): Unit = {
    expenses = expenses.filterNot(expense => expense.id == id)
  }

  def getCurrentDate: LocalDate = {
    val now = Calendar.getInstance
    val str = dateFormat.format(now.getTime)
    LocalDate.parse(str)
  }

  def setBudget(_budget: Int): Unit ={
    budget = _budget
  }
}

object Tracker {
  def apply(): Tracker = {
     new Tracker()
  }
}
