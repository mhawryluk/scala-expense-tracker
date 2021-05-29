package gui
import scala.swing._

class MainWindow extends MainFrame {
  title = "Expense tracker"
  preferredSize = new Dimension(500, 500)
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
      val window = new StatisticWindow
      window.visible = true
    }
    contents += Button("Show history") {
      println("showing history of entries")
      val window = new HistoryWindow
      window.visible = true
    }
  }
}