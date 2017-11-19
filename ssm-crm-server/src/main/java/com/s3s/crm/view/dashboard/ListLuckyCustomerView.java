package com.s3s.crm.view.dashboard;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.LuckyCustomerDto;
import com.s3s.ssm.view.list.AListStatisticView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListLuckyCustomerView extends AListStatisticView<LuckyCustomerDto> {
  private static final long serialVersionUID = 1L;

  public ListLuckyCustomerView() {
    super();
    remove(tabbedPane);
    add(contentPane);
    setVisiblePagingNavigator(false);
    getTable().setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    getTable().setShowVerticalLines(true);
    getTable().setFilterRowVisible(false);
  }

  @Override
  protected JComponent createSearchPanel() {
    return null;
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("customerName", ListRendererType.TEXT);
    listDataModel.addColumn("customerType", ListRendererType.TEXT);
    listDataModel.addColumn("birthday", ListRendererType.DATE);
    listDataModel.addColumn("point", ListRendererType.NUMBER);
  }

  @Override
  protected List<LuckyCustomerDto> loadData(int page, int number) {
    return CrmContextProvider.getInstance().getCustomerService().findLuckyCustomerDto();
  }
}
