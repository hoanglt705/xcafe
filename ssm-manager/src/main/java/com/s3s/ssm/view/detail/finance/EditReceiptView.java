package com.s3s.ssm.view.detail.finance;

import java.util.Map;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.PaymentDto;
import com.s3s.ssm.service.IViewService;

public class EditReceiptView extends EditPaymentView {
  private static final long serialVersionUID = 1L;

  public EditReceiptView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  public IViewService<PaymentDto> getViewService() {
    return ManagerContextProvider.getInstance().getReceiptService();
  }
}
