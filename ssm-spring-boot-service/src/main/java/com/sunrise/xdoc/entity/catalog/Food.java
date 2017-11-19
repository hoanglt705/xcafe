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
@Table(name = "catalog_food")
@Inheritance(strategy = InheritanceType.JOINED)
public class Food extends Product {
  private static final long serialVersionUID = 1L;
  private Long unitPrice;
  private Set<FoodIngredient> foodIngredients = new HashSet<FoodIngredient>();

  @Column(name = "unit_price", nullable = false)
  public Long getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Long unitPrice) {
    this.unitPrice = unitPrice;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "food", orphanRemoval = true)
  public Set<FoodIngredient> getFoodIngredients() {
    return foodIngredients;
  }

  public void setFoodIngredients(Set<FoodIngredient> material) {
    this.foodIngredients = material;
  }
}
