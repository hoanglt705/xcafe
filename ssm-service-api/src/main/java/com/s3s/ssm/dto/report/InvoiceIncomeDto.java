package com.s3s.ssm.dto.report;

import java.io.Serializable;
import java.util.Date;

public class InvoiceIncomeDto implements Serializable {
  private static final long serialVersionUID = 1L;
  private String invoiceCode;
  private Date createdDate;
  private String foodTableName;
  private Long totalAmount;
  private Long discount;
  private Long income;

  public String getInvoiceCode() {
    return invoiceCode;
  }

  public void setInvoiceCode(String invoiceCode) {
    this.invoiceCode = invoiceCode;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getFoodTableName() {
    return foodTableName;
  }

  public void setFoodTableName(String foodTableName) {
    this.foodTableName = foodTableName;
  }

  public Long getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Long totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Long getDiscount() {
    return discount;
  }

  public void setDiscount(Long discount) {
    this.discount = discount;
  }

  public Long getIncome() {
    return income;
  }

  public void setIncome(Long income) {
    this.income = income;
  }

}
