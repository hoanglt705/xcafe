package com.s3s.ssm.view.event;

import com.s3s.ssm.dto.InvoiceDto;

public class BackToControlPanelEvent {
  private InvoiceDto invoice;

  public BackToControlPanelEvent(InvoiceDto activeInvoice) {
    this.invoice = activeInvoice;
  }

  public InvoiceDto getInvoice() {
    return invoice;
  }

}
