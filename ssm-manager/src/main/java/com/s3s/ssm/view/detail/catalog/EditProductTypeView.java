package com.s3s.ssm.view.detail.catalog;

import java.util.Map;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.ProductTypeDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditProductTypeView extends AEditServiceView<ProductTypeDto> {
  private static final long serialVersionUID = 728867266827208141L;

  public EditProductTypeView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("image", DetailFieldType.IMAGE);
  }

  @Override
  protected String getDefaultTitle(ProductTypeDto entity) {
    return entity.getName();
  }

  @Override
  public IViewService<ProductTypeDto> getViewService() {
    return ManagerContextProvider.getInstance().getProductTypeService();
  }
}
