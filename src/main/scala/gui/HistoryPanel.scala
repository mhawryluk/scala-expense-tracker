package gui

import engine.{Entry, Statistics, Tracker}

import java.time.LocalDate
import scala.collection.mutable.{ListBuffer, Map}
import scala.swing.event.ButtonClicked
import scala.swing.{BoxPanel, Button, CheckMenuItem, Color, Dimension, Label, Orientation}

class HistoryPanel extends BoxPanel(Orientation.Vertical){
  val historyLabel = new Label("HistoryPanel")
  contents += historyLabel
  preferredSize = new Dimension(300, 250)
  background = new Color(0xf0efeb)

  // TODO category
  var startDate = LocalDate.now()
  var endDate = LocalDate.now()

  var entries : List[Entry] = Tracker.getBetweenLocalDates(startDate, endDate)

  var entryMap : Map[CheckMenuItem, Int] = Map()

  var checkItems : ListBuffer[CheckMenuItem] = ListBuffer()
  val deleteButton = new Button("Delete chosen entries")

  listenTo(deleteButton)
  reactions  += {
    case ButtonClicked(`deleteButton`) => deleteChosen()
  }

  def updateContents(): Unit ={
    contents.clear()
    for (item <- checkItems){
      contents += item
    }
    contents += deleteButton
  }

  def updateItems(): Unit ={
    checkItems.clear()
    entryMap.clear()
    for (entry <- entries){
      val menuItem = new CheckMenuItem(entry.toString)
      checkItems += menuItem
      entryMap += (menuItem -> entry.id)
    }
    updateContents()
  }

  def updateHistory(): Unit ={
    entries = Tracker.getBetweenLocalDates(startDate, endDate)
    updateItems()
  }

  def changeStartDate(date: String): Unit ={
    startDate = LocalDate.parse(date)
    updateHistory()
  }
  def changeEndDate(date: String): Unit ={
    endDate = LocalDate.parse(date)
    updateHistory()
  }

  def deleteChosen(): Unit ={
    println("delete chosen")
    for (item <- checkItems){
      if (item.selected){
        println("item.selected")
        val id = entryMap.getOrElse(item, -1)
        Tracker.removeEntry(id)
      }
    }
    updateHistory()
  }

}
