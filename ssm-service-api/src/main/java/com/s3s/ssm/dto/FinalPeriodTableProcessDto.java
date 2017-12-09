package com.s3s.ssm.dto;

import java.util.Date;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class FinalPeriodTableProcessDto extends AbstractCodeObject {
  private static final long serialVersionUID = -4981758112218834552L;
  private String foodTableCode;
  private Date processingDate;
  private Long totalAmount;

  public FinalPeriodTableProcessDto() {

  }

  public FinalPeriodTableProcessDto(String foodTableCode, Date processingDate, Long totalAmount) {
    this.foodTableCode = foodTableCode;
    this.processingDate = processingDate;
    this.totalAmount = totalAmount;
  }

  public Date getProcessingDate() {
    return processingDate;
  }

  public void setProcessingDate(Date processingDate) {
    this.processingDate = processingDate;
  }

  public Long getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Long totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getFoodTableCode() {
    return foodTableCode;
  }

  public void setFoodTableCode(String foodTableCode) {
    this.foodTableCode = foodTableCode;
  }

}
