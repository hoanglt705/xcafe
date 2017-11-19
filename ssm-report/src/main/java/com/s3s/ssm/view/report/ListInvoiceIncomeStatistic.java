package com.s3s.ssm.view.report;

import java.awt.FlowLayout;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.s3s.ssm.config.ReportContextProvider;
import com.s3s.ssm.dto.report.InvoiceIncomeDto;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.view.list.AListStatisticView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;
import com.s3s.ssm.widget.JVCompoundDatePicker;

public class ListInvoiceIncomeStatistic extends AListStatisticView<InvoiceIncomeDto> {
  private static final long serialVersionUID = 1L;
  private JButton btnSearch;
  private JVCompoundDatePicker coumpoundDatePicker;

  @Override
  protected List<InvoiceIncomeDto> loadData(int firstIndex, int maxResults) {
    Date toDate = coumpoundDatePicker.getToDate();
    Date fromDate = coumpoundDatePicker.getFromDate();
    return ReportContextProvider.getInstance().getReportService()
            .statisticInvoiceIncome(fromDate, toDate);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("createdDate", ListRendererType.DATE);
    listDataModel.addColumn("invoiceCode", ListRendererType.TEXT);
    listDataModel.addColumn("foodTableName", ListRendererType.TEXT);
    listDataModel.addColumn("totalAmount", ListRendererType.NUMBER);
    listDataModel.addColumn("discount", ListRendererType.NUMBER);
    listDataModel.addColumn("income", ListRendererType.NUMBER);
  }

  @Override
  protected JPanel createSearchPanel() {
    btnSearch = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.SEARCH_ICON));
    btnSearch.setText(getMessage("button.text.search"));
    btnSearch.setName("btnSearch");
    btnSearch.addActionListener(e -> {
      refreshAndBackToFirstPage();
    });

    JButton btnExport = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.EXPORT_ICON));
    btnExport.setText(getMessage("default.button.export"));

    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    coumpoundDatePicker = new JVCompoundDatePicker();
    panel.add(coumpoundDatePicker);
    panel.add(btnSearch);
    panel.add(btnExport);

    return panel;
  }

  @Override
  protected void clearCriteria() {
  }
}
