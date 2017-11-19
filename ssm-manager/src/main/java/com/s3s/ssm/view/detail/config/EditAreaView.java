package com.s3s.ssm.view.detail.config;

import java.util.Map;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.AreaDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditAreaView extends AEditServiceView<AreaDto> {
  private static final long serialVersionUID = 728867266827208141L;

  public EditAreaView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
  }

  @Override
  protected String getDefaultTitle(AreaDto entity) {
    return entity.getName();
  }

  @Override
  public IViewService<AreaDto> getViewService() {
    return ManagerContextProvider.getInstance().getAreaService();
  }
}
