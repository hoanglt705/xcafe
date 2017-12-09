package com.s3s.ssm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class UomCategoryDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private UomCategoryDto parent;

  public UomCategoryDto() {
    super();
  }

  public UomCategoryDto(long id, String code, boolean active) {
    super(id, code, active);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UomCategoryDto getParent() {
    return parent;
  }

  public void setParent(UomCategoryDto parent) {
    this.parent = parent;
  }
}
