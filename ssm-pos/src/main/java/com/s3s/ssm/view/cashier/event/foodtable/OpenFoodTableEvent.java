package com.s3s.ssm.view.cashier.event.foodtable;

import com.s3s.ssm.dto.FoodTableDto;

public class OpenFoodTableEvent {
  private FoodTableDto foodTable;

  public OpenFoodTableEvent(FoodTableDto foodTable) {
    this.foodTable = foodTable;
  }

  public FoodTableDto getFoodTable() {
    return foodTable;
  }

}
