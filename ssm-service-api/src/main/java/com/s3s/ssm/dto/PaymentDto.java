package com.s3s.ssm.dto;

import java.util.Date;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class PaymentDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private PaymentContentDto paymentContent;
  private Date paymentDate;
  private EmployeeDto staff;
  private PaymentMode paymentMode = PaymentMode.CASH;
  private Long amount;
  private String notes;

  public PaymentContentDto getPaymentContent() {
    return paymentContent;
  }

  public void setPaymentContent(PaymentContentDto paymentContent) {
    this.paymentContent = paymentContent;
  }

  public Date getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }

  public EmployeeDto getStaff() {
    return staff;
  }

  public void setStaff(EmployeeDto staff) {
    this.staff = staff;
  }

  public PaymentMode getPaymentMode() {
    return paymentMode;
  }

  public void setPaymentMode(PaymentMode paymentMode) {
    this.paymentMode = paymentMode;
  }

  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

}
