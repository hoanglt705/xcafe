package com.s3s.ssm.view.cashier.event.calculator;

import com.s3s.ssm.dto.FoodTableDto;

/**
 * Event fires when a customer orders but not use
 * 
 */
public class AbortEvent {
  private final FoodTableDto foodTable;

  public AbortEvent(FoodTableDto foodTable) {
    this.foodTable = foodTable;
  }

  public FoodTableDto getFoodTable() {
    return foodTable;
  }
}
