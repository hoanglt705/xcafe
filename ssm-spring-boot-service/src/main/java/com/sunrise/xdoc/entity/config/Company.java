/*
 * Bank
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.sunrise.xdoc.entity.config;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "config_company")
public class Company extends AbstractActiveCodeOLObject {
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

  public Company() {
    super();
  }

  public Company(String name) {
    super();
    this.name = name;
  }

  @Column(name = "name", unique = true, nullable = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Column(name = "phone")
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Column(name = "fixPhone")
  public String getFixPhone() {
    return fixPhone;
  }

  public void setFixPhone(String fixPhone) {
    this.fixPhone = fixPhone;
  }

  @Column(name = "codeLength")
  public Integer getCodeLength() {
    return codeLength;
  }

  public void setCodeLength(Integer codeLength) {
    this.codeLength = codeLength;
  }

  @Column(name = "import_form_code_rule")
  public String getImportFormCodeRule() {
    return importFormCodeRule;
  }

  public void setImportFormCodeRule(String importFormCodeRule) {
    this.importFormCodeRule = importFormCodeRule;
  }

  @Column(name = "export_form_code_rule")
  public String getExportFormCodeRule() {
    return exportFormCodeRule;
  }

  public void setExportFormCodeRule(String exportFormCodeRule) {
    this.exportFormCodeRule = exportFormCodeRule;
  }

  @Column(name = "default_page_size")
  public Integer getDefaultPageSize() {
    return defaultPageSize;
  }

  public void setDefaultPageSize(Integer defaultPageSize) {
    this.defaultPageSize = defaultPageSize;
  }

  @Column(name = "invoice_code_rule")
  public String getInvoiceCodeRule() {
    return invoiceCodeRule;
  }

  public void setInvoiceCodeRule(String invoiceCodeRule) {
    this.invoiceCodeRule = invoiceCodeRule;
  }

  @Column(name = "employee_code_rule")
  public String getEmployeeCodeRule() {
    return employeeCodeRule;
  }

  public void setEmployeeCodeRule(String employeeCodeRule) {
    this.employeeCodeRule = employeeCodeRule;
  }

  @Column(name = "payment_code_rule")
  public String getPaymentCodeRule() {
    return paymentCodeRule;
  }

  public void setPaymentCodeRule(String paymentCodeRule) {
    this.paymentCodeRule = paymentCodeRule;
  }

  @Column(name = "receipt_code_rule")
  public String getReceiptCodeRule() {
    return receiptCodeRule;
  }

  public void setReceiptCodeRule(String receiptCodeRule) {
    this.receiptCodeRule = receiptCodeRule;
  }

  @Column(name = "open_time")
  public Date getOpenTime() {
    return openTime;
  }

  public void setOpenTime(Date openTime) {
    this.openTime = openTime;
  }

  @Column(name = "close_time")
  public Date getCloseTime() {
    return closeTime;
  }

  public void setCloseTime(Date closeTime) {
    this.closeTime = closeTime;
  }

  @Column(name = "table_view_alert_time")
  public Date getTableViewAlertTime() {
    return tableViewAlertTime;
  }

  public void setTableViewAlertTime(Date tableViewAlertTime) {
    this.tableViewAlertTime = tableViewAlertTime;
  }
}
