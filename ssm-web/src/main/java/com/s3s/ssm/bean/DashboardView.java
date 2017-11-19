package com.s3s.ssm.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import com.s3s.ssm.config.DashboardContextProvider;
import com.s3s.ssm.dto.LatestInvoiceDto;
import com.s3s.ssm.dto.PaymentDto;

@ManagedBean(name = "dashboardView")
@ViewScoped
public class DashboardView {
  private PieChartModel pieModel;
  private BarChartModel incomeChartModel;

  @PostConstruct
  public void init() {
    createPieModel();
    createBarModel();
  }

  public PieChartModel getPieModel() {
    return pieModel;
  }

  private void createPieModel() {
    long[] operatingTable = DashboardContextProvider.getInstance().getDashboardService().getOperatingTable();
    pieModel = new PieChartModel();
    pieModel.set("Waiting", operatingTable[0]);
    pieModel.set("Serving", operatingTable[1]);
    pieModel.set("Empty", operatingTable[2]);
    pieModel.setLegendPosition("w");
    pieModel.setShowDataLabels(true);
  }

  
  public List<PaymentDto> getPaymentDto() {
    return DashboardContextProvider.getInstance().getDashboardService().getLatestPayment(10);
  }
  
  public List<LatestInvoiceDto> getInvoiceDto() {
    return DashboardContextProvider.getInstance().getDashboardService().getLatestInvoice(10);
  }

  public BarChartModel getBarModel() {
    return incomeChartModel;
  }

  private BarChartModel initBarModel() {
    BarChartModel model = new BarChartModel();
    long[] weekSale = DashboardContextProvider.getInstance().getReportService().getThisWeekSale();

    ChartSeries boys = new ChartSeries();
    boys.setLabel("Day");
    boys.set("Sun", weekSale[0]);
    boys.set("Mon", weekSale[1]);
    boys.set("Tue", weekSale[2]);
    boys.set("Wed", weekSale[3]);
    boys.set("Thu", weekSale[4]);
    boys.set("Fri", weekSale[5]);
    boys.set("Sat", weekSale[6]);
    model.addSeries(boys);
    return model;
  }

  private void createBarModel() {
    incomeChartModel = initBarModel();
    Axis xAxis = incomeChartModel.getAxis(AxisType.X);
    xAxis.setLabel("Day");
    Axis yAxis = incomeChartModel.getAxis(AxisType.Y);
    yAxis.setLabel("Income");
  }
}
