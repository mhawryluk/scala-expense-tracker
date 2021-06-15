package gui

import engine.{Entry, Tracker}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.swing._
import scala.swing.event.ButtonClicked

class HistoryPanel extends BoxPanel(Orientation.Vertical) with EntryPanel {
  preferredSize = new Dimension(300, 250)
  //  background = new Color(0xffffff)

  private var entries: List[Entry] = Tracker.getBetweenLocalDates(startDate, endDate)()
  private val entryMap: mutable.Map[CheckMenuItem, Int] = mutable.Map()
  private val checkItems: ListBuffer[CheckMenuItem] = ListBuffer()
  private val deleteButton = new Button("Delete chosen entries")

  listenTo(deleteButton)
  reactions += {
    case ButtonClicked(`deleteButton`) => deleteChosen()
  }

  def updateContents(): Unit = {
    contents.clear()
    contents += deleteButton
    for (item <- checkItems) {
      contents += item
    }
    peer.revalidate()
    peer.repaint()
  }

  def updateItems(): Unit = {
    checkItems.clear()
    entryMap.clear()
    for (entry <- entries) {
      val menuItem = new CheckMenuItem(entry.toString)
      checkItems += menuItem
      entryMap += (menuItem -> entry.id)
    }
    updateContents()
  }

  def update(): Unit = {
    entries = Tracker.getBetweenLocalDates(startDate, endDate) (Tracker.getFromCategories(categories)())
    updateItems()
  }

  def getYear: Int = endDate.getYear

  def deleteChosen(): Unit = {
    checkItems.foreach(item =>
      if (item.selected) {
        val id = entryMap.getOrElse(item, -1)
        Tracker.removeEntry(id)
      }
    )
    MainWindow.updateEntries()
  }
}