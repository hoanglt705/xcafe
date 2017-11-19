package com.s3s.ssm.dto;

public interface IActiveObject extends IIdObject {
  public boolean isActive();

  public void setActive(boolean isActive);
}
