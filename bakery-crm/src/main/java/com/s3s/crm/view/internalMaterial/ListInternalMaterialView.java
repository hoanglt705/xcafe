package com.s3s.crm.view.internalMaterial;

import javax.swing.Icon;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.InternalMaterialDto;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListServiceView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListInternalMaterialView extends AListServiceView<InternalMaterialDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListInternalMaterialView() {
    super();
  }

  public ListInternalMaterialView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void loadService() {
    setViewService(CrmContextProvider.getInstance().getInternalMaterialService());
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("name", ListRendererType.TEXT);
  }

  @Override
  protected Class<? extends AbstractEditView<InternalMaterialDto>> getEditViewClass() {
    return EditInternalMaterialView.class;
  }
}
