package gui

import scala.swing.event.KeyTyped
import scala.swing.{Button, ComboBox, Dimension, GridPanel, MainFrame, TextField}

sealed class MoneyWindow extends MainFrame{
  preferredSize = new Dimension(320, 320)
  // TODO dateField
  val textField: TextField = new TextField {
    listenTo(keys)
    reactions += {
      case e: KeyTyped => if (!e.char.isDigit) e.consume
    }
  }
}

class ExpenseWindow extends MoneyWindow {
  title = "Add expenditure"
  val categoryBox = new ComboBox(List("food", "power", "other"))
  contents = new GridPanel(3,1) {
    contents += textField
    contents += categoryBox
    contents += Button("Accept and close") {
      println("Accepting and closing ExpenseWindow")
      println(textField.text)
      textField.peer.setText("")
      println(categoryBox.item)
      // TODO add expense
      close()
    }
  }
}

class IncomeWindow extends MoneyWindow {
  title = "Add income"
  val categoryBox = new ComboBox(List("work", "gift", "other"))
  contents = new GridPanel(3,1) {
    contents += textField
    contents += categoryBox
    contents += Button("Accept and close") {
      println("Accepting and closing IncomeWindow")
      println(textField.text)
      textField.peer.setText("")
      println(categoryBox.item)
      // TODO add income
      close()
    }
  }
}

