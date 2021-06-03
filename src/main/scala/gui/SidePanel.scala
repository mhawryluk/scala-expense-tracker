package gui

import engine.{ExpenseCategory, IncomeCategory}

import java.time.LocalDate
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

  val beginDate: LocalDate = LocalDate.parse("2000-01-01")
  val lastDate1: LocalDate = LocalDate.now()
  val allDates =  beginDate.datesUntil(lastDate1.plusDays(1)).toArray.reverse

  val fromDateBox = new ComboBox(allDates)
  val untilDateBox = new ComboBox(allDates)

  contents += new Label("   from:   ")
  contents += fromDateBox

  contents += new Label("    to:       ")
  contents += untilDateBox
}