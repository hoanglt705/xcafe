package com.s3s.ssm.view.cashier.event.area;

import com.s3s.ssm.dto.AreaDto;

public class AreaEvent {
  private final AreaDto area;

  public AreaEvent(AreaDto area) {
    this.area = area;
  }

  public AreaDto getArea() {
    return area;
  }
}
