package engine

import engine.ExpenseCategory.ExpenseCategory
import engine.IncomeCategory.IncomeCategory

import java.time.LocalDate
import scala.collection.immutable.ListMap
import scala.collection.mutable.ListBuffer

object Statistics {

  def getPercent(value: BigDecimal, total: BigDecimal): String = {
    if (total == 0) 0.toString + "%" else (value * 100 / total).toInt.toString + "%"
  }

  def mapExpenses(from: LocalDate, to: LocalDate): ListMap[ExpenseCategory, BigDecimal] = {
    val map = collection.mutable.Map[ExpenseCategory, BigDecimal]()
    val entries = Tracker.getBetweenLocalDates(from, to)
    for (category <- ExpenseCategory.values) {
      map += (category -> Tracker.getSum(Tracker.getFromCategories(Set(category), entries)))
    }
    ListMap(map.toSeq.sortWith(_._2 < _._2): _*)
  }

  def mapIncomes(from: LocalDate, to: LocalDate): ListMap[IncomeCategory, BigDecimal] = {
    val map = collection.mutable.Map[IncomeCategory, BigDecimal]()
    val entries = Tracker.getBetweenLocalDates(from, to)
    for (category <- IncomeCategory.values) {
      map += (category -> Tracker.getSum(Tracker.getFromCategories(Set(category), entries)))
    }
    ListMap(map.toSeq.sortWith(_._2 < _._2): _*)
  }

  def getMenuItems(from: LocalDate, to: LocalDate): List[String] = {
    val entries = Tracker.getBetweenLocalDates(from, to)
    val menuList: ListBuffer[String] = ListBuffer()
    for (entry <- entries) {
      val value: String = entry.category.toString + "  " + entry.amount.toString + "  " + entry.date.toString
      menuList += value
    }
    menuList.toList
  }

}
