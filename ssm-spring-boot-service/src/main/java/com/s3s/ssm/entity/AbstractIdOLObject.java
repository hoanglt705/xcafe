/*
 * AbstractIdOLObject
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
package com.s3s.ssm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractIdOLObject extends AbstractBaseIdObject {
  private static final long serialVersionUID = -4709212848984538326L;
  private String userInserted;
  private Date dateInserted;
  private String userLastUpdate;
  private Date dateLastUpdate;
  private Long version;

  @Column(name = "usr_log_i")
  public String getUserInserted() {
    return userInserted;
  }

  @Deprecated
  public void setUserInserted(String userInserted) {
    this.userInserted = userInserted;
  }

  @Column(name = "dte_log_i")
  public Date getDateInserted() {
    return dateInserted;
  }

  @Deprecated
  public void setDateInserted(Date dateInserted) {
    this.dateInserted = dateInserted;
  }

  @Column(name = "usr_log_lu")
  public String getUserLastUpdate() {
    return userLastUpdate;
  }

  @Deprecated
  public void setUserLastUpdate(String userLastUpdate) {
    this.userLastUpdate = userLastUpdate;
  }

  @Column(name = "dte_log_lu")
  public Date getDateLastUpdate() {
    return dateLastUpdate;
  }

  @Deprecated
  public void setDateLastUpdate(Date dateLastUpdate) {
    this.dateLastUpdate = dateLastUpdate;
  }

  @Column(name = "version")
  @Version
  public Long getVersion() {
    return version;
  }

  /**
   * Only used by Hibernate.
   * 
   * @param version
   */
  @Deprecated
  public void setVersion(Long version) {
    this.version = version;
  }
}
