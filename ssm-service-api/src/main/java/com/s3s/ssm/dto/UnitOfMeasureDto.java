package com.s3s.ssm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class UnitOfMeasureDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private String shortName;
  private Float exchangeRate;
  private Boolean isBaseMeasure;
  private UomCategoryDto uomCategory;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public Float getExchangeRate() {
    return exchangeRate;
  }

  public void setExchangeRate(Float exchangeRate) {
    this.exchangeRate = exchangeRate;
  }

  public Boolean getIsBaseMeasure() {
    return isBaseMeasure;
  }

  public void setIsBaseMeasure(Boolean isBaseMeasure) {
    this.isBaseMeasure = isBaseMeasure;
  }

  public UomCategoryDto getUomCategory() {
    return uomCategory;
  }

  public void setUomCategory(UomCategoryDto uomCategory) {
    this.uomCategory = uomCategory;
  }
}
