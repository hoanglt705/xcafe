package com.s3s.crm.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.s3s.ssm.dto.InvoiceDetailDto;
import com.s3s.ssm.dto.InvoiceStatus;
import com.s3s.ssm.dto.base.AbstractCodeObject;

public class InvoiceDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private Date createdDate;
  private Long totalAmount = 0L;
  private Long totalReturnAmount = 0L;
  private Long totalPaymentAmount = 0L;
  private Long discount = 0L;
  private Long vatTax = 0L;
  private int vatPercent = 0;
  private Long income;
  private List<InvoiceDetailDto> invoiceDetails = new ArrayList<>();
  private InvoiceStatus invoiceStatus;

  public Long getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Long totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Long getTotalReturnAmount() {
    return totalReturnAmount;
  }

  public void setTotalReturnAmount(Long totalReturnAmount) {
    this.totalReturnAmount = totalReturnAmount;
  }

  public Long getTotalPaymentAmount() {
    return totalPaymentAmount;
  }

  public void setTotalPaymentAmount(Long totalPaymentAmount) {
    this.totalPaymentAmount = totalPaymentAmount;
  }

  public Long getDiscount() {
    return discount;
  }

  public void setDiscount(Long discount) {
    this.discount = discount;
  }

  public Long getVatTax() {
    return vatTax;
  }

  public void setVatTax(Long vatTax) {
    this.vatTax = vatTax;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public List<InvoiceDetailDto> getInvoiceDetails() {
    return invoiceDetails;
  }

  public void setInvoiceDetails(List<InvoiceDetailDto> invoiceDetails) {
    this.invoiceDetails = invoiceDetails;
  }

  public InvoiceStatus getInvoiceStatus() {
    return invoiceStatus;
  }

  public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
    this.invoiceStatus = invoiceStatus;
  }

  public Long getIncome() {
    return income;
  }

  public void setIncome(Long income) {
    this.income = income;
  }

  public int getVatPercent() {
    return vatPercent;
  }

  public void setVatPercent(int vatPercent) {
    this.vatPercent = vatPercent;
  }
}
