package com.s3s.crm.view;

import java.util.Map;

import com.s3s.crm.dto.InvoiceDto;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;

public class EditInvoiceView extends AEditServiceView<InvoiceDto> {
  private static final long serialVersionUID = 1L;

  public EditInvoiceView(Map<String, Object> entity) {
    super(entity);
  }

  @SuppressWarnings("unused")
  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {

  }

}
