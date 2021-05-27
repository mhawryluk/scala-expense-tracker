package engine

import engine.ExpenseCategory.ExpenseCategory
import engine.IncomeCategory.IncomeCategory

import java.time.LocalDate

class Tracker() {
  var expenses: List[Expense] = List[Expense]()
  var incomes: List[Income] = List[Income]()
  private var maxEntryId: Int = 0
  var budget: Int = -1

  def addEntry(amount: String, category: AnyRef, date: Any = null, description: String = ""): Unit = {
    var parsedDate: LocalDate = null

    date match {
      case str: String => parsedDate = LocalDate.parse(str)
      case localDate: LocalDate => parsedDate = localDate
      case null => parsedDate = LocalDate.now
      case _ => throw new IllegalArgumentException("wrong date format passed")
    }

    category match {
      case cat: ExpenseCategory => expenses ::= Expense(maxEntryId, BigDecimal(amount), parsedDate, cat, description)
      case cat: IncomeCategory => incomes ::= Income(maxEntryId, BigDecimal(amount), parsedDate, cat, description)
      case _ => throw new IllegalArgumentException("wrong category type")
    }

    maxEntryId += 1
  }

  def removeExpense(id: Int): Unit = expenses =
    expenses.filterNot(expense => expense.id == id)

  def getTotalExpenseAmount(expenses: List[Entry] = expenses): BigDecimal =
    expenses.foldLeft(BigDecimal("0"))(_ + _.amount)

  def getFromCategory(category: AnyRef, expenses: List[Entry] = expenses): List[Entry] =
    expenses.filter(expense => expense.category == category)

  def getFromDate(fromStr: String, toStr: String, expenses: List[Entry] = expenses): List[Entry] = {
    val from: LocalDate = LocalDate.parse(fromStr)
    val to: LocalDate = LocalDate.parse(toStr)

    expenses.filter(expense => expense.date.isAfter(from)
      && expense.date.isBefore(to) || expense.date.isEqual(from)
      || expense.date.isEqual(to))
  }
}

object Tracker {
  def apply(): Tracker = {
    new Tracker()
  }
}
