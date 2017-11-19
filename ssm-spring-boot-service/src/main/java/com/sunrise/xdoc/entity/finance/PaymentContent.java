package com.sunrise.xdoc.entity.finance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.dto.PaymentType;
import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "finace_payment_content")
public class PaymentContent extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = -2057055414452621087L;
  private String name;
  private PaymentType paymentType;
  private PaymentContent parent;

  @Column(name = "name", length = 128)
  @NotBlank
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "payment_type")
  @Enumerated(EnumType.STRING)
  public PaymentType getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(PaymentType paymentType) {
    this.paymentType = paymentType;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "parent_id")
  public PaymentContent getParent() {
    return parent;
  }

  public void setParent(PaymentContent parent) {
    this.parent = parent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return name;
  }

}
