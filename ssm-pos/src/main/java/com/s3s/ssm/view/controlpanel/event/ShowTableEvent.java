package com.s3s.ssm.view.controlpanel.event;

import com.s3s.ssm.dto.FoodTableDto;

public class ShowTableEvent {
  private final ShowTableEnum filter;
  private final FoodTableDto foodTable;

  public ShowTableEvent(ShowTableEnum filter) {
    this(filter, null);
  }

  public ShowTableEvent(ShowTableEnum filter, FoodTableDto foodTable) {
    this.filter = filter;
    this.foodTable = foodTable;
  }

  public ShowTableEnum getFilter() {
    return filter;
  }

  public FoodTableDto getFoodTable() {
    return foodTable;
  }

  public enum ShowTableEnum {
    SHOW_ALL, SHOW_EMPTY, SHOW_SERVING, SHOW_BOOKING, SHOW_SPECIFIC
  }
}
