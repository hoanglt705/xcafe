package com.s3s.crm.view.materialType;

import javax.swing.Icon;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.MaterialTypeDto;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListServiceView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListMaterialTypeView extends AListServiceView<MaterialTypeDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListMaterialTypeView() {
    super();
  }

  public ListMaterialTypeView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void loadService() {
    setViewService(CrmContextProvider.getInstance().getMaterialTypeService());
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("name", ListRendererType.TEXT);
  }

  @Override
  protected Class<? extends AbstractEditView<MaterialTypeDto>> getEditViewClass() {
    return EditMaterialTypeView.class;
  }
}
