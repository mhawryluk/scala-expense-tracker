package gui
import scala.swing._

class MainWindow extends MainFrame {
  title = "Expense tracker"
  preferredSize = new Dimension(800, 800)
  contents = new GridPanel(5,1) {
    contents += new Label("Hello")
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
    contents += Button("Show balance and statistics") {
      println("showing balance and statistics")
      // TODO
    }
    contents += Button("Show history") {
      println("showing history of entries")
      // TODO enable to delete entries
    }
  }
}