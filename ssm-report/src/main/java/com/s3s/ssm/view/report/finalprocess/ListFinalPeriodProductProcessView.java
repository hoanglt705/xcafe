package com.s3s.ssm.view.report.finalprocess;

import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;

import com.s3s.ssm.dto.report.FinalPeriodProductProcessDto;
import com.s3s.ssm.view.list.AListStatisticView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListFinalPeriodProductProcessView extends AListStatisticView<FinalPeriodProductProcessDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  private List<FinalPeriodProductProcessDto> data;

  public ListFinalPeriodProductProcessView() {
    add(contentPane);
    setVisiblePagingNavigator(false);
    getTable().setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    getTable().setShowVerticalLines(true);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("productName", ListRendererType.TEXT);
    listDataModel.addColumn("uomName", ListRendererType.TEXT);
    listDataModel.addColumn("importQuantity", ListRendererType.NUMBER);
    listDataModel.addColumn("exportQuantity", ListRendererType.NUMBER);
    listDataModel.addColumn("soldQuantity", ListRendererType.NUMBER);
    listDataModel.addColumn("quantityInStock", ListRendererType.NUMBER);
  }

  @Override
  protected List<FinalPeriodProductProcessDto> loadData(int firstIndex, int maxResults) {
    return getData() != null ? getData() : Collections.emptyList();
  }

  @Override
  protected int getTotalPages() {
    return 1;
  }

  @Override
  protected JComponent createSearchPanel() {
    return null;
  }

  @Override
  protected void clearCriteria() {

  }

  public List<FinalPeriodProductProcessDto> getData() {
    return data;
  }

  @SuppressWarnings("unchecked")
  public void setData(List<?> data) {
    this.data = (List<FinalPeriodProductProcessDto>) data;
  }
}
