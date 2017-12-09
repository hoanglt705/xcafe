package com.s3s.crm.dto;

import java.util.Date;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class LuckyCustomerDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String customerName;
  private String customerType;
  private Date birthday;
  private int point;

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCustomerType() {
    return customerType;
  }

  public void setCustomerType(String customerType) {
    this.customerType = customerType;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public int getPoint() {
    return point;
  }

  public void setPoint(int point) {
    this.point = point;
  }

}
