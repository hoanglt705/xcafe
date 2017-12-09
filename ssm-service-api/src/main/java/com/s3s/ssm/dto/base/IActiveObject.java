package com.s3s.ssm.dto.base;

public interface IActiveObject extends IIdObject {
  public boolean isActive();

  public void setActive(boolean isActive);
}
