package com.sunrise.xdoc.entity.catalog;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "catalog_material")
@Inheritance(strategy = InheritanceType.JOINED)
public class Material extends Product {
  private static final long serialVersionUID = 1L;
  private Long beginningQuantityInStock = 0l;
  private Double quantityInStock = 0d; // count based on base uom
  private Long minimumQuantity = 0l;
  private Boolean retailable = false;
  private Set<ImportPriceDetail> importPrices = new HashSet<ImportPriceDetail>();

  @Column(name = "beginning_quantity_in_stock")
  public Long getBeginningQuantityInStock() {
    return beginningQuantityInStock;
  }

  public void setBeginningQuantityInStock(Long beginningQuantityInStock) {
    this.beginningQuantityInStock = beginningQuantityInStock;
  }

  @Column(name = "quantity_in_stock")
  public Double getQuantityInStock() {
    return quantityInStock;
  }

  public void setQuantityInStock(Double quantityInStock) {
    this.quantityInStock = quantityInStock;
  }

  @Column(name = "minimum_quantity")
  public Long getMinimumQuantity() {
    return minimumQuantity;
  }

  public void setMinimumQuantity(Long minimumQuantity) {
    this.minimumQuantity = minimumQuantity;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "material")
  public Set<ImportPriceDetail> getImportPrices() {
    return importPrices;
  }

  public void setImportPrices(Set<ImportPriceDetail> importPrices) {
    this.importPrices = importPrices;
  }

  @Column(name = "retailable")
  public Boolean getRetailable() {
    return retailable;
  }

  public void setRetailable(Boolean retailable) {
    this.retailable = retailable;
  }
}
