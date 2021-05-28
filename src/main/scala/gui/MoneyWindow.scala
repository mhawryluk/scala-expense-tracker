package gui

import engine.{ExpenseCategory, IncomeCategory}
import scala.swing.event.KeyTyped
import scala.swing.{Button, ComboBox, Dimension, GridPanel, Label, MainFrame, TextField}


sealed class MoneyWindow extends MainFrame{
  preferredSize = new Dimension(400, 320)
  val textField: TextField = new TextField {
    listenTo(keys)
    reactions += {
      case e: KeyTyped => if (!e.char.isDigit) e.consume
    }
  }
  val dayBox = new ComboBox(1 to 31)
  val monthBox = new ComboBox(1 to 12)
  val yearBox = new ComboBox(2000 to 2021)
  val cancelButton : Button = new Button("Cancel"){
    println("Cancelling")
    close()
  }
}

class ExpenseWindow extends MoneyWindow {
  title = "Add expenditure"
  val categoryBox = new ComboBox(ExpenseCategory.values.toList)
  contents = new GridPanel(6,2) {
    contents += new Label("Enter amount: ")
    contents += textField
    contents += new Label("Choose category: ")
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
      println(textField.text)
      textField.peer.setText("")
      println(categoryBox.peer.getSelectedItem)
      println(ExpenseCategory.values.toList)
      // TODO check with exception if date is valid
      // TODO add expense
      close()
    }
  }
}

class IncomeWindow extends MoneyWindow {
  title = "Add income"
  val categoryBox = new ComboBox(IncomeCategory.values.toList)
  contents = new GridPanel(6,2) {
    contents += new Label("Enter amount: ")
    contents += textField
    contents += new Label("Choose category: ")
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
      println(textField.text)
      textField.peer.setText("")
      println(categoryBox.peer.getSelectedItem)
      // TODO check with exception if date is valid
      // TODO add income
      close()
    }
  }
}

