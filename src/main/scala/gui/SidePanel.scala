package gui

import engine.{AllCategory, ExpenseCategory, IncomeCategory}

import java.time.LocalDate
import scala.swing.event.{ButtonClicked, SelectionChanged}
import scala.swing.{Button, CheckMenuItem, Color, ComboBox, Dimension, FlowPanel, GridPanel, Label}


class SidePanel extends FlowPanel {
  preferredSize = new Dimension(200, 800)
  background = new Color(0x99c1de)

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

  contents += new GridPanel(2, 1) {
    contents += incomeCheckButton
    contents += expenseCheckButton
  }
  // TODO add functions for check buttons


  // choosing category boxes
  private val categoryPairs: Seq[(String, CheckMenuItem)] =
    for (category <- IncomeCategory.values.toList concat ExpenseCategory.values.toList)
      yield (category.toString, new CheckMenuItem(category.toString){
        listenTo(this)
        reactions += {
          case ButtonClicked(_) => MainWindow.selectCategory(category)
        }
      })
  val ChooseCategoryMap: Map[String, CheckMenuItem] = Map(categoryPairs :_*)

  contents += new GridPanel(categoryPairs.size, 1) {
    for ((name, box) <- ChooseCategoryMap) contents += box
  }

  // choosing tracking period
  val beginDate: LocalDate = LocalDate.parse("2000-01-01")
  val lastDate1: LocalDate = LocalDate.now()
  val allDates: Array[AnyRef] = beginDate.datesUntil(lastDate1.plusDays(1)).toArray.reverse

  val fromDateBox = new ComboBox(allDates)
  val untilDateBox = new ComboBox(allDates)

  contents += new Label("     from:    ")
  contents += fromDateBox

  contents += new Label("       to:       ")
  contents += untilDateBox

  listenTo(fromDateBox.selection)
  listenTo(untilDateBox.selection)

  reactions += {
    case SelectionChanged(`fromDateBox`) =>
      checkValidDates()
      MainWindow.changeStartDate(fromDateBox.peer.getSelectedItem.toString)
    case SelectionChanged(`untilDateBox`) =>
      checkValidDates()
      MainWindow.changeEndDate(untilDateBox.peer.getSelectedItem.toString)
  }

  def checkValidDates(): Unit = {
    if (LocalDate.parse(fromDateBox.peer.getSelectedItem.toString).isAfter(LocalDate.parse(untilDateBox.peer.getSelectedItem.toString))) {
      fromDateBox.peer.setSelectedItem(untilDateBox.peer.getSelectedItem)
    }
  }
  peer.repaint()
}