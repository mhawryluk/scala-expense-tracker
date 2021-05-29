package engine

import engine.ExpenseCategory.ExpenseCategory

import java.math.MathContext
import scala.collection.immutable.ListMap

object Statistics {

  def getPercent(value: BigDecimal, total: BigDecimal): String = {
    (value*100/total).round(new MathContext(1)).toString() + "%"
  }

  def mapExpenses(from: String, to: String): ListMap[ExpenseCategory, BigDecimal] ={
    val map = collection.mutable.Map[ExpenseCategory, BigDecimal]()
    val entries = Tracker.getBetweenDates(from, to)
    for (category <- ExpenseCategory.values){
      map += (category -> Tracker.getSum(Tracker.getFromCategories(Set(category), entries)))
    }
    ListMap(map.toSeq.sortWith(_._2 < _._2):_*)
  }

}
