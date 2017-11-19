package com.s3s.ssm.export.dto;

import net.sf.jsefa.common.validator.LongValidator;
import net.sf.jsefa.csv.annotation.CsvDataType;
import net.sf.jsefa.csv.annotation.CsvField;

@CsvDataType(defaultPrefix = "ProductExporterDto")
public class ProductExporterDto {
  @CsvField(pos = 1, required = true)
  private String code;
  @CsvField(pos = 2)
  private String name;
  @CsvField(pos = 3)
  private ProductTypeExporterDto productTypeDto;
  @CsvField(pos = 4, validatorType = LongValidator.class)
  private Long sellPrice;
  @CsvField(pos = 5, required = true)
  private String uomCode;
  @CsvField(pos = 6)
  private String uomName;

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

  public ProductTypeExporterDto getProductTypeDto() {
    return productTypeDto;
  }

  public void setProductTypeDto(ProductTypeExporterDto productTypeDto) {
    this.productTypeDto = productTypeDto;
  }

  public Long getSellPrice() {
    return sellPrice;
  }

  public void setSellPrice(Long sellPrice) {
    this.sellPrice = sellPrice;
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

}
