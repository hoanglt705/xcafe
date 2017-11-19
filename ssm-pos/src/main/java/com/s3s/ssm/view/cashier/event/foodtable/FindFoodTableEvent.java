package com.s3s.ssm.view.cashier.event.foodtable;

public class FindFoodTableEvent {
  private Criteria criteria;
  
  public FindFoodTableEvent(Criteria criteria) {
    this.criteria = criteria;
  }
  public Criteria getCriteria() {
    return criteria;
  }
  public enum Criteria {
    CLOSING, SHOW_ALL
  }
}
