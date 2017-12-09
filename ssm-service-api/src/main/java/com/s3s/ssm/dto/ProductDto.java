package com.s3s.ssm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class ProductDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private ProductTypeDto productType = new ProductTypeDto();
  private byte[] image;
  private boolean isFood;
  private double quantityInStock;
  private long minimumQuantity;
  private Long sellPrice;
  private UnitOfMeasureDto uom = new UnitOfMeasureDto();

  public ProductDto() {
  }

  public ProductDto(String code, String name) {
    setCode(code);
    setName(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ProductTypeDto getProductType() {
    return productType;
  }

  public void setProductType(ProductTypeDto productType) {
    this.productType = productType;
  }

  @Override
  public String toString() {
    return name;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public boolean isFood() {
    return isFood;
  }

  public void setFood(boolean isFood) {
    this.isFood = isFood;
  }

  public double getQuantityInStock() {
    return quantityInStock;
  }

  public void setQuantityInStock(double quantityInStock) {
    this.quantityInStock = quantityInStock;
  }

  public long getMinimumQuantity() {
    return minimumQuantity;
  }

  public void setMinimumQuantity(long minimumQuantity) {
    this.minimumQuantity = minimumQuantity;
  }

  public Long getSellPrice() {
    return sellPrice;
  }

  public void setSellPrice(Long sellPrice) {
    this.sellPrice = sellPrice;
  }

  public UnitOfMeasureDto getUom() {
    return uom;
  }

  public void setUom(UnitOfMeasureDto uom) {
    this.uom = uom;
  }
}
