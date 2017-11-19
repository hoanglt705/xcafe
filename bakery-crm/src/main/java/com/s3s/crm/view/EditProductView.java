package com.s3s.crm.view;

import java.util.Map;

import com.s3s.crm.dto.ConfigDto;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;

public class EditProductView extends AEditServiceView<ConfigDto> {
  private static final long serialVersionUID = 1L;

  public EditProductView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {

  }

}
