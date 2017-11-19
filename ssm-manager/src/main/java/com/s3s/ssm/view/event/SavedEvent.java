/*
 * SaveEvent
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

package com.s3s.ssm.view.event;

import java.util.EventObject;

import com.s3s.ssm.dto.IIdObject;

public class SavedEvent<T extends IIdObject> extends EventObject {
  private static final long serialVersionUID = -3306843792346320138L;

  private T entity;
  private boolean isNew;

  /**
   * @param source
   */
  public SavedEvent(Object source, T entity, boolean isNew) {
    super(source);
    this.entity = entity;
    this.isNew = isNew;
  }

  /**
   * Get the entity saved.
   * 
   * @return the entity which is saved.
   */
  public T getEntity() {
    return entity;
  }

  /**
   * Is the entity is saved newly, or edited.
   * 
   * @return isNew or edit
   */
  public boolean isNew() {
    return isNew;
  }

}
