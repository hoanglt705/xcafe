package com.s3s.ssm.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.plaf.BorderUIResource;

import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;

import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.list.AListView;
import com.s3s.ssm.view.report.ListEximportStatistic;
import com.s3s.ssm.view.report.ListInvoiceIncomeStatistic;
import com.s3s.ssm.view.report.ListProductInStroreStatistic;
import com.s3s.ssm.view.report.ListProductIncomeStatistic;
import com.s3s.ssm.view.report.ListWarningProductStatistic;
import com.s3s.ssm.view.report.finalprocess.FinalPeriodProceedPanel;

import de.javasoft.swing.JYTabbedPane;

public class ReportTaskPaneBuilder implements ActionListener {

  private static final String WARNING_PRODUCT_TITLE = ControlConfigUtils
          .getString("JTree.Store.WarningProduct");
  private static final String FOODTABLE_INCOME_TITLE = ControlConfigUtils
          .getString("JTree.Config.FoodTableIncome");
  private static final String PRODUCT_INCOME_TITLE = ControlConfigUtils
          .getString("JTree.Catalog.ProductIncome");
  private static final String INVOICE_INCOME_TITLE = ControlConfigUtils
          .getString("JTree.Catalog.InvoiceIncome");
  private static final String EXIMPORT_TITLE = ControlConfigUtils.getString("JTree.Store.Eximport");
  private static final String IN_STORE_TITLE = ControlConfigUtils.getString("JTree.Store.InStore");
  private static final String FINAL_PERIOD_PROCESS_TITLE = ControlConfigUtils
          .getString("JTree.Config.FinalPeriodProceed");
  private JYTabbedPane contentViewPane;
  private ButtonGroup buttonGroup = new ButtonGroup();

  public ReportTaskPaneBuilder(JYTabbedPane contentViewPanel) {
    this.contentViewPane = contentViewPanel;
  }

  public JComponent buildApplicationContext() {
    JXTaskPaneContainer container = new JXTaskPaneContainer();
    container.setBorder(new BorderUIResource(BorderFactory.createEmptyBorder(2, 2, 2, 2)));
    container.add(generateReportTaskPane());
    container.add(generateStatisticTaskPane());
    container.add(generateFinalPeriodProceedButton());
    return container;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void actionPerformed(ActionEvent e) {
    String name = ((AbstractButton) e.getSource()).getName();
    JPanel view = null;
    String title = null;
    if ("bntProductIncomeStatistic".equals(name)) {
      title = PRODUCT_INCOME_TITLE;
    } else if ("bntFoodTableIncome".equals(name)) {
      title = FOODTABLE_INCOME_TITLE;
    } else if ("bntWarningProductNode".equals(name)) {
      title = WARNING_PRODUCT_TITLE;
    } else if ("bntFinalPeriodProceed".equals(name)) {
      title = FINAL_PERIOD_PROCESS_TITLE;
    } else if ("bntEximport".equals(name)) {
      title = EXIMPORT_TITLE;
    } else if ("bntInvoiceIncome".equals(name)) {
      title = INVOICE_INCOME_TITLE;
    } else if ("bntInStore".equals(name)) {
      title = IN_STORE_TITLE;
    }
    if (contentViewPane.indexOfTab(title) == -1) {
      switch (name) {
        case "bntProductIncomeStatistic":
          view = new ListProductIncomeStatistic();
          break;
        case "bntWarningProductNode":
          view = new ListWarningProductStatistic();
          break;
        case "bntFinalPeriodProceed":
          view = new FinalPeriodProceedPanel();
          break;
        case "bntEximport":
          view = new ListEximportStatistic();
          break;
        case "bntInvoiceIncome":
          view = new ListInvoiceIncomeStatistic();
          break;
        case "bntInStore":
          view = new ListProductInStroreStatistic();
          break;
        default:
          break;
      }
      if (view instanceof AListView) {
        ((AListView) view).loadView();
      }
      contentViewPane.addTab(title, view);
    }
    contentViewPane.setSelectedIndex(contentViewPane.indexOfTab(title));
  }

  private JXTaskPane generateStatisticTaskPane() {
    JXTaskPane manageTaskPane = new JXTaskPane();
    manageTaskPane.setTitle(ControlConfigUtils.getString("JTree.StatisticManagement"));
    manageTaskPane.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.REPORT_ICON));

    JToggleButton bntProductIncomeStatistic = new JToggleButton(PRODUCT_INCOME_TITLE);
    bntProductIncomeStatistic.setHorizontalAlignment(SwingConstants.LEFT);
    bntProductIncomeStatistic.addActionListener(this);
    bntProductIncomeStatistic.setName("bntProductIncomeStatistic");

    JToggleButton bntInvoiceIncome = new JToggleButton(INVOICE_INCOME_TITLE);
    bntInvoiceIncome.setHorizontalAlignment(SwingConstants.LEFT);
    bntInvoiceIncome.addActionListener(this);
    bntInvoiceIncome.setName("bntInvoiceIncome");

    JToggleButton bntWarningProduct = new JToggleButton(WARNING_PRODUCT_TITLE);
    bntWarningProduct.setHorizontalAlignment(SwingConstants.LEFT);
    bntWarningProduct.addActionListener(this);
    bntWarningProduct.setName("bntWarningProductNode");

    manageTaskPane.add(bntProductIncomeStatistic);
    manageTaskPane.add(bntWarningProduct);
    manageTaskPane.add(bntInvoiceIncome);

    buttonGroup.add(bntProductIncomeStatistic);
    buttonGroup.add(bntInvoiceIncome);
    buttonGroup.add(bntWarningProduct);

    manageTaskPane.setCollapsed(false);
    return manageTaskPane;
  }

  private JXTaskPane generateReportTaskPane() {
    JXTaskPane manageTaskPane = new JXTaskPane();
    manageTaskPane.setTitle(ControlConfigUtils.getString("JTree.ReportManagement"));
    manageTaskPane.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.STORE_ICON));

    JToggleButton bntEximport = new JToggleButton(EXIMPORT_TITLE);
    bntEximport.setHorizontalAlignment(SwingConstants.LEFT);
    bntEximport.addActionListener(this);
    bntEximport.setName("bntEximport");
    manageTaskPane.add(bntEximport);

    JToggleButton bntInStore = new JToggleButton(IN_STORE_TITLE);
    bntInStore.setHorizontalAlignment(SwingConstants.LEFT);
    bntInStore.addActionListener(this);
    bntInStore.setName("bntInStore");
    manageTaskPane.add(bntInStore);

    buttonGroup.add(bntEximport);
    buttonGroup.add(bntInStore);
    return manageTaskPane;
  }

  private JToggleButton generateFinalPeriodProceedButton() {
    JToggleButton bntFinalPeriodProceed = new JToggleButton(FINAL_PERIOD_PROCESS_TITLE);
    bntFinalPeriodProceed.setHorizontalAlignment(SwingConstants.LEFT);
    bntFinalPeriodProceed.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.PROCESS_ICON));
    bntFinalPeriodProceed.addActionListener(this);
    bntFinalPeriodProceed.setName("bntFinalPeriodProceed");
    buttonGroup.add(bntFinalPeriodProceed);
    return bntFinalPeriodProceed;
  }
}
