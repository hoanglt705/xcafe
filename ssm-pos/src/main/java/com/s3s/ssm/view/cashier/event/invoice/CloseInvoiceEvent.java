package com.s3s.ssm.view.cashier.event.invoice;

import com.s3s.ssm.dto.FoodTableDto;

public class CloseInvoiceEvent {
  private final FoodTableDto foodTable;

  public CloseInvoiceEvent(FoodTableDto foodTable) {
    this.foodTable = foodTable;
  }

  public FoodTableDto getFoodTable() {
    return foodTable;
  }
}
