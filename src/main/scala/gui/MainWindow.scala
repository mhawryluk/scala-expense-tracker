package gui
import engine.{Category, Tracker}

import java.awt.Toolkit
import scala.swing.BorderPanel.Position.{Center, West}
import scala.swing._
import scala.sys.exit

object MainWindow extends MainFrame{
  title = "Expense tracker"

  private val width: Int = 1000
  private val height: Int = 800
  preferredSize = new Dimension(width, height)

  private val screenWidth = Toolkit.getDefaultToolkit.getScreenSize.getWidth
  private val screenHeight = Toolkit.getDefaultToolkit.getScreenSize.getHeight
  peer.setLocation(((screenWidth-width)/2).toInt, ((screenHeight-height)/2).toInt)

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

  updateEntries()

  override def closeOperation(): Unit = {
    println("close")
    Tracker.saveToJson()
    dispose()
    exit(0)
  }

  def changeStartDate(date: String): Unit ={
    statPanel.changeStartDate(date)
    historyPanel.changeStartDate(date)
  }
  def changeEndDate(date: String): Unit ={
    statPanel.changeEndDate(date)
    historyPanel.changeEndDate(date)
  }

  def changeCategory(category: AnyRef): Unit ={
    statPanel.updateCategory(category)
    historyPanel.updateCategory(category)
  }

  def updateEntries(): Unit ={
    statPanel.updateStatistics()
    historyPanel.updateHistory()
  }

}
