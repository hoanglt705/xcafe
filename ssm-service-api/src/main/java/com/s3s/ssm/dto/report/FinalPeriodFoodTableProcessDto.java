package com.s3s.ssm.dto.report;

import java.io.Serializable;
import java.util.Date;


public class FinalPeriodFoodTableProcessDto implements Serializable{
  private static final long serialVersionUID = 1L;
  
  private Date processingDate = new Date();
  private Long totalAmount;
  private String foodTable;
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
  public String getFoodTable() {
    return foodTable;
  }
  public void setFoodTable(String foodTable) {
    this.foodTable = foodTable;
  }
    
}
