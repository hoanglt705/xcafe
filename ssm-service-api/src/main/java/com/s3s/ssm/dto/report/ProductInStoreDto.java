package com.s3s.ssm.dto.report;

import java.io.Serializable;

public class ProductInStoreDto implements Serializable {
  private static final long serialVersionUID = 1L;
  private String productCode;
  private String productName;
  private String unit;
  private Double quantity;

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

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
    if (quantity == null) {
      this.quantity = 0d;
    } else {
      this.quantity = quantity;
    }
  }

}
