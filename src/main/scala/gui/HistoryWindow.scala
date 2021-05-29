package gui

import scala.swing.{Dimension, Label, MainFrame}

class HistoryWindow extends MainFrame{
  title = "Statistics"
  preferredSize = new Dimension(400, 400)
  contents = new Label("hello")
}
