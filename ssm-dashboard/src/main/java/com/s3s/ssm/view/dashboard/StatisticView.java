package com.s3s.ssm.view.dashboard;

import java.awt.BorderLayout;
import java.util.Calendar;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Tooltip;

import javax.swing.JPanel;

import com.s3s.ssm.config.DashboardContextProvider;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

public class StatisticView extends JPanel {
  private static final long serialVersionUID = 1L;

  public static final String TODAY_FILTER = "Today";
  public static final String WEEK_FILTER = "Week";
  public static final String MONTH_FILTER = "Month";
  public static final String YEAR_FILTER = "Year";
  long openTime;

  public StatisticView(final String filter) {
    setLayout(new BorderLayout());
    final JFXPanel chartPanel = new JFXPanel();
    add(chartPanel);
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        createJFXChart(chartPanel, filter);
      }
    });
    Platform.setImplicitExit(false);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(DashboardContextProvider.getInstance().getCompanyService().getCompany().getOpenTime());
    openTime = calendar.get(Calendar.HOUR_OF_DAY);
  }

  /**
   * @param fxPanel
   * @return
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  private void createJFXChart(JFXPanel fxPanel, String filter) {
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    AreaChart<String, Number> areaChart = new AreaChart<String, Number>(xAxis, yAxis);

    XYChart.Series series = new XYChart.Series();
    series.setName("Doanh thu");

    long[] income = null;
    switch (filter) {
      case TODAY_FILTER:
        income = DashboardContextProvider.getInstance().getReportService().getTodaySaleOfWholeDay();
        for (int i = 0; i < income.length; i++) {
          XYChart.Data data = new XYChart.Data(openTime + i + "", income[i]);
          series.getData().add(data);
        }
        xAxis.setLabel(ControlConfigUtils.getString("label.Statistic.hour"));
        break;

      case WEEK_FILTER:
        String sunday = ControlConfigUtils.getString("label.Statistic.sunday");
        String dayInWeek = ControlConfigUtils.getString("label.Statistic.dayInWeek");
        income = DashboardContextProvider.getInstance().getReportService().getThisWeekSale();
        series.getData().add(new XYChart.Data(sunday, income[0]));
        series.getData().add(new XYChart.Data(dayInWeek + " 2", income[1]));
        series.getData().add(new XYChart.Data(dayInWeek + " 3", income[2]));
        series.getData().add(new XYChart.Data(dayInWeek + " 4", income[3]));
        series.getData().add(new XYChart.Data(dayInWeek + " 5", income[4]));
        series.getData().add(new XYChart.Data(dayInWeek + " 6", income[5]));
        series.getData().add(new XYChart.Data(dayInWeek + " 7", income[6]));
        xAxis.setLabel(ControlConfigUtils.getString("label.Statistic.week"));
        break;
      case MONTH_FILTER:
        income = DashboardContextProvider.getInstance().getReportService().getThisMonthSale();
        for (int i = 0; i < income.length; i++) {
          series.getData().add(new XYChart.Data(i + 1 + "", income[i]));
        }
        xAxis.setLabel(ControlConfigUtils.getString("label.Statistic.day"));
        break;
      case YEAR_FILTER:
        income = DashboardContextProvider.getInstance().getReportService().getThisYearSale();
        for (int i = 0; i < income.length; i++) {
          series.getData().add(new XYChart.Data("T" + (i + 1), income[i]));
        }
        xAxis.setLabel(ControlConfigUtils.getString("label.Statistic.month"));
        break;
      default:
        break;
    }

    Scene scene = new Scene(areaChart, 650, 270);
    areaChart.getData().addAll(series);
    areaChart.setTitle(ControlConfigUtils.getString("label.Statistic.income"));
    areaChart.setLegendVisible(false);

    for (final Series<String, Number> series1 : areaChart.getData()) {
      for (final Data<String, Number> data : series1.getData()) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(data.getYValue().toString());
        Tooltip.install(data.getNode(), tooltip);
      }
    }
    fxPanel.setScene(scene);
  }
}
