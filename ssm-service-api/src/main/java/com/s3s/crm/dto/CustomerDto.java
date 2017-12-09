package com.s3s.crm.dto;

import java.util.Date;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class CustomerDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private Date birthday;
  private String email;
  private String phoneNumber;
  private String address;
  private Integer point;
  private CustomerTypeDto customerType = new CustomerTypeDto();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Integer getPoint() {
    return point;
  }

  public void setPoint(Integer point) {
    this.point = point;
  }

  public CustomerTypeDto getCustomerType() {
    return customerType;
  }

  public void setCustomerType(CustomerTypeDto customerType) {
    this.customerType = customerType;
  }

}
