package com.s3s.ssm.view.event;

import com.s3s.ssm.dto.FoodTableDto;

public class EmptyTableViewEvent {
  private final FoodTableDto foodTable;

  public EmptyTableViewEvent(FoodTableDto fromTable) {
    this.foodTable = fromTable;
  }

  public FoodTableDto getFoodTable() {
    return foodTable;
  };

}
