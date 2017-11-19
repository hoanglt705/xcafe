package com.s3s.ssm.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractActiveIdOLObject extends AbstractIdOLObject implements IActiveObject {
  private static final long serialVersionUID = 1L;
  private Boolean active = true;

  @Override
  @Column(name = "active")
  public boolean isActive() {
    return active;
  }

  @Override
  public void setActive(boolean isActive) {
    active = isActive;
  }
}
