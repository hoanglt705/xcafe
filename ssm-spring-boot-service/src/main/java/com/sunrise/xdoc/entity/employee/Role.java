/*
 * Role
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
package com.sunrise.xdoc.entity.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "contact_role")
public class Role extends AbstractActiveCodeOLObject {

  private static final long serialVersionUID = 1L;
  private String name;

  public Role() {
  }

  public Role(String name) {
    this.name = name;
  }

  @Column(name = "name", unique = true)
  @Length(max = 250)
  @NotBlank
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Transient
  @Override
  public String toString() {
    return name;
  }
}
