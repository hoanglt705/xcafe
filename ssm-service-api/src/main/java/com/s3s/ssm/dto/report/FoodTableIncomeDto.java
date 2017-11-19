package com.s3s.ssm.dto.report;

import java.io.Serializable;

public class FoodTableIncomeDto implements Serializable {
  private static final long serialVersionUID = 1L;
  private String foodTableCode;
  private String foodTableName;
  private Long totalAmt = 0L;

  public FoodTableIncomeDto(String foodTableCode, String foodTableName, Long totalAmt) {
    this.foodTableCode = foodTableCode;
    this.foodTableName = foodTableName;
    this.totalAmt = totalAmt;
  }

  public String getFoodTableCode() {
    return foodTableCode;
  }

  public void setFoodTableCode(String foodTableCode) {
    this.foodTableCode = foodTableCode;
  }

  public String getFoodTableName() {
    return foodTableName;
  }

  public void setFoodTableName(String foodTableName) {
    this.foodTableName = foodTableName;
  }

  public Long getTotalAmt() {
    return totalAmt;
  }

  public void setTotalAmt(Long totalAmt) {
    this.totalAmt = totalAmt;
  }
}
