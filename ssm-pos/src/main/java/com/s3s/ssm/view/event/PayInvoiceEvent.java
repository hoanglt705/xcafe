package com.s3s.ssm.view.event;

import com.s3s.ssm.dto.FoodTableDto;

public class PayInvoiceEvent {
  private FoodTableDto foodTable;

  public PayInvoiceEvent(FoodTableDto foodTable) {
    this.foodTable = foodTable;
  }

  public FoodTableDto getFoodTable() {
    return foodTable;
  }
}
