package com.s3s.ssm.dto;

import java.io.Serializable;

public class ImportStoreDetailDto implements Serializable {
  private static final long serialVersionUID = 1L;
  private ImportStoreFormDto importStoreForm;
  private MaterialDto material;
  private Long importUnitPrice;
  private UnitOfMeasureDto uom;
  private Integer quantity = 0;
  private Long priceSubtotal = 0L;

  public ImportStoreFormDto getImportStoreForm() {
    return importStoreForm;
  }

  public void setImportStoreForm(ImportStoreFormDto importStoreForm) {
    this.importStoreForm = importStoreForm;
  }

  public MaterialDto getMaterial() {
    return material;
  }

  public void setMaterial(MaterialDto material) {
    this.material = material;
  }

  public Long getImportUnitPrice() {
    return importUnitPrice;
  }

  public void setImportUnitPrice(Long importUnitPrice) {
    this.importUnitPrice = importUnitPrice;
  }

  public UnitOfMeasureDto getUom() {
    return uom;
  }

  public void setUom(UnitOfMeasureDto uom) {
    this.uom = uom;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Long getPriceSubtotal() {
    return priceSubtotal;
  }

  public void setPriceSubtotal(Long priceSubtotal) {
    this.priceSubtotal = priceSubtotal;
  }

}
