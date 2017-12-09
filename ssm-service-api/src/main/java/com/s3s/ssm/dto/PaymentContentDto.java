package com.s3s.ssm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class PaymentContentDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private PaymentType paymentType;
  private PaymentContentDto parent;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(PaymentType paymentType) {
    this.paymentType = paymentType;
  }

  public PaymentContentDto getParent() {
    return parent;
  }

  public void setParent(PaymentContentDto parent) {
    this.parent = parent;
  }

}
