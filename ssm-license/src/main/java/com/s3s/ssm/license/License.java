package com.s3s.ssm.license;

import java.io.Serializable;
import java.util.Date;

public class License implements Serializable {
  private static final long serialVersionUID = 1L;
  private String fullName;
  private String organizationName;
  private Date expireDate;
  private String macAddress;

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getOrganizationName() {
    return organizationName;
  }

  public void setOrganizationName(String organizationName) {
    this.organizationName = organizationName;
  }

  public Date getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(Date expireDate) {
    this.expireDate = expireDate;
  }

  public String getMacAddress() {
    return macAddress;
  }

  public void setMacAddress(String macAddress) {
    this.macAddress = macAddress;
  }

  public String getData() {
    StringBuilder builder = new StringBuilder();
    builder.append(fullName).append(macAddress).append(organizationName).append(expireDate.toString());
    return builder.toString();
  }
}
