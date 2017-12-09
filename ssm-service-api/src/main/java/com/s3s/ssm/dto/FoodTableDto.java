package com.s3s.ssm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class FoodTableDto extends AbstractCodeObject {
  private static final long serialVersionUID = -5242913287647085942L;
  private String name;
  private Integer seatNum;
  private AreaDto area;

  public FoodTableDto() {
    super();
  }

  public FoodTableDto(long id, String code, boolean active) {
    super(id, code, active);
  }

  public FoodTableDto(String code, String name) {
    setCode(code);
    setName(name);
  }

  public Integer getSeatNum() {
    return seatNum;
  }

  public void setSeatNum(Integer seatNum) {
    this.seatNum = seatNum;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AreaDto getArea() {
    return area;
  }

  public void setArea(AreaDto area) {
    this.area = area;
  }

  @Override
  public String toString() {
    return name;
  }

}
