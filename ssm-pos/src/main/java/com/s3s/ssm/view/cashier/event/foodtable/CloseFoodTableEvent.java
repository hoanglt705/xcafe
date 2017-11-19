package com.s3s.ssm.view.cashier.event.foodtable;

import com.s3s.ssm.dto.FoodTableDto;

public class CloseFoodTableEvent {
  private final FoodTableDto foodTable;

  public CloseFoodTableEvent(FoodTableDto foodTable) {
    this.foodTable = foodTable;
  }

  public FoodTableDto getFoodTable() {
    return foodTable;
  }

}
