package gui

import engine.{ExpenseCategory, IncomeCategory, Tracker}

import scala.swing.event.KeyTyped
import scala.swing.{Button, ComboBox, Dimension, GridPanel, Label, MainFrame, TextField}
import java.time.{DateTimeException, LocalDate}


sealed class MoneyWindow extends MainFrame {
  preferredSize = new Dimension(400, 320)
  val textField: TextField = new TextField {
    listenTo(keys)
    reactions += {
      case e: KeyTyped => if (!e.char.isDigit && e.char != '.') e.consume
    }
  }
  textField.peer.setText("0")

  val dayBox = new ComboBox(1 to 31)
  val monthBox = new ComboBox(1 to 12)
  val yearBox = new ComboBox(2000 to 2021)

  val cancelButton = Button("Cancel") {
    println("Cancelling")
    close()
  }

  // TODO move common code up

  override def closeOperation(): Unit = dispose
}

class ExpenseWindow extends MoneyWindow {
  title = "Add expenditure"
  val categoryBox = new ComboBox(ExpenseCategory.values.toList)
  contents = new GridPanel(6, 2) {
    contents += new Label("Enter expense amount: ")
    contents += textField
    contents += new Label("Choose expense category: ")
    contents += categoryBox
    contents += new Label("Choose date [day]: ")
    contents += dayBox
    contents += new Label("Choose date [month]: ")
    contents += monthBox
    contents += new Label("Choose date [year]: ")
    contents += yearBox
    contents += cancelButton
    contents += Button("Accept and close") {
      println("Accepting and closing ExpenseWindow")
      val amount = textField.text
      textField.peer.setText("0")
      val category = categoryBox.peer.getSelectedItem

      val day = dayBox.item
      val month = monthBox.item
      val year = yearBox.item
      try {
        val date = LocalDate.of(year, month, day)
        Tracker.addEntry(amount, category, date)
        MainWindow.updateEntries()
      }
      catch {
        case e: DateTimeException => println(e.getMessage)
      } finally close()
    }
  }
}

class IncomeWindow extends MoneyWindow {
  title = "Add income"
  val categoryBox = new ComboBox(IncomeCategory.values.toList)
  contents = new GridPanel(6, 2) {
    contents += new Label("Enter income amount: ")
    contents += textField
    contents += new Label("Choose income category: ")
    contents += categoryBox
    contents += new Label("Choose date [day]: ")
    contents += dayBox
    contents += new Label("Choose date [month]: ")
    contents += monthBox
    contents += new Label("Choose date [year]: ")
    contents += yearBox
    contents += cancelButton
    contents += Button("Accept and close") {
      println("Accepting and closing IncomeWindow")
      val amount = textField.text
      textField.peer.setText("0")
      val category = categoryBox.peer.getSelectedItem

      val day = dayBox.item
      val month = monthBox.item
      val year = yearBox.item
      try {
        val date = LocalDate.of(year, month, day)
        Tracker.addEntry(amount, category, date)
        MainWindow.updateEntries()
      }
      catch {
        case e: DateTimeException => println(e.getMessage)
      } finally close()
    }
  }
}

