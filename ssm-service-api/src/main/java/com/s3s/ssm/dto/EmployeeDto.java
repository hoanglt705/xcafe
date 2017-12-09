package com.s3s.ssm.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class EmployeeDto extends AbstractCodeObject {
  private static final long serialVersionUID = 370844580866757411L;
  private String name;
  private String address;
  private String email;
  private String phone;
  private String fax;
  private Date birthday;
  private RoleDto role;
  private String identityCard;
  private Long salary = 0L;
  private byte[] image;
  private List<ShiftDto> shifts = new ArrayList<>();

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

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public RoleDto getRole() {
    return role;
  }

  public void setRole(RoleDto role) {
    this.role = role;
  }

  public String getIdentityCard() {
    return identityCard;
  }

  public void setIdentityCard(String identityCard) {
    this.identityCard = identityCard;
  }

  public Long getSalary() {
    return salary;
  }

  public void setSalary(Long salary) {
    this.salary = salary;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public List<ShiftDto> getShifts() {
    return shifts;
  }

  public void setShifts(List<ShiftDto> shifts) {
    this.shifts = shifts;
  }

}
