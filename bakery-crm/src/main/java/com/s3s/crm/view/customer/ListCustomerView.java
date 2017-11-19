package com.s3s.crm.view.customer;

import javax.swing.Icon;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.CustomerDto;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListServiceView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListCustomerView extends AListServiceView<CustomerDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListCustomerView() {
    super();
  }

  public ListCustomerView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void loadService() {
    setViewService(CrmContextProvider.getInstance().getCustomerService());
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("name", ListRendererType.TEXT);
    listDataModel.addColumn("customerType.name", ListRendererType.TEXT);
    listDataModel.addColumn("point", ListRendererType.TEXT);
    listDataModel.addColumn("birthday", ListRendererType.TEXT);
    listDataModel.addColumn("phoneNumber", ListRendererType.TEXT);
  }

  @Override
  protected Class<? extends AbstractEditView<CustomerDto>> getEditViewClass() {
    return EditCustomerView.class;
  }
}
