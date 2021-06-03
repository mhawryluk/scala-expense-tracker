package gui

import engine.{ExpenseCategory, IncomeCategory}

import scala.swing.{Button, CheckMenuItem, Color, ComboBox, Dimension, FlowPanel, Label}

class SidePanel extends FlowPanel{
  preferredSize = new Dimension(200, 800)
  background = new Color(0x99c1de)
  contents += new Label("SidePanel")

  contents += Button("Add expense") {
    println("adding  expense")
    val window = new ExpenseWindow
    window.visible = true
  }
  contents += Button("Add income") {
    println("adding income")
    val window = new IncomeWindow
    window.visible = true
  }

  val incomeCheckButton = new CheckMenuItem("Incomes")
  val expenseCheckButton = new CheckMenuItem("Expenses")

  contents += incomeCheckButton
  contents += expenseCheckButton

  val categoryBox = new ComboBox(IncomeCategory.values.toList.concat(ExpenseCategory.values.toList))
  contents += categoryBox
}
