package gui

import java.time.LocalDate

trait EntryPanel {

  protected var categories: Set[AnyRef] = Set()
  protected var startDate: LocalDate = LocalDate.now
  protected var endDate: LocalDate = LocalDate.now

  def update(): Unit

  def updateCategory(cat: AnyRef): Unit = {
    if (categories contains cat) categories -= cat
    else categories += cat
    update()
  }

  def setCategories(categories: Set[AnyRef]): Unit = {
    this.categories = categories
    update()
  }

  def changeStartDate(date: String): Unit = {
    startDate = LocalDate.parse(date)
    update()
  }

  def changeEndDate(date: String): Unit = {
    endDate = LocalDate.parse(date)
    update()
  }
}
