package gui

import engine.Tracker

import java.awt.Toolkit
import java.awt.Font
import javax.swing.UIManager
import scala.swing.BorderPanel.Position.{Center, West}
import scala.swing._
import scala.sys.exit

object MainWindow extends MainFrame {
  title = "Expense tracker"

  font = new Font(Font.SANS_SERIF, Font.BOLD,  14)
  List("List", "Label", "CheckBoxMenuItem", "Button", "Combobox")
    .foreach(element => UIManager.put(element + ".font", font))

  private val width: Int = 1200
  private val height: Int = 1000
  preferredSize = new Dimension(width, height)

  private val screenWidth = Toolkit.getDefaultToolkit.getScreenSize.getWidth
  private val screenHeight = Toolkit.getDefaultToolkit.getScreenSize.getHeight
  peer.setLocation(((screenWidth - width) / 2).toInt, ((screenHeight - height) / 2).toInt)

  val sidePanel = new SidePanel
  val historyPanel = new HistoryPanel
  val statPanel = new StatPanel
  val monthlyStatPanel = new MonthlyStats

  contents = new BorderPanel() {
    val mainPanel: GridPanel = new GridPanel(2, 1) {
      contents += historyPanel
      contents += statPanel
    }

    layout(new GridPanel(2, 1){
      contents += sidePanel
      contents += monthlyStatPanel
    }) = West
    layout(mainPanel) = Center
  }

  private val entryPanels = List(statPanel, historyPanel)
  updateEntries()

  override def closeOperation(): Unit = {
    Tracker.saveToJson()
    dispose()
    exit(0)
  }

  def changeStartDate(date: String): Unit = entryPanels.foreach(_.changeStartDate(date))

  def changeEndDate(date: String): Unit = {
    entryPanels.foreach(_.changeEndDate(date))
    monthlyStatPanel(historyPanel.getYear)
  }

  def selectCategory(category: AnyRef): Unit = entryPanels.foreach(_.updateCategory(category))

  def setCategories(categories: Set[AnyRef]): Unit =  entryPanels.foreach(_.setCategories(categories))

  def updateEntries(): Unit = {
    entryPanels.foreach(_.update())
    monthlyStatPanel(historyPanel.getYear)
  }
}
