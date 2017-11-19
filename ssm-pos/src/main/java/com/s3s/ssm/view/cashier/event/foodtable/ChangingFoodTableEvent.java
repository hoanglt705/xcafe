package com.s3s.ssm.view.cashier.event.foodtable;

import com.s3s.ssm.dto.FoodTableDto;

public class ChangingFoodTableEvent {
  private FoodTableDto foodTable;
  private int openingFoodTableNum;

  public ChangingFoodTableEvent(FoodTableDto foodTable, int openingFoodTableNum) {
    this.foodTable = foodTable;
    this.openingFoodTableNum = openingFoodTableNum;
  }

  public FoodTableDto getFoodTable() {
    return foodTable;
  }

  public int getOpeningFoodTableNum() {
    return openingFoodTableNum;
  }
}
