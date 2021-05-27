package engine


object ExpenseCategory extends Enumeration {
  type ExpenseCategory = Value
  val Food, House, Transportation, Entertainment, Health, OtherExpense = Value
}

object IncomeCategory extends Enumeration {
  type IncomeCategory = Value
  val Work, Pension, Gift, OtherIncome = Value
}
