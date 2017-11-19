package com.s3s.crm.view.materialType;

import java.util.Map;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.MaterialTypeDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditMaterialTypeView extends AEditServiceView<MaterialTypeDto> {
  private static final long serialVersionUID = 1L;

  public EditMaterialTypeView(Map<String, Object> entity) {
    super(entity);
  }

  @SuppressWarnings("unused")
  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
  }

  @Override
  public IViewService<MaterialTypeDto> getViewService() {
    return CrmContextProvider.getInstance().getMaterialTypeService();
  }
}
