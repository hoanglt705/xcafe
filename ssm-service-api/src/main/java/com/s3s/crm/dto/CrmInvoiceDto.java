package com.s3s.crm.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class CrmInvoiceDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private Date createdDate;
  private CustomerDto customer = new CustomerDto();
  private boolean borrowCard;
  private Long totalAmount;
  private Long discountAmount;
  private Long paymentAmount;
  private PromotionDto promotion;
  private Integer vatPercent;
  private List<CrmInvoiceDetailDto> invoiceDetails = new ArrayList<>();

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public boolean isBorrowCard() {
    return borrowCard;
  }

  public void setBorrowCard(boolean borrowCard) {
    this.borrowCard = borrowCard;
  }

  public List<CrmInvoiceDetailDto> getInvoiceDetails() {
    return invoiceDetails;
  }

  public void setInvoiceDetails(List<CrmInvoiceDetailDto> invoiceDetails) {
    this.invoiceDetails = invoiceDetails;
  }

  public CustomerDto getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerDto customer) {
    this.customer = customer;
  }

  public Long getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Long totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Long getDiscountAmount() {
    return discountAmount;
  }

  public void setDiscountAmount(Long discountAmount) {
    this.discountAmount = discountAmount;
  }

  public Long getPaymentAmount() {
    return paymentAmount;
  }

  public void setPaymentAmount(Long paymentAmount) {
    this.paymentAmount = paymentAmount;
  }

  public Integer getVatPercent() {
    return vatPercent;
  }

  public void setVatPercent(Integer vatPercent) {
    this.vatPercent = vatPercent;
  }

  public PromotionDto getPromotion() {
    return promotion;
  }

  public void setPromotion(PromotionDto promotion) {
    this.promotion = promotion;
  }
}
