package com.sunrise.xdoc.entity.employee;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "contact_employee")
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = -3762262328040341408L;
  private String name;
  private String address;
  private String email;
  private String phone;
  private String fax;
  private Date birthday;
  private Role role;
  private String identityCard;
  private Long salary = 0L;
  private byte[] image;
  private Set<Shift> shifts = new HashSet<Shift>();

  @Column(name = "name", unique = true)
  @Length(max = 250)
  @NotBlank
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "address")
  @Length(max = 1024)
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Column(name = "email")
  @Length(max = 128)
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

  @Column(name = "birthday")
  @NotNull
  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  @Column(name = "identity_card", unique = true)
  @Length(max = 20)
  public String getIdentityCard() {
    return identityCard;
  }

  public void setIdentityCard(String identityCard) {
    this.identityCard = identityCard;
  }

  @Column(name = "salary")
  public Long getSalary() {
    return salary;
  }

  public void setSalary(Long salary) {
    this.salary = salary;
  }

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "at_staff_shift", joinColumns = {@JoinColumn(name = "staff_id")}, inverseJoinColumns =
  {@JoinColumn(name = "shift_id")})
  public Set<Shift> getShifts() {
    return shifts;
  }

  public void setShifts(Set<Shift> shifts) {
    this.shifts = shifts;
  }

  @Override
  public String toString() {
    return getName();
  }

  @ManyToOne
  @JoinColumn(name = "role_id")
  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Column(name = "image", length = 5 * 1024 * 1024)
  @Lob
  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }
}
