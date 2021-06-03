package gui
import scala.swing.BorderPanel.Position.{Center, West}
import scala.swing._

class MainWindow extends MainFrame {
  title = "Expense tracker"
  preferredSize = new Dimension(1000, 800)

  val sidePanel = new SidePanel
  val historyPanel = new HistoryPanel
  val statPanel = new StatPanel

  contents = new BorderPanel() {
    val mainPanel: GridPanel = new GridPanel(2, 1){
      contents += historyPanel
      contents += statPanel
    }

    layout(sidePanel) = West
    layout(mainPanel) = Center
  }
}