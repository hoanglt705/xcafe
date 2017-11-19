package com.s3s.ssm.dto;

import java.io.Serializable;

public class ImportPriceDetailDto implements Serializable {
  private static final long serialVersionUID = 1L;
  private MaterialDto material = new MaterialDto();
  private SupplierDto supplier = new SupplierDto();
  private Long price;
  private boolean mainSupplier = false;

  public MaterialDto getMaterial() {
    return material;
  }

  public void setMaterial(MaterialDto material) {
    this.material = material;
  }

  public SupplierDto getSupplier() {
    return supplier;
  }

  public void setSupplier(SupplierDto supplier) {
    this.supplier = supplier;
  }

  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public boolean isMainSupplier() {
    return mainSupplier;
  }

  public void setMainSupplier(boolean mainSupplier) {
    this.mainSupplier = mainSupplier;
  }

}
