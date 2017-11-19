/*
 * UnitOfMeasure
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.sunrise.xdoc.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "config_unit_of_measure")
public class UnitOfMeasure extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private UomCategory uomCategory;
  private String name;
  private String shortName;
  private Float exchangeRate = 1F;
  private Boolean isBaseMeasure = false;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "uom_category_id", nullable = false)
  public UomCategory getUomCategory() {
    return uomCategory;
  }

  public void setUomCategory(UomCategory uomCategory) {
    this.uomCategory = uomCategory;
  }

  @Column(name = "uom_name", nullable = false, length = 128)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "uom_short_name", length = 20)
  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  @Column(name = "change_rate", nullable = false)
  public Float getExchangeRate() {
    return exchangeRate;
  }

  public void setExchangeRate(Float exchangeRate) {
    this.exchangeRate = exchangeRate;
  }

  @Column(name = "is_base_measure", nullable = false)
  public Boolean getIsBaseMeasure() {
    return isBaseMeasure;
  }

  public void setIsBaseMeasure(Boolean isBaseMeasure) {
    this.isBaseMeasure = isBaseMeasure;
  }

  @Override
  public String toString() {
    if (shortName != null) {
      return shortName;
    }
    return name;
  }
}
