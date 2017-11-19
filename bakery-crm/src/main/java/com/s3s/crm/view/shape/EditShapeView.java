package com.s3s.crm.view.shape;

import java.util.Map;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.ShapeDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditShapeView extends AEditServiceView<ShapeDto> {
  private static final long serialVersionUID = 1L;

  public EditShapeView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
  }

  @Override
  public IViewService<ShapeDto> getViewService() {
    return CrmContextProvider.getInstance().getShapeService();
  }
}
