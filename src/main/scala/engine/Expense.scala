package engine
import Category._

import java.time.LocalDate

case class Expense(id: Int, amount: BigDecimal, date: LocalDate, category: Category, description: String=null)
