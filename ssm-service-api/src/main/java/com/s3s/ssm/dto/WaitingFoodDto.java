package com.s3s.ssm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class WaitingFoodDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String foodName;
  private Integer quantity;
  private String status;
  private String foodTableName;

  public String getFoodName() {
    return foodName;
  }

  public void setFoodName(String foodName) {
    this.foodName = foodName;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getFoodTableName() {
    return foodTableName;
  }

  public void setFoodTableName(String foodTableName) {
    this.foodTableName = foodTableName;
  }
}
