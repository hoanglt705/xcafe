package com.s3s.ssm.export.dto;

import net.sf.jsefa.csv.annotation.CsvDataType;
import net.sf.jsefa.csv.annotation.CsvField;

@CsvDataType()
public class UomCategoryExporterDto {
  @CsvField(pos = 1)
  private String code;
  @CsvField(pos = 2)
  private String name;
  @CsvField(pos = 3)
  private String parentCode;
  @CsvField(pos = 4)
  private String parentName;

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

  public String getParentCode() {
    return parentCode;
  }

  public void setParentCode(String parentCode) {
    this.parentCode = parentCode;
  }

  public String getParentName() {
    return parentName;
  }

  public void setParentName(String parentName) {
    this.parentName = parentName;
  }

}