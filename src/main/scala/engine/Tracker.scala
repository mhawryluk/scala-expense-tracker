package engine

import engine.ExpenseCategory.ExpenseCategory
import engine.IncomeCategory.IncomeCategory

import java.time.LocalDate
import scala.io.Source

object Tracker {
  var expenses: List[Expense] = List[Expense]()
  var incomes: List[Income] = List[Income]()
  def entries: List[Entry] = List.concat(expenses, incomes).sortBy(entry => entry.date)

  val fileName = "data/entries2.json"

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
      case cat: ExpenseCategory => expenses ::= Expense(maxEntryId, -BigDecimal(amount), parsedDate, cat, description)
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

  def getBetweenLocalDates(from: LocalDate, to: LocalDate, entries: List[Entry] = entries): List[Entry] = {
    entries.filter(entry => entry.date.isAfter(from)
      && entry.date.isBefore(to) || entry.date.isEqual(from)
      || entry.date.isEqual(to))
  }

  def readFromJson(fileName: String=fileName): Unit ={
    val file = Source.fromFile(fileName)
    try {
      val jsonString = file.getLines.mkString.strip()
      if (jsonString.isEmpty) return
      val data = ujson.read(jsonString)
      for (x <- data.arr){
        val amount: String = x(0).toString.replaceAll("^\"|\"$", "")
        val categoryString: String = x(1).toString.replaceAll("^\"|\"$", "")
        val date: String = x(2).toString.replaceAll("^\"|\"$", "")
        val description: String = x(3).toString.replaceAll("^\"|\"$", "")

        var category: AnyRef = null

        ExpenseCategory.withNameOpt(categoryString) match {
          case Some(cat) => category = cat
          case None => IncomeCategory.withNameOpt(categoryString) match {
              case Some(cat) => category = cat
              case None => throw new IllegalArgumentException("no such category " + categoryString)
          }
        }
        addEntry(amount, category, date, description)
      }
    }
    finally file.close()
  }

  def saveToJson(fileName: String=fileName): Unit = {
    val entriesList = entries.map(entry => List(entry.amount.toString, entry.category.toString, entry.date.toString, entry.description))

    val file = new java.io.PrintWriter(fileName)
    try file.write(ujson.write(entriesList))
    finally file.close()
    println("saved to file")
  }
}
