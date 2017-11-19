package com.s3s.ssm.view.event;

import com.s3s.ssm.dto.FoodTableDto;

public class StopInvoiceEvent {
  private FoodTableDto foodTable;

  public StopInvoiceEvent(FoodTableDto foodTable) {
    this.foodTable = foodTable;
  }

  public FoodTableDto getFoodTable() {
    return foodTable;
  }
}
