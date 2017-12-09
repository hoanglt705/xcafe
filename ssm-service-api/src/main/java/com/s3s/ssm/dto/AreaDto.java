package com.s3s.ssm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class AreaDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;

  public AreaDto() {
  }

  public AreaDto(long id, String code, String name) {
    setId(id);
    setCode(code);
    setName(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
