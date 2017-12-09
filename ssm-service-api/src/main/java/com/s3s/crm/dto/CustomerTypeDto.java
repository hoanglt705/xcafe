package com.s3s.crm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class CustomerTypeDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private Integer minPoint;
  private Integer discountPercent;
  private Integer effectiveTime;
  private Integer pointToRemind;
  private CustomerTypeDto nextCustomerType;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getMinPoint() {
    return minPoint;
  }

  public void setMinPoint(Integer minPoint) {
    this.minPoint = minPoint;
  }

  public Integer getDiscountPercent() {
    return discountPercent;
  }

  public void setDiscountPercent(Integer discountPercent) {
    this.discountPercent = discountPercent;
  }

  public Integer getEffectiveTime() {
    return effectiveTime;
  }

  public void setEffectiveTime(Integer effectiveTime) {
    this.effectiveTime = effectiveTime;
  }

  public Integer getPointToRemind() {
    return pointToRemind;
  }

  public void setPointToRemind(Integer pointToRemind) {
    this.pointToRemind = pointToRemind;
  }

  public CustomerTypeDto getNextCustomerType() {
    return nextCustomerType;
  }

  public void setNextCustomerType(CustomerTypeDto nextCustomerType) {
    this.nextCustomerType = nextCustomerType;
  }

}
