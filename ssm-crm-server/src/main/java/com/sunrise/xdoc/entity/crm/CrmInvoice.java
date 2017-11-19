package com.sunrise.xdoc.entity.crm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "crm_invoice")
@Inheritance(strategy = InheritanceType.JOINED)
public class CrmInvoice extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private Date createdDate = new Date();
  private Customer customer = new Customer();
  private boolean borrowCard;
  private Long totalAmount;
  private Long discountAmount;
  private Long paymentAmount;
  private Promotion promotion;
  private Integer vatPercent;
  private List<CrmInvoiceDetail> invoiceDetails = new ArrayList<>();

  @Column(name = "created_date")
  @NotNull
  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  @ManyToOne
  @JoinColumn(name = "customer_id")
  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  @Column(name = "borrow_card")
  public boolean isBorrowCard() {
    return borrowCard;
  }

  public void setBorrowCard(boolean borrowCard) {
    this.borrowCard = borrowCard;
  }

  @Column(name = "total_amount")
  public Long getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Long totalAmount) {
    this.totalAmount = totalAmount;
  }

  @Column(name = "discount_amount")
  public Long getDiscountAmount() {
    return discountAmount;
  }

  public void setDiscountAmount(Long discountAmount) {
    this.discountAmount = discountAmount;
  }

  @Column(name = "payment_amount")
  public Long getPaymentAmount() {
    return paymentAmount;
  }

  public void setPaymentAmount(Long paymentAmount) {
    this.paymentAmount = paymentAmount;
  }

  @ManyToOne
  @JoinColumn(name = "promotion_id")
  public Promotion getPromotion() {
    return promotion;
  }

  public void setPromotion(Promotion promotion) {
    this.promotion = promotion;
  }

  @Column(name = "vat_percent")
  public Integer getVatPercent() {
    return vatPercent;
  }

  public void setVatPercent(Integer vatPercent) {
    this.vatPercent = vatPercent;
  }

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "invoice")
  public List<CrmInvoiceDetail> getInvoiceDetails() {
    return invoiceDetails;
  }

  public void setInvoiceDetails(List<CrmInvoiceDetail> invoiceDetails) {
    this.invoiceDetails = invoiceDetails;
  }

}
