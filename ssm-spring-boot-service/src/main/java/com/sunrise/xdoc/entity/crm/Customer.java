package com.sunrise.xdoc.entity.crm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "crm_customer")
public class Customer extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private Date birthday;
  private String email;
  private String phone;
  private String address;
  private Integer point = 0;
  private CustomerType customerType;

  @Column(name = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "birthday")
  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  @Column(name = "email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name = "phone")
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Column(name = "address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Column(name = "point")
  public Integer getPoint() {
    return point;
  }

  public void setPoint(Integer point) {
    this.point = point;
  }

  @ManyToOne
  @JoinColumn(name = "customer_type_id")
  public CustomerType getCustomerType() {
    return customerType;
  }

  public void setCustomerType(CustomerType customerType) {
    this.customerType = customerType;
  }

}
