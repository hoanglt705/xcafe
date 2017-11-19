package com.sunrise.xdoc.entity.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.sunrise.xdoc.entity.config.UnitOfMeasure;

@Entity
@Table(name = "catalog_food_ingredient")
@Inheritance(strategy = InheritanceType.JOINED)
public class FoodIngredient extends AbstractBaseIdObject {
  private static final long serialVersionUID = 1L;
  private Food food;
  private Material material;
  private UnitOfMeasure uom;
  private Integer quantity = 0;
  private Long unitPrice = 0L;
  private Long subPriceTotal = 0L;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "food_id")
  public Food getFood() {
    return food;
  }

  public void setFood(Food food) {
    this.food = food;
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
  @JoinColumn(name = "uom_id")
  public UnitOfMeasure getUom() {
    return uom;
  }

  public void setUom(UnitOfMeasure uom) {
    this.uom = uom;
  }

  @Column(name = "quantity")
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  @Column(name = "unit_price")
  public Long getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Long unitPrice) {
    this.unitPrice = unitPrice;
  }

  @Column(name = "subprice_total")
  public Long getSubPriceTotal() {
    return subPriceTotal;
  }

  public void setSubPriceTotal(Long subPriceTotal) {
    this.subPriceTotal = subPriceTotal;
  }

}
