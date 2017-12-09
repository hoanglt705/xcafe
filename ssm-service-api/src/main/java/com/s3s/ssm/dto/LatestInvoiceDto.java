package com.s3s.ssm.dto;

import java.util.Date;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class LatestInvoiceDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String foodTable;
  private Date createdDate;
  private Long amount;
  private Long income;

  public String getFoodTable() {
    return foodTable;
  }

  public void setFoodTable(String foodTable) {
    this.foodTable = foodTable;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }

  public Long getIncome() {
    return income;
  }

  public void setIncome(Long income) {
    this.income = income;
  }

}
