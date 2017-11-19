package com.s3s.crm.view;

import java.util.Map;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.SizeDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditSizeView extends AEditServiceView<SizeDto> {
  private static final long serialVersionUID = 1L;

  public EditSizeView(Map<String, Object> entity) {
    super(entity);
  }

  @SuppressWarnings("unused")
  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
  }

  @Override
  public IViewService<SizeDto> getViewService() {
    return CrmContextProvider.getInstance().getSizeService();
  }
}
