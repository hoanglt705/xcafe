package com.s3s.ssm.view.dashboard;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import org.apache.commons.lang.time.DateUtils;

import com.s3s.ssm.config.DashboardContextProvider;
import com.s3s.ssm.service.IReportService;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.util.IUpdatable;

public class OverviewPanel extends JPanel implements IUpdatable {
  private static final long serialVersionUID = 1L;
  private Logger LOGGER = Logger.getLogger(OverviewPanel.class.getName());

  private final JLabel lblThisYearTotalSale = new JLabel();
  private final JLabel lblThisMonthTotalSale = new JLabel();
  private final JLabel lblThisWeekTotalInvoice = new JLabel();
  private final JLabel lblTodayTotalInvoice = new JLabel();

  private IReportService reportService = DashboardContextProvider.getInstance().getReportService();

  private DecimalFormat decimalFormat = new DecimalFormat();
  private Timer refreshTimer;

  public OverviewPanel() {
    setLayout(new GridLayout(7, 2));
    add(new JLabel("<html><b>" + ControlConfigUtils
            .getString("label.DashboardPanel.overview.thisYearTotalSale") + "</b></html>"));
    add(lblThisYearTotalSale);
    add(new JLabel("<html><b>" + ControlConfigUtils
            .getString("label.DashboardPanel.overview.thisMonthTotalSale") + "</b></html>"));
    add(lblThisMonthTotalSale);
    add(new JLabel("<html><b>" + ControlConfigUtils
            .getString("label.DashboardPanel.overview.thisWeekTotalSale") + "</b></html>"));
    add(lblThisWeekTotalInvoice);
    add(new JLabel("<html><b>" + ControlConfigUtils
            .getString("label.DashboardPanel.overview.thisTodayTotalSale") + "</b></html>"));
    add(lblTodayTotalInvoice);

    lblThisYearTotalSale.setHorizontalAlignment(SwingConstants.RIGHT);
    lblThisMonthTotalSale.setHorizontalAlignment(SwingConstants.RIGHT);
    lblThisWeekTotalInvoice.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTodayTotalInvoice.setHorizontalAlignment(SwingConstants.RIGHT);

    (new UpdateThisYearTotalSale()).execute();
    (new UpdateThisMonthTotalSale()).execute();
    (new UpdateThisWeekTotalSale()).execute();
    (new UpdateTodayTotalSale()).execute();

    refreshTimer = new Timer(DELAY, new ActionListener() {

      @SuppressWarnings("unused")
      @Override
      public void actionPerformed(ActionEvent e) {
        lblThisYearTotalSale.setText("<html><b>" + decimalFormat.format(getThisYearTotalSale())
                + " VND</b></html>");
        lblThisMonthTotalSale.setText("<html><b>" + decimalFormat.format(getThisMonthTotalSale())
                + " VND</b></html>");
        lblThisWeekTotalInvoice.setText("<html><b>" + decimalFormat.format(getThisWeekTotalSale())
                + " VND</b></html>");
        lblTodayTotalInvoice.setText("<html><b>" + decimalFormat.format(getTodayTotalSale())
                + " VND</b></html>");
      }
    });
    refreshTimer.start();
  }

  private class UpdateThisYearTotalSale extends SwingWorker<Long, Object> {

    @Override
    protected Long doInBackground() throws Exception {
      return getThisYearTotalSale();
    }

    @Override
    protected void done() {
      try {
        lblThisYearTotalSale.setText("<html><b>" + decimalFormat.format(get()) + " VND</b></html>");
      } catch (InterruptedException | ExecutionException e) {
        LOGGER.log(Level.SEVERE, e.getMessage());
      }
    }
  }

  private Long getThisYearTotalSale() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MONTH, Calendar.JANUARY);
    calendar = DateUtils.truncate(calendar, Calendar.MONTH);
    return reportService.getTotalIncome(calendar.getTime());
  }

  private Long getThisMonthTotalSale() {
    Date fromDate = DateUtils.truncate(Calendar.getInstance(), Calendar.MONTH).getTime();
    return reportService.getTotalIncome(fromDate);
  }

  private Long getThisWeekTotalSale() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    Date sunday = DateUtils.truncate(calendar.getTime(), Calendar.DATE);
    return reportService.getTotalIncome(sunday);
  }

  private Long getTodayTotalSale() {
    return reportService.getTotalIncome(DateUtils.truncate(new Date(), Calendar.DATE));
  }

  private class UpdateThisMonthTotalSale extends SwingWorker<Long, Object> {

    @Override
    protected Long doInBackground() throws Exception {
      return getThisMonthTotalSale();
    }

    @Override
    protected void done() {
      try {
        lblThisMonthTotalSale.setText("<html><b>" + decimalFormat.format(get()) + " VND</b></html>");
      } catch (InterruptedException | ExecutionException e) {
        LOGGER.log(Level.SEVERE, e.getMessage());
      }
    }
  }

  private class UpdateThisWeekTotalSale extends SwingWorker<Long, Object> {

    @Override
    protected Long doInBackground() throws Exception {
      return getThisWeekTotalSale();
    }

    @Override
    protected void done() {
      try {
        lblThisWeekTotalInvoice.setText("<html><b>" + decimalFormat.format(get()) + " VND</b></html>");
      } catch (InterruptedException | ExecutionException e) {
        LOGGER.log(Level.SEVERE, e.getMessage());
      }
    }
  }

  private class UpdateTodayTotalSale extends SwingWorker<Long, Object> {

    @Override
    protected Long doInBackground() throws Exception {
      return getTodayTotalSale();
    }

    @Override
    protected void done() {
      try {
        lblTodayTotalInvoice.setText("<html><b>" + decimalFormat.format(get()) + " VND</b></html>");
      } catch (InterruptedException | ExecutionException e) {
        LOGGER.log(Level.SEVERE, e.getMessage());
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
