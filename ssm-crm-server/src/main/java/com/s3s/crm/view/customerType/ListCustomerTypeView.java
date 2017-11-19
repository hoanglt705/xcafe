package com.s3s.crm.view.customerType;

import javax.swing.Icon;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.CustomerTypeDto;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListServiceView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListCustomerTypeView extends AListServiceView<CustomerTypeDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListCustomerTypeView() {
    super();
  }

  public ListCustomerTypeView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void loadService() {
    setViewService(CrmContextProvider.getInstance().getCustomerTypeService());
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("name", ListRendererType.TEXT);
    listDataModel.addColumn("minPoint", ListRendererType.NUMBER);
    listDataModel.addColumn("discountPercent", ListRendererType.NUMBER);
    listDataModel.addColumn("effectiveTime", ListRendererType.NUMBER);
    listDataModel.addColumn("pointToRemind", ListRendererType.NUMBER);
  }

  @Override
  protected Class<? extends AbstractEditView<CustomerTypeDto>> getEditViewClass() {
    return EditCustomerTypeView.class;
  }
}
