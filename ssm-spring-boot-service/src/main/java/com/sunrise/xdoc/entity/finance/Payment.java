package com.sunrise.xdoc.entity.finance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.dto.PaymentMode;
import com.s3s.ssm.entity.AbstractActiveCodeOLObject;
import com.sunrise.xdoc.entity.employee.Employee;

@Entity
@Table(name = "finance_payment")
@Inheritance(strategy = InheritanceType.JOINED)
public class Payment extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 4824284933146267482L;
  private PaymentContent paymentContent;
  private Date paymentDate = new Date();
  private Employee staff;
  private PaymentMode paymentMode = PaymentMode.CASH;
  private Long amount;
  private String notes;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "payment_content_id")
  @NotNull
  public PaymentContent getPaymentContent() {
    return paymentContent;
  }

  public void setPaymentContent(PaymentContent paymentContent) {
    this.paymentContent = paymentContent;
  }

  @Column(name = "payment_date")
  @NotNull
  public Date getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "staff_id")
  @NotNull
  public Employee getStaff() {
    return staff;
  }

  public void setStaff(Employee staff) {
    this.staff = staff;
  }

  @Column(name = "payment_mode")
  @Enumerated(EnumType.STRING)
  public PaymentMode getPaymentMode() {
    return paymentMode;
  }

  public void setPaymentMode(PaymentMode paymentMode) {
    this.paymentMode = paymentMode;
  }

  @Column(name = "amount")
  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }

  @Column(name = "notes")
  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}
