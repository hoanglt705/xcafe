package com.s3s.ssm.dto;

import java.util.ArrayList;
import java.util.List;

public class FoodDto extends ProductDto {
  private static final long serialVersionUID = 1L;
  private Long unitPrice;
  private List<FoodIngredientDto> foodIngredients = new ArrayList<>();

  public FoodDto() {
    super();
  }

  public FoodDto(String code, String name) {
    super(code, name);
  }

  public Long getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Long unitPrice) {
    this.unitPrice = unitPrice;
  }

  public List<FoodIngredientDto> getFoodIngredients() {
    return foodIngredients;
  }

  public void setFoodIngredients(List<FoodIngredientDto> foodIngredients) {
    this.foodIngredients = foodIngredients;
  }
}
