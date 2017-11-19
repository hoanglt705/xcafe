package com.sunrise.xdoc.entity.crm;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "crm_customer_type")
public class CustomerType extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private Integer minPoint;
  private Integer discountPercent;
  private Integer effectiveTime;
  private Integer pointToRemind;
  private CustomerType nextCustomerType;

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

  @ManyToOne
  @JoinColumn(name = "next_customer_type_id")
  public CustomerType getNextCustomerType() {
    return nextCustomerType;
  }

  public void setNextCustomerType(CustomerType nextCustomerType) {
    this.nextCustomerType = nextCustomerType;
  }
}
