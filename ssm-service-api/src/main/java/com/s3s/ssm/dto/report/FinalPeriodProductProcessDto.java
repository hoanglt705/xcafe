package com.s3s.ssm.dto.report;

import java.io.Serializable;
import java.util.Date;

public class FinalPeriodProductProcessDto implements Serializable {
  private static final long serialVersionUID = 1L;

  private Date processingDate = new Date();
  private Long totalAmount;
  private String productName;
  private String uomName;
  private Double quantityInStock;
  private Integer importQuantity;
  private Integer exportQuantity;
  private Double soldQuantity;

  public Date getProcessingDate() {
    return processingDate;
  }

  public void setProcessingDate(Date processingDate) {
    this.processingDate = processingDate;
  }

  public Long getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Long totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getUomName() {
    return uomName;
  }

  public void setUomName(String uomName) {
    this.uomName = uomName;
  }

  public Double getQuantityInStock() {
    return quantityInStock;
  }

  public void setQuantityInStock(Double quantityInStock) {
    this.quantityInStock = quantityInStock;
  }

  public Integer getImportQuantity() {
    return importQuantity;
  }

  public void setImportQuantity(Integer importQuantity) {
    this.importQuantity = importQuantity;
  }

  public Integer getExportQuantity() {
    return exportQuantity;
  }

  public void setExportQuantity(Integer exportQuantity) {
    this.exportQuantity = exportQuantity;
  }

  public Double getSoldQuantity() {
    return soldQuantity;
  }

  public void setSoldQuantity(Double soldQuantity) {
    this.soldQuantity = soldQuantity;
  }

}
