/*
 * IEditSavedListener
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

package com.s3s.ssm.view;

import java.util.EventListener;

import com.s3s.ssm.dto.IIdObject;
import com.s3s.ssm.view.event.SavedEvent;

public interface ISavedListener<T extends IIdObject> extends EventListener {
  /**
   * Invoke when the edit view was saved.
   */
  void doSaved(SavedEvent<T> e);
}
