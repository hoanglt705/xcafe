package com.s3s.ssm.view.report;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.s3s.ssm.config.ReportContextProvider;
import com.s3s.ssm.dto.WarningProductDto;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.view.list.AListStatisticView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListWarningProductStatistic extends AListStatisticView<WarningProductDto> {
  private static final long serialVersionUID = 1L;

  @Override
  protected List<WarningProductDto> loadData(int firstIndex, int maxResults) {
    return ReportContextProvider.getInstance().getReportService()
            .statisticWarningProduct(firstIndex, maxResults);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("productCode", ListRendererType.TEXT);
    listDataModel.addColumn("productName", ListRendererType.TEXT);
    listDataModel.addColumn("uomName", ListRendererType.TEXT);
    listDataModel.addColumn("minimumQuantity", ListRendererType.NUMBER);
    listDataModel.addColumn("quantity", ListRendererType.NUMBER);
  }

  @Override
  protected int getTotalPages() {
    return 1;
  }

  @Override
  protected JPanel createSearchPanel() {
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton btnExport = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.EXPORT_ICON));
    btnExport.setText(getMessage("default.button.export"));
    searchPanel.add(btnExport);
    return searchPanel;
  }

  @Override
  protected void clearCriteria() {
  }
}
