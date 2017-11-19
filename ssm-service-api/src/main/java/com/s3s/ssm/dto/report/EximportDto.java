package com.s3s.ssm.dto.report;

import java.io.Serializable;

public class EximportDto implements Serializable {
  private static final long serialVersionUID = 1L;
  private String productName;
  private Double startQuantity;
  private Double exportQuantity;
  private Double importQuantity;
  private Double instoreQuantity;

  public Double getExportQuantity() {
    return exportQuantity;
  }

  public void setExportQuantity(Double exportQuantity) {
    this.exportQuantity = exportQuantity;
  }

  public Double getImportQuantity() {
    return importQuantity;
  }

  public void setImportQuantity(Double importQuantity) {
    this.importQuantity = importQuantity;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Double getStartQuantity() {
    return startQuantity;
  }

  public void setStartQuantity(Double startQuantity) {
    this.startQuantity = startQuantity;
  }

  public Double getInstoreQuantity() {
    return instoreQuantity;
  }

  public void setInstoreQuantity(Double instoreQuantity) {
    this.instoreQuantity = instoreQuantity;
  }

}
