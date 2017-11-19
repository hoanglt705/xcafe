package com.sunrise.xdoc.entity.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.sunrise.xdoc.entity.catalog.Material;
import com.sunrise.xdoc.entity.config.UnitOfMeasure;

@Entity
@Table(name = "store_export_detail")
@Inheritance(strategy = InheritanceType.JOINED)
public class ExportStoreDetail extends AbstractIdOLObject {
  private static final long serialVersionUID = 1L;
  private ExportStoreForm exportStoreForm;
  private Material material;
  private Long importUnitPrice;
  private UnitOfMeasure uom;
  private Integer quantity = 0;
  private Long priceSubtotal = 0L;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "material_id", nullable = false)
  public Material getMaterial() {
    return material;
  }

  public void setMaterial(Material product) {
    this.material = product;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "uom_id", nullable = false)
  public UnitOfMeasure getUom() {
    return uom;
  }

  public void setUom(UnitOfMeasure uom) {
    this.uom = uom;
  }

  @Column(name = "quantity", nullable = false)
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "form_id", nullable = false)
  public ExportStoreForm getExportStoreForm() {
    return exportStoreForm;
  }

  public void setExportStoreForm(ExportStoreForm exportStoreForm) {
    this.exportStoreForm = exportStoreForm;
  }

  @Column(name = "import_unit_price")
  public Long getImportUnitPrice() {
    return importUnitPrice;
  }

  public void setImportUnitPrice(Long importUnitPrice) {
    this.importUnitPrice = importUnitPrice;
  }

  @Column(name = "price_subtotal")
  public Long getPriceSubtotal() {
    return priceSubtotal;
  }

  public void setPriceSubtotal(Long priceSubtotal) {
    this.priceSubtotal = priceSubtotal;
  }
}
