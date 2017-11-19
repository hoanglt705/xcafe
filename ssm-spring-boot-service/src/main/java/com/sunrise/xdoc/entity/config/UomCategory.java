/*
 * UomCategory
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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "config_uom_category")
public class UomCategory extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private UomCategory parentUomCategory;
  private Set<UomCategory> subUomCates = new HashSet<UomCategory>();

  @Column(name = "name", nullable = false, length = 128)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "parentUomCategory_id")
  public UomCategory getParentUomCategory() {
    return parentUomCategory;
  }

  public void setParentUomCategory(UomCategory parentUomCategory) {
    this.parentUomCategory = parentUomCategory;
  }

  @OneToMany(mappedBy = "parentUomCategory")
  public Set<UomCategory> getSubUomCates() {
    return subUomCates;
  }

  public void setSubUomCates(Set<UomCategory> subUomCates) {
    this.subUomCates = subUomCates;
  }

  @Override
  public String toString() {
    String fullname = name;
    // get all name of previous parents
    UomCategory parent = parentUomCategory;
    while (parent != null) {
      fullname = parent.name + " / " + fullname;
      parent = parent.parentUomCategory;
    }
    return fullname;
  }
}