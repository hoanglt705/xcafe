package com.s3s.ssm.view.cashier.event.invoice;

import com.s3s.ssm.dto.InvoiceDetailDto;

public class AddQuantityEvent {
  private InvoiceDetailDto dto;

  public AddQuantityEvent(InvoiceDetailDto dto) {
    this.dto = dto;
  }

  public InvoiceDetailDto getDto() {
    return dto;
  }
}
