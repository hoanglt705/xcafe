package com.s3s.ssm.view.dashboard;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.text.Text;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.s3s.ssm.config.DashboardContextProvider;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.util.IUpdatable;

public class OperatingTableView extends JPanel implements IUpdatable {
  private static final long serialVersionUID = 1L;
  private Timer refreshTimer;

  public OperatingTableView() {
    setLayout(new BorderLayout());
    final JFXPanel chartPanel = new JFXPanel();
    add(chartPanel);
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        createJFXChart(chartPanel);
      }
    });
    Platform.setImplicitExit(false);
    refreshTimer = new Timer(DELAY, new ActionListener() {

      @SuppressWarnings("unused")
      @Override
      public void actionPerformed(ActionEvent e) {
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            createJFXChart(chartPanel);
            chartPanel.validate();
            chartPanel.repaint();
          }
        });
      }
    });
    refreshTimer.start();
  }

  private void createJFXChart(JFXPanel fxPanel) {
    long[] operatingTable = DashboardContextProvider.getInstance().getDashboardService().getOperatingTable();

    String waitingLabel = ControlConfigUtils
            .getString("label.DashboardPanel.operatingTable.waiting");
    String servingLabel = ControlConfigUtils
            .getString("label.DashboardPanel.operatingTable.serving");
    String emptyLabel = ControlConfigUtils
            .getString("label.DashboardPanel.operatingTable.empty");
    ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(
                    new PieChart.Data(waitingLabel, operatingTable[0]),
                    new PieChart.Data(servingLabel, operatingTable[1]),
                    new PieChart.Data(emptyLabel, operatingTable[2]));
    PieChart chart = new PieChart(pieChartData);
    chart.setLabelLineLength(5);
    chart.setLegendSide(Side.BOTTOM);
    Scene scene = new Scene(chart);
    fxPanel.setScene(scene);

    for (Node node : chart.lookupAll("Text.chart-pie-label")) {
      if (node instanceof Text) {
        for (PieChart.Data data : pieChartData) {
          if (data.getName().equals(((Text) node).getText()))
            ((Text) node).setText(String.format("%,.0f", data.getPieValue()));
        }
      }
    }

  }

  @Override
  public void stop() {
    refreshTimer.stop();
  }

  @Override
  public void start() {
    refreshTimer.start();
  }
}
