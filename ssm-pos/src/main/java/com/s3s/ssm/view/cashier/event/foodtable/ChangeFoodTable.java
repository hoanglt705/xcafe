package com.s3s.ssm.view.cashier.event.foodtable;

import com.s3s.ssm.dto.FoodTableDto;

public class ChangeFoodTable {
  private FoodTableDto fFromTable;
  private FoodTableDto fToTable;

  public ChangeFoodTable(FoodTableDto fromTable, FoodTableDto toTable) {
    fFromTable = fromTable;
    fToTable = toTable;
  }

  public FoodTableDto getFromTable() {
    return fFromTable;
  }

  public void setFromTable(FoodTableDto fromTable) {
    this.fFromTable = fromTable;
  }

  public FoodTableDto getToTable() {
    return fToTable;
  }

  public void setToTable(FoodTableDto toTable) {
    this.fToTable = toTable;
  }

}
