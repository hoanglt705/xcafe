package com.s3s.ssm.view.event;

import java.time.LocalTime;

import com.s3s.ssm.dto.FoodTableDto;

public class OrderEvent {
  private FoodTableDto foodTable;
  private LocalTime waitingTime;

  public OrderEvent(FoodTableDto table, LocalTime waitingTime) {
    this.foodTable = table;
    this.waitingTime = waitingTime;
  }

  public OrderEvent(FoodTableDto foodTable) {
    this(foodTable, null);
  }

  public FoodTableDto getFoodTable() {
    return foodTable;
  }

  public LocalTime getWaitingTime() {
    return waitingTime;
  }
}
