package com.s3s.ssm.entity;

public interface IActiveObject extends IIdObject {
  public boolean isActive();

  public void setActive(boolean isActive);
}
