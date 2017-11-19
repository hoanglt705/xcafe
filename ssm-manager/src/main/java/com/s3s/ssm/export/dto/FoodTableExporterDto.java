package com.s3s.ssm.export.dto;

import net.sf.jsefa.csv.annotation.CsvDataType;
import net.sf.jsefa.csv.annotation.CsvField;

@CsvDataType()
public class FoodTableExporterDto {
  @CsvField(pos = 1)
  private String code;
  @CsvField(pos = 2)
  private String name;
  @CsvField(pos = 3)
  private Integer seatNum;
  @CsvField(pos = 4)
  private AreaExporterDto area;

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

  public Integer getSeatNum() {
    return seatNum;
  }

  public void setSeatNum(Integer seatNum) {
    this.seatNum = seatNum;
  }

  public AreaExporterDto getArea() {
    return area;
  }

  public void setArea(AreaExporterDto area) {
    this.area = area;
  }

}
