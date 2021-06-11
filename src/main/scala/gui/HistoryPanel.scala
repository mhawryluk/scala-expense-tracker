package gui

import engine.{Entry, Tracker}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.swing.event.ButtonClicked
import scala.swing._

class HistoryPanel extends BoxPanel(Orientation.Vertical) with EntryPanel {
  preferredSize = new Dimension(300, 250)
  background = new Color(0xf0efeb)

  var entries: List[Entry] = Tracker.getBetweenLocalDates(startDate, endDate)
  var entryMap: mutable.Map[CheckMenuItem, Int] = mutable.Map()
  var checkItems: ListBuffer[CheckMenuItem] = ListBuffer()
  val deleteButton = new Button("Delete chosen entries")

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
    entries = Tracker.getBetweenLocalDates(startDate, endDate, Tracker.getFromCategories(categories))
    updateItems()
  }

  def getYear: Int = endDate.getYear

  def deleteChosen(): Unit = {
    println("delete chosen")
    for (item <- checkItems) {
      if (item.selected) {
        println("item.selected")
        val id = entryMap.getOrElse(item, -1)
        Tracker.removeEntry(id)
      }
    }
    MainWindow.updateEntries()
  }
}