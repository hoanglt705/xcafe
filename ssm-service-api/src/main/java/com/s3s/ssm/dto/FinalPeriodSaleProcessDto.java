package com.s3s.ssm.dto;

import java.util.Date;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class FinalPeriodSaleProcessDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private Date processingDate;
  private Long totalAmount;

  private Long saleTotal;
  private Long thisYearSaleTotal;
  private Long invoiceTotal;
  private Long thisYearInvoiceTotal;

  public Date getProcessingDate() {
    return processingDate;
  }

  public void setProcessingDate(Date processingDate) {
    this.processingDate = processingDate;
  }

  public Long getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Long totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Long getSaleTotal() {
    return saleTotal;
  }

  public void setSaleTotal(Long saleTotal) {
    this.saleTotal = saleTotal;
  }

  public Long getThisYearSaleTotal() {
    return thisYearSaleTotal;
  }

  public void setThisYearSaleTotal(Long thisYearSaleTotal) {
    this.thisYearSaleTotal = thisYearSaleTotal;
  }

  public Long getInvoiceTotal() {
    return invoiceTotal;
  }

  public void setInvoiceTotal(Long invoiceTotal) {
    this.invoiceTotal = invoiceTotal;
  }

  public Long getThisYearInvoiceTotal() {
    return thisYearInvoiceTotal;
  }

  public void setThisYearInvoiceTotal(Long thisYearInvoiceTotal) {
    this.thisYearInvoiceTotal = thisYearInvoiceTotal;
  }

}
