package gui

import scala.swing.{BoxPanel, Color, Dimension, Label, Orientation}

class HistoryPanel extends BoxPanel(Orientation.Vertical){
  contents += new Label("HistoryPanel")

  preferredSize = new Dimension(300, 250)
  background = new Color(0xf0efeb)
}
