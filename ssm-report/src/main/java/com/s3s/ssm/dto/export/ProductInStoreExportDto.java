package com.s3s.ssm.dto.export;

import java.math.BigDecimal;

import net.sf.jsefa.common.validator.BigDecimalValidator;
import net.sf.jsefa.csv.annotation.CsvDataType;
import net.sf.jsefa.csv.annotation.CsvField;

@CsvDataType()
public class ProductInStoreExportDto {
  @CsvField(pos = 1)
  private String productCode;
  @CsvField(pos = 2)
  private String productName;
  @CsvField(pos = 3)
  private String unit;
  @CsvField(pos = 4, validatorType = BigDecimalValidator.class)
  private BigDecimal quantity;

  public BigDecimal getQuantity() {
    return quantity;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

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
}
