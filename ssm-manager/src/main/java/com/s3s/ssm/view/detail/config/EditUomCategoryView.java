package com.s3s.ssm.view.detail.config;

import java.util.List;
import java.util.Map;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.UomCategoryDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditUomCategoryView extends AEditServiceView<UomCategoryDto> {
  private static final long serialVersionUID = 1L;

  public EditUomCategoryView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    List<UomCategoryDto> suggestParent = ManagerContextProvider.getInstance().getUomCategoryService()
            .getSuggestParent(entity);
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("parent", DetailFieldType.DROPDOWN).setDataList(suggestParent);
  }

  @Override
  public IViewService<UomCategoryDto> getViewService() {
    return ManagerContextProvider.getInstance().getUomCategoryService();
  }
}
