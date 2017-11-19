package com.s3s.ssm.view.cashier.event.invoice;

import com.s3s.ssm.dto.InvoiceDetailDto;

public class DeleteInvoiceDetailEvent {

  private InvoiceDetailDto dto;

  public DeleteInvoiceDetailEvent(InvoiceDetailDto dto) {
    this.dto = dto;
  }

  public InvoiceDetailDto getDto() {
    return dto;
  }
}
