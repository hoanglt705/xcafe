package com.s3s.ssm.view.registry;

import java.util.Map;

import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

class EditRegistryView extends AEditServiceView<RegistryDto> {
  private static final long serialVersionUID = -3453036965884985713L;

  public EditRegistryView(Map<String, Object> entity) {
    super(entity);
    setVisibleToolBar(false);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).width(300);
    detailDataModel.addAttribute("company", DetailFieldType.TEXTBOX).width(300);
    detailDataModel.addAttribute("machineCode", DetailFieldType.TEXTBOX).width(300);
    detailDataModel.addAttribute("registryCode", DetailFieldType.TEXTBOX).width(300);
  }

  @Override
  protected String getDefaultTitle(RegistryDto entity) {
    return entity.getCode();
  }

  @Override
  public IViewService<RegistryDto> getViewService() {
    return new RegistryServiceImpl();
  }
}