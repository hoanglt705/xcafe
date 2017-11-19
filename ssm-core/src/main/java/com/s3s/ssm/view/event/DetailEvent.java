package com.s3s.ssm.view.event;

import java.util.List;

public class DetailEvent {
  private List<?> details;

  public DetailEvent(List<?> details) {
    this.details = details;
  }

  public List<?> getDetails() {
    return details;
  }
}
