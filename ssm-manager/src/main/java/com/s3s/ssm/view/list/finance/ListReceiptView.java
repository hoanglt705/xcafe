package com.s3s.ssm.view.list.finance;

import com.s3s.ssm.config.ManagerContextProvider;

public class ListReceiptView extends ListPaymentView {
  private static final long serialVersionUID = 1L;

  @Override
  protected void loadService() {
    setViewService(ManagerContextProvider.getInstance().getReceiptService());
  }
}
