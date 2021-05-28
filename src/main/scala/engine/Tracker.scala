package engine

import engine.ExpenseCategory.ExpenseCategory
import engine.IncomeCategory.IncomeCategory

import java.io.PrintWriter
import java.time.LocalDate
import scala.io.Source

class Tracker() {
  var expenses: List[Expense] = List[Expense]()
  var incomes: List[Income] = List[Income]()
  def entries: List[Entry] = List.concat(expenses, incomes).sortBy(entry => entry.date)

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

  def removeEntry(id: Int): Unit = {
    expenses = expenses.filterNot(expense => expense.id == id)
    incomes = incomes.filterNot(income => income.id == id)
  }

  def getSum(entries: List[Entry] = entries): BigDecimal =
    entries.foldLeft(BigDecimal("0"))(_ + _.amount)

  def getFromCategories(categories: Set[AnyRef], entries: List[Entry] = entries): List[Entry] =
    entries.filter(entry => categories(entry.category))

  def getBetweenDates(fromStr: String, toStr: String, entries: List[Entry] = entries): List[Entry] = {
    val from: LocalDate = LocalDate.parse(fromStr)
    val to: LocalDate = LocalDate.parse(toStr)

    entries.filter(entry => entry.date.isAfter(from)
      && entry.date.isBefore(to) || entry.date.isEqual(from)
      || entry.date.isEqual(to))
  }
}

object Tracker {
  def apply(): Tracker = {
//    readFromJson("data/entries2.json")
    new Tracker()
  }

  def readFromJson(fileName: String): Unit ={
    val file = Source.fromFile(fileName)
    try {
      val jsonString = file.getLines.mkString
      val data = ujson.read(jsonString)
      for (x <- data.arr){
        val id: String = x(0).toString.replaceAll("^\"|\"$", "")
        val amount: String = x(1).toString
        val categoryString: String = x(2).toString
        val date: String = x(3).toString
        val description: String = x(4).toString
        var category = null
      }
    }
    finally{
      file.close()
    }
  }

  def saveToJson(tracker: Tracker, fileName: String): Unit = {
    val entriesList = tracker.entries.map(entry => List(entry.id.toString, entry.amount.toString, entry.category.toString, entry.date.toString, entry.description))

    val file = new java.io.PrintWriter(fileName)
    try {
        file.write(ujson.write(entriesList))
    } finally {
      file.close()
    }
  }

}
