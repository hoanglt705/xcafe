package com.s3s.ssm.dto;

import java.io.Serializable;

public class FoodIngredientDto implements Serializable {
  private static final long serialVersionUID = 1L;
  private MaterialDto material = new MaterialDto();
  private UnitOfMeasureDto uom = new UnitOfMeasureDto();
  private Integer quantity = 0;
  private Long unitPrice = 0L;
  private Long subPriceTotal = 0L;
  private FoodDto food;

  public MaterialDto getMaterial() {
    return material;
  }

  public void setMaterial(MaterialDto material) {
    this.material = material;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Long getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Long unitPrice) {
    this.unitPrice = unitPrice;
  }

  public Long getSubPriceTotal() {
    return subPriceTotal;
  }

  public void setSubPriceTotal(Long subPriceTotal) {
    this.subPriceTotal = subPriceTotal;
  }

  public FoodDto getFood() {
    return food;
  }

  public void setFood(FoodDto food) {
    this.food = food;
  }

  public UnitOfMeasureDto getUom() {
    return uom;
  }

  public void setUom(UnitOfMeasureDto uom) {
    this.uom = uom;
  }

}
