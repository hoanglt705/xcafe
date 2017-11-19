package com.s3s.ssm.export.dto;

import net.sf.jsefa.csv.annotation.CsvDataType;
import net.sf.jsefa.csv.annotation.CsvField;

@CsvDataType(defaultPrefix = "ProductTypeExporterDto")
public class ProductTypeExporterDto {
  @CsvField(pos = 1)
  private String code;
  @CsvField(pos = 2)
  private String name;

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

}
