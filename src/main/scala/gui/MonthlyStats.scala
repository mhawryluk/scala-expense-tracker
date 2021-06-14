package gui

import engine.Tracker
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.{ChartFactory, ChartPanel, JFreeChart}
import org.jfree.data.category.DefaultCategoryDataset

import java.time.LocalDate
import scala.swing.{Dimension, GridPanel}

class MonthlyStats extends GridPanel(1, 1) {

  preferredSize = new Dimension(250, 400)
  apply(LocalDate.now.getYear)

  def apply(year: Int): Unit = {
    val dataset = new DefaultCategoryDataset

    for (monthNum <- 1 to 12) {
      val startDate = LocalDate.of(year, monthNum, 1)
      val endDate = startDate.withDayOfMonth(startDate.lengthOfMonth)
      val month: String = startDate.getMonth.toString

      val entries = Tracker.getBetweenLocalDates(startDate, endDate)()

      val incomeSum = Tracker.getSum(Tracker.getIncomes(entries))
      dataset.addValue(incomeSum, "incomes", month)

      val expenseSum = Tracker.getSum(Tracker.getExpenses(entries))
      dataset.addValue(expenseSum, "expenses", month)

      val balance = expenseSum + incomeSum
      dataset.addValue(balance, "balance", month)
    }

    val chart: JFreeChart = ChartFactory.createBarChart(year.toString, null, null, dataset, PlotOrientation.HORIZONTAL, true, false, false)
    peer.removeAll()
    peer.add(new ChartPanel(chart))
  }
}
