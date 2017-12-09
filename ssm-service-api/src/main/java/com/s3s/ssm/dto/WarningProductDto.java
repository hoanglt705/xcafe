package com.s3s.ssm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class WarningProductDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String productCode;
  private String productName;
  private Double quantity;
  private Long minimumQuantity;
  private String uomName;

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  public Long getMinimumQuantity() {
    return minimumQuantity;
  }

  public void setMinimumQuantity(Long minimumQuantity) {
    this.minimumQuantity = minimumQuantity;
  }

  public String getUomName() {
    return uomName;
  }

  public void setUomName(String uomName) {
    this.uomName = uomName;
  }

}
