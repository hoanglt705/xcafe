package com.s3s.crm.view.product;

import javax.swing.Icon;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.CrmProductDto;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListServiceView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListCrmProductView extends AListServiceView<CrmProductDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListCrmProductView() {
    super();
  }

  public ListCrmProductView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void loadService() {
    setViewService(CrmContextProvider.getInstance().getCrmProductService());
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    // listDataModel.addColumn("image", ListRendererType.IMAGE);
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("sellPrice", ListRendererType.NUMBER);
  }

  @Override
  protected Class<? extends AbstractEditView<CrmProductDto>> getEditViewClass() {
    return EditCrmProductView.class;
  }
}
