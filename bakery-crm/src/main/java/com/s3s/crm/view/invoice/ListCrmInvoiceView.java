package com.s3s.crm.view.invoice;

import javax.swing.Icon;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.CrmInvoiceDto;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListServiceView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListCrmInvoiceView extends AListServiceView<CrmInvoiceDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListCrmInvoiceView() {
    super();
  }

  public ListCrmInvoiceView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("createdDate", ListRendererType.DATE);
    listDataModel.addColumn("customer.name", ListRendererType.TEXT);
    listDataModel.addColumn("borrowCard", ListRendererType.BOOLEAN);
    listDataModel.addColumn("totalAmount", ListRendererType.NUMBER);
    listDataModel.addColumn("paymentAmount", ListRendererType.NUMBER);
  }

  @Override
  protected Class<? extends AbstractEditView<CrmInvoiceDto>> getEditViewClass() {
    return EditCrmInvoiceView.class;
  }

  @Override
  protected void loadService() {
    setViewService(CrmContextProvider.getInstance().getCrmInvoiceService());
  }

}
