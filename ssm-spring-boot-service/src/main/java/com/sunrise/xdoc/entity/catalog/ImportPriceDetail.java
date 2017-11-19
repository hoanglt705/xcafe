package com.sunrise.xdoc.entity.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.sunrise.xdoc.entity.contact.Supplier;

@Entity
@Table(name = "catalog_import_price_detail")
@Inheritance(strategy = InheritanceType.JOINED)
public class ImportPriceDetail extends AbstractBaseIdObject {
  private static final long serialVersionUID = 1L;
  private Material material;
  private Supplier supplier;
  private Long price;
  private boolean mainSupplier = false;

  public ImportPriceDetail() {
    super();
  }

  public ImportPriceDetail(Material material, Supplier supplier, Long price) {
    this.material = material;
    this.supplier = supplier;
    this.price = price;
  }

  @ManyToOne
  @JoinColumn(name = "material_id")
  public Material getMaterial() {
    return material;
  }

  public void setMaterial(Material material) {
    this.material = material;
  }

  @ManyToOne
  @JoinColumn(name = "supplier_id")
  public Supplier getSupplier() {
    return supplier;
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }

  @Column(name = "price")
  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  @Column(name = "is_main_supplier")
  public boolean isMainSupplier() {
    return mainSupplier;
  }

  public void setMainSupplier(boolean mainSupplier) {
    this.mainSupplier = mainSupplier;
  }

}
