package gui

import engine.{ExpenseCategory, IncomeCategory, Tracker}

import scala.swing.event.KeyTyped
import scala.swing.{Button, ComboBox, Dimension, GridPanel, Label, MainFrame, TextField}
import java.time.{DateTimeException, LocalDate}


class MoneyWindow extends MainFrame {
  preferredSize = new Dimension(400, 320)
  val textField: TextField = new TextField {
    listenTo(keys)
    reactions += {
      case e: KeyTyped => if (!e.char.isDigit && e.char != '.') e.consume
    }
  }
  textField.peer.setText("0")
  val descriptionField: TextField = new TextField("")
  val beginDate: LocalDate = LocalDate.parse("2000-01-01")
  val lastDate1: LocalDate = LocalDate.now()
  val allDates: Array[AnyRef] = beginDate.datesUntil(lastDate1.plusDays(1)).toArray.reverse
  val dateBox = new ComboBox(allDates)
  val categoryBox: ComboBox[AnyRef] = new ComboBox(List(""))

  val cancelButton = Button("Cancel") {
    println("Cancelling")
    close()
  }

  def addContents(catBox: ComboBox[AnyRef]): Unit = {
    contents = new GridPanel(5, 2) {
      contents += new Label("Enter amount: ")
      contents += textField
      contents += new Label("Choose category: ")
      contents += catBox
      contents += new Label("Choose date: ")
      contents += dateBox
      contents += new Label("Enter description")
      contents += descriptionField
      contents += cancelButton
      contents += Button("Accept and close") {
        val amount = textField.text
        textField.peer.setText("0")
        val category = catBox.peer.getSelectedItem
        try {
          val date = LocalDate.parse(dateBox.peer.getSelectedItem.toString)
          val description = descriptionField.text
          Tracker.addEntry(amount, category, date)
          MainWindow.updateEntries()
        }
        catch {
          case e: DateTimeException => println(e.getMessage)
        } finally close()
      }
    }
  }

  override def closeOperation(): Unit = dispose
}

class ExpenseWindow extends MoneyWindow {
  title = "Add expenditure"
  override val categoryBox = new ComboBox(ExpenseCategory.values.toList)
  addContents(categoryBox)
}

class IncomeWindow extends MoneyWindow {
  title = "Add income"
  override val categoryBox = new ComboBox(IncomeCategory.values.toList)
  addContents(categoryBox)
}

