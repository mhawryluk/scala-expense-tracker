package engine

import engine.ExpenseCategory.ExpenseCategory
import engine.IncomeCategory.IncomeCategory

import java.time.LocalDate
import scala.collection.immutable.ListMap

object Statistics {

  def getPercent(value: BigDecimal, total: BigDecimal): String = {
    if (total == 0) 0.toString + "%" else (value * 100 / total).toInt.toString + "%"
  }

  def mapExpenses(from: LocalDate, to: LocalDate): ListMap[ExpenseCategory, BigDecimal] = {
    val map = collection.mutable.Map[ExpenseCategory, BigDecimal]()
    val entries = Tracker.getBetweenLocalDates(from, to)()
    ExpenseCategory.values.foreach(
      category => map += (category -> (Tracker.getSum _ compose (Tracker.getFromCategories(Set(category))(_)))(entries))
    )
    ListMap(map.toSeq.sortWith(_._2 < _._2): _*)
  }

  def mapIncomes(from: LocalDate, to: LocalDate): ListMap[IncomeCategory, BigDecimal] = {
    val map = collection.mutable.Map[IncomeCategory, BigDecimal]()
    val entries = Tracker.getBetweenLocalDates(from, to)()
    IncomeCategory.values.foreach(
      category => map += (category -> Tracker.getSum(Tracker.getFromCategories(Set(category))(entries)))
    )
    ListMap(map.toSeq.sortWith(_._2 > _._2): _*)
  }
}
