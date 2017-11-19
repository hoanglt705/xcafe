package com.s3s.ssm.view.report.finalprocess;

import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;

import com.s3s.ssm.dto.report.FinalPeriodFoodTableProcessDto;
import com.s3s.ssm.view.list.AListStatisticView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListFinalPeriodFoodTableProcessView extends AListStatisticView<FinalPeriodFoodTableProcessDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  private List<FinalPeriodFoodTableProcessDto> data;

  public ListFinalPeriodFoodTableProcessView() {
    add(contentPane);
    setVisiblePagingNavigator(false);
    getTable().setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    getTable().setShowVerticalLines(true);
    // getTable().setFilterRowVisible(false);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("foodTable", ListRendererType.TEXT);
    listDataModel.addColumn("totalAmount", ListRendererType.NUMBER);
  }

  @Override
  protected List<FinalPeriodFoodTableProcessDto> loadData(int firstIndex, int maxResults) {
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

  public List<FinalPeriodFoodTableProcessDto> getData() {
    return data;
  }

  @SuppressWarnings("unchecked")
  public void setData(List<?> data) {
    this.data = (List<FinalPeriodFoodTableProcessDto>) data;
  }
}
