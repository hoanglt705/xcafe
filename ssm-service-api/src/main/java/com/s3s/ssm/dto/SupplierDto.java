package com.s3s.ssm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class SupplierDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private String address;
  private String email;
  private String phone;
  private String fax;
  private byte[] image;
  private String representer;
  private String position;
  private String contactPerson1;
  private String phone1;
  private String contactPerson2;
  private String phone2;
  private String website;

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public String getRepresenter() {
    return representer;
  }

  public void setRepresenter(String representer) {
    this.representer = representer;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getContactPerson1() {
    return contactPerson1;
  }

  public void setContactPerson1(String contactPerson1) {
    this.contactPerson1 = contactPerson1;
  }

  public String getPhone1() {
    return phone1;
  }

  public void setPhone1(String phone1) {
    this.phone1 = phone1;
  }

  public String getContactPerson2() {
    return contactPerson2;
  }

  public void setContactPerson2(String contactPerson2) {
    this.contactPerson2 = contactPerson2;
  }

  public String getPhone2() {
    return phone2;
  }

  public void setPhone2(String phone2) {
    this.phone2 = phone2;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

}
