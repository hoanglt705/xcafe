package com.s3s.ssm.view.event;

import com.s3s.ssm.dto.SupplierDto;

public class SupplierChangeEvent {
  private SupplierDto suppler;

  public SupplierChangeEvent(SupplierDto suppler) {
    this.suppler = suppler;
  }

  public SupplierDto getSuppler() {
    return suppler;
  }
}
