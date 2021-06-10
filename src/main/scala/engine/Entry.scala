package engine


import engine.ExpenseCategory.ExpenseCategory
import engine.IncomeCategory.IncomeCategory

import java.time.LocalDate

sealed trait Entry {
  def id: Int
  def amount: BigDecimal
  def date: LocalDate
  def category: AnyRef
  def description: String
  override def toString: String = category.toString + "   " + amount.toString + "   " + date.toString + "   " + description
}

case class Expense(id: Int, amount: BigDecimal, date: LocalDate, category: ExpenseCategory, description: String = null) extends Entry

case class Income(id: Int, amount: BigDecimal, date: LocalDate, category: IncomeCategory, description: String = null) extends Entry
