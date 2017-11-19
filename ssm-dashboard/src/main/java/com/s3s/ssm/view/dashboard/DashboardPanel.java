package com.s3s.ssm.view.dashboard;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.VerticalLayout;
import org.softsmithy.lib.swing.internal.TableLayoutConstants;

import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.factory.SComponentFactory;
import com.s3s.ssm.view.util.IUpdatable;

public class DashboardPanel extends JPanel implements IUpdatable {
  private static final long serialVersionUID = 1L;

  private static final String TOP_LATEST_PAYMENT_TITLE = ControlConfigUtils
          .getString("label.DashboardPanel.topLatestPayment");
  private static final String TOP_LATEST_INVOICE_TITLE = ControlConfigUtils
          .getString("label.DashboardPanel.topLatestInvoice");
  private static final String STATISTIC_TITLE = ControlConfigUtils
          .getString("label.DashboardPanel.statistic.title");
  private static final String OVERVIEW_TITLE = ControlConfigUtils
          .getString("label.DashboardPanel.overview.title");
  private static final String OPERATING_TABLE_TITLE = ControlConfigUtils
          .getString("label.DashboardPanel.operating.title");

  private ListTopLatestPaymentView latestPaymentView;

  private ListTopLatestTableInvoiceView latestTableInvoiceView;

  private OperatingTableView operatingTableView;

  public DashboardPanel() {
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    TableLayout layout = new TableLayout(new double[][] {
        {TableLayoutConstants.FILL, TableLayoutConstants.FILL, TableLayoutConstants.FILL,
            TableLayoutConstants.FILL},
        {TableLayoutConstants.FILL, TableLayoutConstants.FILL}});
    layout.setHGap(5);
    layout.setVGap(5);

    setLayout(layout);

    add(generateOverview(), "3,0,3,0");
    add(generateStatistic(), "0,0,2,0");
    JPanel tempPanel = new JPanel(new GridLayout(1, 2, 5, 0));
    tempPanel.add(generateTopLatestInvoiceChart());
    tempPanel.add(generateTopLatestPaymentChart());
    add(tempPanel, "0,1,2,1");
    add(generateOperatingTableView(), "3,1,3,1");
  }

  private JPanel generateOperatingTableView() {
    JXTitledPanel titlePanel = new JXTitledPanel(OPERATING_TABLE_TITLE);
    operatingTableView = new OperatingTableView();
    titlePanel.setContentContainer(operatingTableView);
    return titlePanel;
  }

  private JPanel generateOverview() {
    JXTitledPanel titlePanel = new JXTitledPanel(OVERVIEW_TITLE);
    titlePanel.setContentContainer(new OverviewPanel());
    return titlePanel;
  }

  private JPanel generateStatistic() {
    final JXTitledPanel titlePanel = new JXTitledPanel(STATISTIC_TITLE);
    @SuppressWarnings("rawtypes")
    final JComboBox dropdown = SComponentFactory
            .createDropdown(new String[] {
                ControlConfigUtils.getString("label.Statistic.today"),
                ControlConfigUtils.getString("label.Statistic.thisWeek"),
                ControlConfigUtils.getString("label.Statistic.thisMonth"),
                ControlConfigUtils.getString("label.Statistic.thisYear")
            });
    dropdown.addItemListener(new ItemListener() {
      @SuppressWarnings("unused")
      @Override
      public void itemStateChanged(ItemEvent e) {
        titlePanel.getContentContainer().removeAll();
        if (dropdown.getSelectedIndex() == 0) {
          titlePanel.getContentContainer().add(new StatisticView(StatisticView.TODAY_FILTER));
        } else if (dropdown.getSelectedIndex() == 1) {
          titlePanel.getContentContainer().add(new StatisticView(StatisticView.WEEK_FILTER));
        } else if (dropdown.getSelectedIndex() == 2) {
          titlePanel.getContentContainer().add(new StatisticView(StatisticView.MONTH_FILTER));
        } else {
          titlePanel.getContentContainer().add(new StatisticView(StatisticView.YEAR_FILTER));
        }
        titlePanel.getContentContainer().revalidate();
        titlePanel.getContentContainer().repaint();
      }
    });
    dropdown.setSelectedIndex(0);
    titlePanel.setRightDecoration(dropdown);
    titlePanel.getContentContainer().add(new StatisticView(StatisticView.TODAY_FILTER));
    return titlePanel;
  }

  private JPanel generateTopLatestInvoiceChart() {
    JXTitledPanel titlePanel = new JXTitledPanel(TOP_LATEST_INVOICE_TITLE);
    JPanel contentContainer = new JPanel(new BorderLayout());
    contentContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    contentContainer.setLayout(new VerticalLayout());
    latestTableInvoiceView = new ListTopLatestTableInvoiceView();
    contentContainer.add(latestTableInvoiceView);
    titlePanel.setContentContainer(contentContainer);
    return titlePanel;
  }

  private JPanel generateTopLatestPaymentChart() {
    JXTitledPanel titlePanel = new JXTitledPanel(TOP_LATEST_PAYMENT_TITLE);
    JPanel contentContainer = new JPanel(new BorderLayout());
    contentContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    contentContainer.setLayout(new VerticalLayout());
    latestPaymentView = new ListTopLatestPaymentView();
    contentContainer.add(latestPaymentView);
    titlePanel.setContentContainer(contentContainer);
    return titlePanel;
  }

  @Override
  public void stop() {
    latestTableInvoiceView.stop();
    latestPaymentView.stop();
    operatingTableView.stop();
  }

  @Override
  public void start() {
    latestTableInvoiceView.start();
    latestPaymentView.start();
    operatingTableView.start();
  }
}
