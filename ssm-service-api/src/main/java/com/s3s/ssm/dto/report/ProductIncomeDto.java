package com.s3s.ssm.dto.report;

import java.io.Serializable;

public class ProductIncomeDto implements Serializable {
  private static final long serialVersionUID = 1L;
  private String productCode;
  private String productName;
  private String unit;
  private Integer quantity;
  private Long sellPrice;
  private Long sellTotalAmt;
  private Long importPrice;
  private Long importTotalAmt;
  private Long interestAmt;
  private Float incomePercent;

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public Long getSellPrice() {
    return sellPrice;
  }

  public void setSellPrice(Long sellPrice) {
    this.sellPrice = sellPrice;
  }

  public Long getSellTotalAmt() {
    return sellTotalAmt;
  }

  public void setSellTotalAmt(Long sellTotalAmt) {
    this.sellTotalAmt = sellTotalAmt;
  }

  public Long getImportPrice() {
    return importPrice;
  }

  public void setImportPrice(Long importPrice) {
    this.importPrice = importPrice;
  }

  public Long getImportTotalAmt() {
    return importTotalAmt;
  }

  public void setImportTotalAmt(Long importTotalAmt) {
    this.importTotalAmt = importTotalAmt;
  }

  public Float getIncomePercent() {
    return incomePercent;
  }

  public void setIncomePercent(Float incomePercent) {
    this.incomePercent = incomePercent;
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

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    if (quantity == null) {
      this.quantity = quantity;
    } else {
      this.quantity = quantity;
    }
  }

  public Long getInterestAmt() {
    return interestAmt;
  }

  public void setInterestAmt(Long interestAmt) {
    this.interestAmt = interestAmt;
  }
}
