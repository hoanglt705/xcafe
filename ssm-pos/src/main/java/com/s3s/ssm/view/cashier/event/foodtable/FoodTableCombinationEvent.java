package com.s3s.ssm.view.cashier.event.foodtable;

import com.s3s.ssm.dto.FoodTableDto;

public class FoodTableCombinationEvent {
  private FoodTableDto fromFoodTable;
  private FoodTableDto toFoodTable;

  public FoodTableCombinationEvent(FoodTableDto fromFoodTable, FoodTableDto toFoodTable) {
    this.fromFoodTable = fromFoodTable;
    this.toFoodTable = toFoodTable;
  }

  public FoodTableDto getFromFoodTable() {
    return fromFoodTable;
  }

  public FoodTableDto getToFoodTable() {
    return toFoodTable;
  }

}
