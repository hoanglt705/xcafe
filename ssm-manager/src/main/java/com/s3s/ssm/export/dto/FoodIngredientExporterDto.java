package com.s3s.ssm.export.dto;

import net.sf.jsefa.common.validator.IntegerValidator;
import net.sf.jsefa.common.validator.LongValidator;
import net.sf.jsefa.csv.annotation.CsvDataType;
import net.sf.jsefa.csv.annotation.CsvField;

@CsvDataType(defaultPrefix = "Ingredient")
public class FoodIngredientExporterDto {
  @CsvField(pos = 1)
  private String materialCode;
  @CsvField(pos = 2)
  private String materialName;
  @CsvField(pos = 3)
  private String uomCode;
  @CsvField(pos = 4)
  private String uomName;
  @CsvField(pos = 5, validatorType = IntegerValidator.class)
  private Integer quantity = 0;
  @CsvField(pos = 6, validatorType = LongValidator.class)
  private Long unitPrice = 0L;
  @CsvField(pos = 7, validatorType = LongValidator.class)
  private Long subPriceTotal = 0L;

  public String getMaterialCode() {
    return materialCode;
  }

  public void setMaterialCode(String materialCode) {
    this.materialCode = materialCode;
  }

  public String getUomCode() {
    return uomCode;
  }

  public void setUomCode(String uomCode) {
    this.uomCode = uomCode;
  }

  public String getUomName() {
    return uomName;
  }

  public void setUomName(String uomName) {
    this.uomName = uomName;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Long getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Long unitPrice) {
    this.unitPrice = unitPrice;
  }

  public Long getSubPriceTotal() {
    return subPriceTotal;
  }

  public void setSubPriceTotal(Long subPriceTotal) {
    this.subPriceTotal = subPriceTotal;
  }

  public String getMaterialName() {
    return materialName;
  }

  public void setMaterialName(String materialName) {
    this.materialName = materialName;
  }

}
