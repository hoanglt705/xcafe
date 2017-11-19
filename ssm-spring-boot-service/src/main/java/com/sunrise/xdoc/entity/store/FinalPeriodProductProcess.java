package com.sunrise.xdoc.entity.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sunrise.xdoc.entity.catalog.Product;
import com.sunrise.xdoc.entity.config.UnitOfMeasure;

@Entity
@Table(name = "report_final_period_product_process")
@Inheritance(strategy = InheritanceType.JOINED)
public class FinalPeriodProductProcess extends AbstractFinalPeriodProcess {
  private static final long serialVersionUID = 1L;
  private Product product;
  private UnitOfMeasure uom;
  private Double quantityInStock;
  private Integer importQuantity;
  private Integer exportQuantity;
  private Double soldQuantity;

  @ManyToOne
  @JoinColumn(name = "product_id")
  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  @Column(name = "quantity_in_stock")
  public Double getQuantityInStock() {
    return quantityInStock;
  }

  public void setQuantityInStock(Double quantityInStock) {
    this.quantityInStock = quantityInStock;
  }

  @ManyToOne
  @JoinColumn(name = "uom_id")
  public UnitOfMeasure getUom() {
    return uom;
  }

  public void setUom(UnitOfMeasure uom) {
    this.uom = uom;
  }

  @Column(name = "import_quantity")
  public Integer getImportQuantity() {
    return importQuantity;
  }

  public void setImportQuantity(Integer importQuantity) {
    this.importQuantity = importQuantity;
  }

  @Column(name = "export_quantity")
  public Integer getExportQuantity() {
    return exportQuantity;
  }

  public void setExportQuantity(Integer exportQuantity) {
    this.exportQuantity = exportQuantity;
  }

  @Column(name = "sold_quantity")
  public Double getSoldQuantity() {
    return soldQuantity;
  }

  public void setSoldQuantity(Double soldQuantity) {
    this.soldQuantity = soldQuantity;
  }
}
