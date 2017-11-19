package com.sunrise.xdoc.entity.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;
import com.sunrise.xdoc.entity.config.UnitOfMeasure;

@Entity
@Table(name = "catalog_product")
@Inheritance(strategy = InheritanceType.JOINED)
public class Product extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private ProductType productType;
  private byte[] image;
  private Long sellPrice;
  private UnitOfMeasure uom;

  @Column(name = "name", unique = true, nullable = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @ManyToOne
  @JoinColumn(name = "product_type_id")
  public ProductType getProductType() {
    return productType;
  }

  public void setProductType(ProductType productType) {
    this.productType = productType;
  }

  @Column(name = "image", length = 5 * 1024 * 1024)
  @Lob
  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  @Column(name = "sell_price")
  public Long getSellPrice() {
    return sellPrice;
  }

  public void setSellPrice(Long sellPrice) {
    this.sellPrice = sellPrice;
  }

  @ManyToOne
  @JoinColumn(name = "uom_id", nullable = false)
  public UnitOfMeasure getUom() {
    return uom;
  }

  public void setUom(UnitOfMeasure uom) {
    this.uom = uom;
  }

  @Override
  public String toString() {
    return name;
  }

}
