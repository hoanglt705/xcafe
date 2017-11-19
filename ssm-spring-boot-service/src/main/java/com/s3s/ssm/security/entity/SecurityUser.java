/*
 * User
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
package com.s3s.ssm.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "security_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class SecurityUser extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private String username;
  private String password;
  private String note;
  private SecurityRole role;

  @ManyToOne
  @JoinColumn(name = "role_id")
  public SecurityRole getRole() {
    return role;
  }

  public void setRole(SecurityRole role) {
    this.role = role;
  }

  @Column(name = "username", unique = true)
  @Length(max = 250)
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Column(name = "password")
  @Length(max = 250)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Column(name = "note")
  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}
