package com.s3s.crm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class ConfigDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private String address;
  private String phone;
  private String fixPhone;
  private Long pointValue;
  private Integer rewardIntroducePercent;
  private Integer warningBirthdayBerore;
  private boolean sendMailAfterPayment;
  private String prefixInvoiceCode;
  private String emailHostName;
  private int smtpPort;
  private String emailUsername;
  private String emailPassword;

  public String getEmailHostName() {
    return emailHostName;
  }

  public void setEmailHostName(String emailHostName) {
    this.emailHostName = emailHostName;
  }

  public int getSmtpPort() {
    return smtpPort;
  }

  public void setSmtpPort(int smtpPort) {
    this.smtpPort = smtpPort;
  }

  public String getEmailUsername() {
    return emailUsername;
  }

  public void setEmailUsername(String emailUsername) {
    this.emailUsername = emailUsername;
  }

  public String getEmailPassword() {
    return emailPassword;
  }

  public void setEmailPassword(String emailPassword) {
    this.emailPassword = emailPassword;
  }

  public Integer getWarningBirthdayBerore() {
    return warningBirthdayBerore;
  }

  public void setWarningBirthdayBerore(Integer warningBirthdayBerore) {
    this.warningBirthdayBerore = warningBirthdayBerore;
  }

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

  public Long getPointValue() {
    return pointValue;
  }

  public void setPointValue(Long pointValue) {
    this.pointValue = pointValue;
  }

  public Integer getRewardIntroducePercent() {
    return rewardIntroducePercent;
  }

  public void setRewardIntroducePercent(Integer rewardIntroducePercent) {
    this.rewardIntroducePercent = rewardIntroducePercent;
  }

  public boolean isSendMailAfterPayment() {
    return sendMailAfterPayment;
  }

  public void setSendMailAfterPayment(boolean sendMailAfterPayment) {
    this.sendMailAfterPayment = sendMailAfterPayment;
  }

  public String getPrefixInvoiceCode() {
    return prefixInvoiceCode;
  }

  public void setPrefixInvoiceCode(String prefixInvoiceCode) {
    this.prefixInvoiceCode = prefixInvoiceCode;
  }
}
