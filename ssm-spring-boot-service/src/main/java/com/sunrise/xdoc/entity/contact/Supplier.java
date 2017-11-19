package com.sunrise.xdoc.entity.contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "contact_supplier")
@Inheritance(strategy = InheritanceType.JOINED)
public class Supplier extends AbstractContact {
  private static final long serialVersionUID = 1L;
  private byte[] image;
  private String representer;
  private String position;
  private String contactPerson1;
  private String phone1;
  private String contactPerson2;
  private String phone2;
  private String website;

  @Column(name = "image", length = 5 * 1024 * 1024)
  @Lob
  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  @Column(name = "representer")
  @Length(max = 255)
  public String getRepresenter() {
    return representer;
  }

  public void setRepresenter(String representer) {
    this.representer = representer;
  }

  @Column(name = "position")
  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  @Column(name = "contact_person1")
  @Length(max = 255)
  public String getContactPerson1() {
    return contactPerson1;
  }

  public void setContactPerson1(String contactPerson1) {
    this.contactPerson1 = contactPerson1;
  }

  @Column(name = "phone_1")
  @Length(max = 20)
  public String getPhone1() {
    return phone1;
  }

  public void setPhone1(String phone1) {
    this.phone1 = phone1;
  }

  @Column(name = "contact_person2")
  @Length(max = 255)
  public String getContactPerson2() {
    return contactPerson2;
  }

  public void setContactPerson2(String contactPerson2) {
    this.contactPerson2 = contactPerson2;
  }

  @Column(name = "phone_2")
  @Length(max = 20)
  public String getPhone2() {
    return phone2;
  }

  public void setPhone2(String phone2) {
    this.phone2 = phone2;
  }

  @Column(name = "website")
  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }
}
