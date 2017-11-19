package com.sunrise.xdoc.entity.contact;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@MappedSuperclass
public abstract class AbstractContact extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = -3762262328040341408L;
  private String name;
  private String address;
  private String email;
  private String phone;
  private String fax;

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

  @Column(name = "email")
  @Email
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name = "phone")
  @Length(max = 20)
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Column(name = "fax")
  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }
}
