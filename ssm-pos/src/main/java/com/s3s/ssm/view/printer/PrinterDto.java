package com.s3s.ssm.view.printer;

import java.io.Serializable;

import com.s3s.ssm.dto.ICodeObject;

@SuppressWarnings("unused")
public class PrinterDto implements ICodeObject, Serializable {
  private static final long serialVersionUID = 1L;
  private String invoicePrinter;
  private String invoicePaperSize;
  private Integer invoicePrintNumber;
  private Boolean isPrintedAfterPayment;
  private Boolean invoicePreview;

  private String kitchenPrinter;
  private String kitchenPaperSize;
  private Boolean kitchenPreview;

  public String getInvoicePrinter() {
    return invoicePrinter;
  }

  public void setInvoicePrinter(String invoicePrinter) {
    this.invoicePrinter = invoicePrinter;
  }

  public String getInvoicePaperSize() {
    return invoicePaperSize;
  }

  public void setInvoicePaperSize(String invoicePaperSize) {
    this.invoicePaperSize = invoicePaperSize;
  }

  public Integer getInvoicePrintNumber() {
    return invoicePrintNumber;
  }

  public void setInvoicePrintNumber(Integer invoicePrintNumber) {
    this.invoicePrintNumber = invoicePrintNumber;
  }

  public Boolean getIsPrintedAfterPayment() {
    return isPrintedAfterPayment;
  }

  public void setIsPrintedAfterPayment(Boolean isPrintedAfterPayment) {
    this.isPrintedAfterPayment = isPrintedAfterPayment;
  }

  public Boolean getInvoicePreview() {
    return invoicePreview;
  }

  public void setInvoicePreview(Boolean invoicePreview) {
    this.invoicePreview = invoicePreview;
  }

  public String getKitchenPrinter() {
    return kitchenPrinter;
  }

  public void setKitchenPrinter(String kitchenPrinter) {
    this.kitchenPrinter = kitchenPrinter;
  }

  public String getKitchenPaperSize() {
    return kitchenPaperSize;
  }

  public void setKitchenPaperSize(String kitchenPaperSize) {
    this.kitchenPaperSize = kitchenPaperSize;
  }

  public Boolean getKitchenPreview() {
    return kitchenPreview;
  }

  public void setKitchenPreview(Boolean kitchenPreview) {
    this.kitchenPreview = kitchenPreview;
  }

  @Override
  public boolean isActive() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setActive(boolean isActive) {
  }

  @Override
  public Long getId() {
    return null;
  }

  @Override
  public void setId(long code) {
  }

  @Override
  public String getCode() {
    return null;
  }

  @Override
  public void setCode(String code) {
  }

}
