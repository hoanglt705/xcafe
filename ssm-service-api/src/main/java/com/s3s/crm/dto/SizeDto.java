package com.s3s.crm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class SizeDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
