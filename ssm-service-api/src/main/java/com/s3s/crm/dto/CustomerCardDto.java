package com.s3s.crm.dto;

import java.util.Date;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class CustomerCardDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private Date createdDate;
  private CustomerDto customer = new CustomerDto();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public CustomerDto getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerDto customer) {
    this.customer = customer;
  }
}
