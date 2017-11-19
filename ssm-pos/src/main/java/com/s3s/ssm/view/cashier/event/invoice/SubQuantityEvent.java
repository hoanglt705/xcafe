package com.s3s.ssm.view.cashier.event.invoice;

import com.s3s.ssm.dto.InvoiceDetailDto;

public class SubQuantityEvent {

  private InvoiceDetailDto dto;

  public SubQuantityEvent(InvoiceDetailDto dto) {
    this.dto = dto;
  }

  public InvoiceDetailDto getDto() {
    return dto;
  }
}
