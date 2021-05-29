package gui

import engine.Tracker

import scala.swing.{Dimension, GridPanel, Label, MainFrame}

class StatisticWindow extends MainFrame{
  title = "Statistics"
  preferredSize = new Dimension(400, 400)

  contents = new GridPanel(1,2){
    contents += new Label("Balance: " + Tracker.getSum(Tracker.entries).toString())
    // TODO choose category and date
  }
}
