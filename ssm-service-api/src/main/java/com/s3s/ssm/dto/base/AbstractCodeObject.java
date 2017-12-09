package com.s3s.ssm.dto.base;

import java.io.Serializable;

public abstract class AbstractCodeObject implements ICodeObject, Serializable {
  private static final long serialVersionUID = 4927712229816383377L;
  private long id;
  private String code;
  private boolean active = true;

  public AbstractCodeObject() {
    super();
  }

  public AbstractCodeObject(long id, String code, boolean active) {
    this.id = id;
    this.code = code;
    this.active = active;
  }

  @Override
  public void setId(long id) {
    this.id = id;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return getCode();
  }

  @Override
  public boolean isActive() {
    return active;
  }

  @Override
  public void setActive(boolean isActive) {
    active = isActive;
  }

  @Override
  public int hashCode() {
    return getCode().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof AbstractCodeObject)) {
      return false;
    }
    if (((AbstractCodeObject) obj).getCode() == null || getCode() == null) {
      return false;
    }
    return getCode().equals(((AbstractCodeObject) obj).getCode());
  }
}
