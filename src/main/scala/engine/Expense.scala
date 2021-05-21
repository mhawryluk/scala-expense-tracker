package engine
import Category._

import java.time.LocalDate

case class Expense(value: Int, date: LocalDate, category: Category, description: String=null)
