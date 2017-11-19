package com.sunrise.xdoc.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "config_food_table")
@Inheritance(strategy = InheritanceType.JOINED)
public class FoodTable extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = -5284799243435617565L;
  private String name;
  private Area area;
  private Integer seatNum;

  @Column(name = "name", unique = true)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @ManyToOne
  @JoinColumn(name = "area_id")
  public Area getArea() {
    return area;
  }

  public void setArea(Area area) {
    this.area = area;
  }

  @Column(name = "seat_num")
  public Integer getSeatNum() {
    return seatNum;
  }

  public void setSeatNum(Integer seatNum) {
    this.seatNum = seatNum;
  }

  @Override
  public String toString() {
    return name;
  }

}
