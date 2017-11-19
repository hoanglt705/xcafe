package com.s3s.ssm.view.cashier.event.foodtable;

import com.s3s.ssm.dto.FoodTableDto;

public class DeleteFoodTableEvent {
  private FoodTableDto fSelectedFoodTable;

  public DeleteFoodTableEvent(FoodTableDto selectedFoodTable) {
    fSelectedFoodTable = selectedFoodTable;
  }

  public FoodTableDto getSelectedFoodTable() {
    return fSelectedFoodTable;
  }
}
