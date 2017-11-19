package com.s3s.crm.dto;

import java.io.Serializable;

public class PotentialCustomerDto implements Serializable {
  private static final long serialVersionUID = 1L;
  private String customerName;
  private String customerType;
  private String nextCustomerType;
  private int currentPoint;
  private int targetPoint;

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCustomerType() {
    return customerType;
  }

  public String getNextCustomerType() {
    return nextCustomerType;
  }

  public void setNextCustomerType(String nextCustomerType) {
    this.nextCustomerType = nextCustomerType;
  }

  public void setCustomerType(String customerType) {
    this.customerType = customerType;
  }

  public int getCurrentPoint() {
    return currentPoint;
  }

  public void setCurrentPoint(int currentPoint) {
    this.currentPoint = currentPoint;
  }

  public int getTargetPoint() {
    return targetPoint;
  }

  public void setTargetPoint(int targetPoint) {
    this.targetPoint = targetPoint;
  }

}
