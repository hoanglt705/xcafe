package com.s3s.ssm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class ExportStoreFormDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;

  public ExportStoreFormDto() {
  }

  public ExportStoreFormDto(Long id, String code, String name) {
    setCode(code);
    setId(id);
    setName(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
