package gui

import engine.ExpenseCategory.ExpenseCategory
import engine.IncomeCategory.IncomeCategory

import scala.swing.{Dimension, GridPanel, MainFrame}
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.data.general.DefaultPieDataset
import org.jfree.data.general.PieDataset
import org.jfree.ui.ApplicationFrame
import org.jfree.ui.RefineryUtilities


class PieChart extends MainFrame{

  def showExpenses(expenseMap: Map[ExpenseCategory, BigDecimal]): Unit ={
    val dataset: DefaultPieDataset = new DefaultPieDataset()
    for ((c,v) <- expenseMap){
      dataset.setValue(c.toString, v.toFloat)
    }
    peer.setContentPane(new ChartPanel(createChart(dataset, "Expenses")))
    peer.setLocationRelativeTo(null)
  }

  def showIncomes(incomeMap: Map[IncomeCategory, BigDecimal]): Unit ={
    val dataset: DefaultPieDataset = new DefaultPieDataset()
    for ((c,v) <- incomeMap){
      dataset.setValue(c.toString, v.toFloat)
    }
    peer.setContentPane(new ChartPanel(createChart(dataset, "Incomes")))
    peer.setLocationRelativeTo(null)
  }

  def createChart(dataset: PieDataset, title: String): JFreeChart ={
    val chart: JFreeChart = ChartFactory.createPieChart(title, dataset, true, true, false)
    chart
  }

  override def closeOperation(): Unit = dispose


}
