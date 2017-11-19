package com.sunrise.xdoc.entity.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "catalog_product_type")
@Inheritance(strategy = InheritanceType.JOINED)
public class ProductType extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private byte[] image;

  @Column(name = "name", unique = true, nullable = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
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
