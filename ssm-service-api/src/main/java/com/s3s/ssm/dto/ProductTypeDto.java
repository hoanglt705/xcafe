package com.s3s.ssm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class ProductTypeDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private byte[] image;

  public ProductTypeDto() {
    super();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }
}
