package com.s3s.ssm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class RoleDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;

  public RoleDto() {
  }

  public RoleDto(Long id, String code, String name) {
    this.setId(id);
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
