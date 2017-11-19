package com.s3s.ssm.view.event;

import com.s3s.ssm.dto.FoodTableDto;

public class DeleteInvoiceEvent {
  private FoodTableDto foodTable;

  public DeleteInvoiceEvent(FoodTableDto foodTable) {
    this.foodTable = foodTable;
  }

  public FoodTableDto getFoodTable() {
    return foodTable;
  }
}
