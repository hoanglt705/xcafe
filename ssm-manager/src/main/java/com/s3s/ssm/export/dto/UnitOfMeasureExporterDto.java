package com.s3s.ssm.export.dto;

import java.math.BigDecimal;

import net.sf.jsefa.common.validator.BigDecimalValidator;
import net.sf.jsefa.csv.annotation.CsvDataType;
import net.sf.jsefa.csv.annotation.CsvField;

@CsvDataType()
public class UnitOfMeasureExporterDto {
  @CsvField(pos = 1, required = true)
  private String code;
  @CsvField(pos = 2, required = true)
  private String name;
  @CsvField(pos = 3)
  private String shortName;
  @CsvField(pos = 4, validatorType = BigDecimalValidator.class)
  private BigDecimal exchangeRate;
  @CsvField(pos = 5)
  private Boolean isBaseMeasure;
  @CsvField(pos = 6)
  private UomCategoryExporterDto uomCateDto;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

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

  public BigDecimal getExchangeRate() {
    return exchangeRate;
  }

  public void setExchangeRate(BigDecimal exchangeRate) {
    this.exchangeRate = exchangeRate;
  }

  public Boolean getIsBaseMeasure() {
    return isBaseMeasure;
  }

  public void setIsBaseMeasure(Boolean isBaseMeasure) {
    this.isBaseMeasure = isBaseMeasure;
  }

  public UomCategoryExporterDto getUomCateDto() {
    return uomCateDto;
  }

  public void setUomCateDto(UomCategoryExporterDto uomCateDto) {
    this.uomCateDto = uomCateDto;
  }

}
