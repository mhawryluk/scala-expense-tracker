package gui

import engine.ExpenseCategory.ExpenseCategory
import engine.IncomeCategory.IncomeCategory
import engine.{ExpenseCategory, IncomeCategory}

import java.time.LocalDate
import scala.swing.event.{ButtonClicked, SelectionChanged}
import scala.swing._


class SidePanel extends FlowPanel {
  preferredSize = new Dimension(250, 400)
  background = new Color(0x99c1de)

  // adding entries buttons
  contents += new GridPanel(0, 2) {
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
  }

  // choosing category boxes
  private val categoryBoxes: Seq[(AnyRef, CheckMenuItem)] =
    for (category <- IncomeCategory.values.toList concat ExpenseCategory.values.toList)
      yield (category, new CheckMenuItem(category.toString) {
        listenTo(this)
        reactions += {
          case ButtonClicked(_) => MainWindow.selectCategory(category)
        }
      })

  val ChooseCategoryMap: Map[AnyRef, CheckMenuItem] = Map(categoryBoxes: _*)

  // entry type buttons
  contents += new GridPanel(categoryBoxes.size, 1) {
    for ((_, box) <- ChooseCategoryMap) contents += box
  }

  contents += new GridPanel(2, 2) {
    contents += Button("All entries") {
      MainWindow.setCategories((ExpenseCategory.values concat IncomeCategory.values).toSet)
      for ((_, box) <- categoryBoxes) {
        box.selected = true
      }
    }

    contents += Button("Clear entries") {
      MainWindow.setCategories(Set())
      for ((_, box) <- categoryBoxes) {
        box.selected = false
      }
    }

    contents += Button("All incomes") {
      MainWindow.setCategories(IncomeCategory.values.toSet)
      for ((category, box) <- categoryBoxes) {
        category match {
          case _: IncomeCategory => box.selected = true
          case _ => box.selected = false
        }
      }
    }
    contents += Button("All expenses") {
      MainWindow.setCategories(ExpenseCategory.values.toSet)
      for ((category, box) <- categoryBoxes) {
        category match {
          case _: ExpenseCategory => box.selected = true
          case _ => box.selected = false
        }
      }
    }

  }

  // choosing tracking period
  val beginDate: LocalDate = LocalDate.parse("2010-01-01")
  val lastDate1: LocalDate = LocalDate.now()
  val allDates: Array[AnyRef] = beginDate.datesUntil(lastDate1.plusDays(1)).toArray.reverse

  val fromDateBox = new ComboBox(allDates)
  val untilDateBox = new ComboBox(allDates)

  contents += new GridPanel(0, 2) {
    contents += new Label("from:")
    contents += fromDateBox
  }

  contents += new GridPanel(0, 2) {
    contents += new Label("to:")
    contents += untilDateBox
  }

  contents += new GridPanel(3,0){
    contents += Button("Show last year"){
      untilDateBox.peer.setSelectedItem(LocalDate.now())
      fromDateBox.peer.setSelectedItem(LocalDate.now().minusYears(1))
      changeDates()
    }
    contents += Button("Show last month"){
      untilDateBox.peer.setSelectedItem(LocalDate.now())
      fromDateBox.peer.setSelectedItem(LocalDate.now().minusMonths(1))
      changeDates()
    }
    contents += Button("Show last week"){
      untilDateBox.peer.setSelectedItem(LocalDate.now())
      fromDateBox.peer.setSelectedItem(LocalDate.now().minusWeeks(1))
      changeDates()
    }
  }

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

  def changeDates(): Unit ={
    MainWindow.changeStartDate(fromDateBox.peer.getSelectedItem.toString)
    MainWindow.changeEndDate(untilDateBox.peer.getSelectedItem.toString)
  }

  def checkValidDates(): Unit = {
    if (LocalDate.parse(fromDateBox.peer.getSelectedItem.toString).isAfter(LocalDate.parse(untilDateBox.peer.getSelectedItem.toString))) {
      fromDateBox.peer.setSelectedItem(untilDateBox.peer.getSelectedItem)
    }
  }
}