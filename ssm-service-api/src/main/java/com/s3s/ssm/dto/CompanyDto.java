package com.s3s.ssm.dto;

import java.util.Date;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class CompanyDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private String address;
  private String phone;
  private String fixPhone;
  private Integer codeLength = 20;
  private String importFormCodeRule;
  private String exportFormCodeRule;
  private String invoiceCodeRule;
  private String paymentCodeRule;
  private String employeeCodeRule;
  private String receiptCodeRule;
  private Integer defaultPageSize = 10;
  private Date openTime;
  private Date closeTime;
  private Date tableViewAlertTime;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getFixPhone() {
    return fixPhone;
  }

  public void setFixPhone(String fixPhone) {
    this.fixPhone = fixPhone;
  }

  public Integer getCodeLength() {
    return codeLength;
  }

  public void setCodeLength(Integer codeLength) {
    this.codeLength = codeLength;
  }

  public String getImportFormCodeRule() {
    return importFormCodeRule;
  }

  public void setImportFormCodeRule(String importFormCodeRule) {
    this.importFormCodeRule = importFormCodeRule;
  }

  public String getExportFormCodeRule() {
    return exportFormCodeRule;
  }

  public void setExportFormCodeRule(String exportFormCodeRule) {
    this.exportFormCodeRule = exportFormCodeRule;
  }

  public String getInvoiceCodeRule() {
    return invoiceCodeRule;
  }

  public void setInvoiceCodeRule(String invoiceCodeRule) {
    this.invoiceCodeRule = invoiceCodeRule;
  }

  public String getPaymentCodeRule() {
    return paymentCodeRule;
  }

  public void setPaymentCodeRule(String paymentCodeRule) {
    this.paymentCodeRule = paymentCodeRule;
  }

  public String getEmployeeCodeRule() {
    return employeeCodeRule;
  }

  public void setEmployeeCodeRule(String employeeCodeRule) {
    this.employeeCodeRule = employeeCodeRule;
  }

  public String getReceiptCodeRule() {
    return receiptCodeRule;
  }

  public void setReceiptCodeRule(String receiptCodeRule) {
    this.receiptCodeRule = receiptCodeRule;
  }

  public Integer getDefaultPageSize() {
    return defaultPageSize;
  }

  public void setDefaultPageSize(Integer defaultPageSize) {
    this.defaultPageSize = defaultPageSize;
  }

  public Date getOpenTime() {
    return openTime;
  }

  public void setOpenTime(Date openTime) {
    this.openTime = openTime;
  }

  public Date getCloseTime() {
    return closeTime;
  }

  public void setCloseTime(Date closeTime) {
    this.closeTime = closeTime;
  }

  public Date getTableViewAlertTime() {
    return tableViewAlertTime;
  }

  public void setTableViewAlertTime(Date tableViewAlertTime) {
    this.tableViewAlertTime = tableViewAlertTime;
  }

}
