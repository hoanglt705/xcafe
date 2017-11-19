package com.sunrise.xdoc.entity.crm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "crm_config")
public class Config extends AbstractActiveCodeOLObject {
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

  @Column(name = "name")
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

  @Column(name = "pointValue")
  public Long getPointValue() {
    return pointValue;
  }

  public void setPointValue(Long pointValue) {
    this.pointValue = pointValue;
  }

  @Column(name = "rewardIntroducePercent")
  public Integer getRewardIntroducePercent() {
    return rewardIntroducePercent;
  }

  public void setRewardIntroducePercent(Integer rewardIntroducePercent) {
    this.rewardIntroducePercent = rewardIntroducePercent;
  }

  @Column(name = "sendMailAfterPayment")
  public boolean isSendMailAfterPayment() {
    return sendMailAfterPayment;
  }

  public void setSendMailAfterPayment(boolean sendMailAfterPayment) {
    this.sendMailAfterPayment = sendMailAfterPayment;
  }

  @Column(name = "warningBirthdayBerore")
  public Integer getWarningBirthdayBerore() {
    return warningBirthdayBerore;
  }

  public void setWarningBirthdayBerore(Integer warningBirthdayBerore) {
    this.warningBirthdayBerore = warningBirthdayBerore;
  }

  @Column(name = "prefixInvoiceCode")
  public String getPrefixInvoiceCode() {
    return prefixInvoiceCode;
  }

  public void setPrefixInvoiceCode(String prefixInvoiceCode) {
    this.prefixInvoiceCode = prefixInvoiceCode;
  }

  @Column(name = "emailHostName")
  public String getEmailHostName() {
    return emailHostName;
  }

  public void setEmailHostName(String emailHostName) {
    this.emailHostName = emailHostName;
  }

  @Column(name = "smtpPort")
  public int getSmtpPort() {
    return smtpPort;
  }

  public void setSmtpPort(int smtpPort) {
    this.smtpPort = smtpPort;
  }

  @Column(name = "emailUsername")
  public String getEmailUsername() {
    return emailUsername;
  }

  public void setEmailUsername(String emailUsername) {
    this.emailUsername = emailUsername;
  }

  @Column(name = "emailPassword")
  public String getEmailPassword() {
    return emailPassword;
  }

  public void setEmailPassword(String emailPassword) {
    this.emailPassword = emailPassword;
  }

}
