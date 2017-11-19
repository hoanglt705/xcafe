package com.s3s.crm.view.customerCard;

import javax.swing.Icon;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.CustomerCardDto;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListServiceView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListCustomerCardView extends AListServiceView<CustomerCardDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListCustomerCardView() {
    super();
  }

  public ListCustomerCardView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void loadService() {
    setViewService(CrmContextProvider.getInstance().getCustomerCardService());
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("createdDate", ListRendererType.DATE);
    listDataModel.addColumn("customer.name", ListRendererType.TEXT);
  }

  @Override
  protected Class<? extends AbstractEditView<CustomerCardDto>> getEditViewClass() {
    return EditCustomerCardView.class;
  }
}
