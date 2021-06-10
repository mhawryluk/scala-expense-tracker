package gui

import engine.Tracker
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.{ChartFactory, ChartPanel, JFreeChart}
import org.jfree.data.category.DefaultCategoryDataset

import java.time.LocalDate
import scala.swing.{Dimension, GridPanel}

class MonthlyStats extends GridPanel(1,1){

  preferredSize = new Dimension(250, 400)
  createChart(LocalDate.now.getYear)

  def createChart(year: Int): Unit = {
    val dataset = new DefaultCategoryDataset

    for (month <- 1 to 12){
      val startDate = LocalDate.of(year, month, 1)
      val endDate = startDate.withDayOfMonth(startDate.lengthOfMonth)
      val amount = Tracker.getSum(Tracker.getBetweenLocalDates(startDate, endDate))
      dataset.addValue(amount, "balance", startDate.getMonth)
    }

    val chart: JFreeChart = ChartFactory.createBarChart(year.toString, null, null, dataset, PlotOrientation.HORIZONTAL, true, false, false)
    peer.removeAll()
    peer.add(new ChartPanel(chart))
  }
}
