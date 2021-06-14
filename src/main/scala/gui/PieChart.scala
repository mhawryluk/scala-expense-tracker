package gui

import engine.ExpenseCategory.ExpenseCategory
import engine.IncomeCategory.IncomeCategory
import org.jfree.chart.{ChartFactory, ChartPanel, JFreeChart}
import org.jfree.data.general.{DefaultPieDataset, PieDataset}

import scala.swing.MainFrame


class PieChart extends MainFrame {

  def createChart(dataset: PieDataset, title: String): JFreeChart = {
    val chart: JFreeChart = ChartFactory.createPieChart(title, dataset, true, true, false)
    chart
  }

  def showChart(entryMap: Map[_, BigDecimal], title: String, sign: Int = 1): Unit = {
    val dataset: DefaultPieDataset = new DefaultPieDataset()
    for ((c, v) <- entryMap) {
      dataset.setValue(c.toString, sign * v.toFloat)
    }
    peer.setContentPane(new ChartPanel(createChart(dataset, title)))
    peer.setLocationRelativeTo(null)
  }

  def showExpenses(expenseMap: Map[ExpenseCategory, BigDecimal]): Unit = showChart(expenseMap, "Expenses", -1)
  def showIncomes(incomeMap: Map[IncomeCategory, BigDecimal]): Unit = showChart(incomeMap, "Incomes")

  override def closeOperation(): Unit = dispose
}
