package engine


sealed trait Category extends Enumeration{
  def withNameOpt(s: String): Option[Value] = values.find(_.toString == s)
}

object ExpenseCategory extends Category{
  type ExpenseCategory = Value
  val Food, House, Transportation, Entertainment, Health, OtherExpense = Value
}

object IncomeCategory extends Category{
  type IncomeCategory = Value
  val Work, Pension, Gift, OtherIncome = Value
}

object AllCategory extends Category{
  type AllCategory = Value
  val All = Value
}
