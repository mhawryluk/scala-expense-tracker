package gui

import engine.Tracker

import java.awt.Toolkit
import java.awt.Font
import javax.swing.UIManager
import scala.swing.BorderPanel.Position.{Center, East, West}
import scala.swing._
import scala.sys.exit

object MainWindow extends MainFrame {
  title = "Expense tracker"

  font = new Font(Font.SANS_SERIF, Font.BOLD,  14);
  UIManager.put("List.font", font)
  UIManager.put("Label.font", font)
  UIManager.put("CheckBoxMenuItem.font", font)
  UIManager.put("Button.font", font)
  UIManager.put("ComboBox.font", font)

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

  updateEntries()

  override def closeOperation(): Unit = {
    println("close")
    Tracker.saveToJson()
    dispose()
    exit(0)
  }

  def changeStartDate(date: String): Unit = {
    statPanel.changeStartDate(date)
    historyPanel.changeStartDate(date)
  }

  def changeEndDate(date: String): Unit = {
    statPanel.changeEndDate(date)
    historyPanel.changeEndDate(date)
    monthlyStatPanel.createChart(historyPanel.getYear)
  }

  def selectCategory(category: AnyRef): Unit = {
    statPanel.updateCategory(category)
    historyPanel.updateCategory(category)
  }

  def setCategories(categories: Set[AnyRef]): Unit = {
    historyPanel.setCategories(categories)
    statPanel.setCategories(categories)
  }

  def updateEntries(): Unit = {
    statPanel.update()
    historyPanel.update()
    monthlyStatPanel.createChart(historyPanel.getYear)
  }
}
