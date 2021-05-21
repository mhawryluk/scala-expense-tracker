package engine

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar

class Tracker() {
  var expenses: List[Expense] = List[Expense]()
  private var budget: Int = -1

  private val dateFormat = new SimpleDateFormat("y-MM-d")

  def addExpense(expense: Expense): Unit = {
    expense match{
      case Expense(value, null, category, description) => {
        expenses ::= Expense(value, LocalDate.parse(getCurrentDate), category, description)
      }
      case _ => expenses ::= expense
    }
  }

  def getCurrentDate: String = {
    val now = Calendar.getInstance
    dateFormat.format(now.getTime)
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
