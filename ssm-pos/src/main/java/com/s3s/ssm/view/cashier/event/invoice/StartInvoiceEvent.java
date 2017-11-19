package com.s3s.ssm.view.cashier.event.invoice;

import com.s3s.ssm.dto.FoodTableDto;

public class StartInvoiceEvent {
  private final FoodTableDto foodTable;

  public StartInvoiceEvent(FoodTableDto foodTable) {
    this.foodTable = foodTable;
  }

  public FoodTableDto getFoodTable() {
    return foodTable;
  }
}
