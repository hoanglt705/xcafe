package com.sunrise.xdoc.entity.store;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sunrise.xdoc.entity.config.FoodTable;

@Entity
@Table(name = "report_final_period_table_process")
@Inheritance(strategy = InheritanceType.JOINED)
public class FinalPeriodTableProcess extends AbstractFinalPeriodProcess {
  private static final long serialVersionUID = 1L;
  private FoodTable foodTable;

  @ManyToOne
  @JoinColumn(name = "food_table_id")
  public FoodTable getFoodTable() {
    return foodTable;
  }

  public void setFoodTable(FoodTable foodTable) {
    this.foodTable = foodTable;
  }
}
