/*
 * DaoHelper
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
package com.s3s.ssm.util;

import com.s3s.ssm.dao.IBaseDao;

/**
 * 
 * @author phamcongbang
 * 
 */
public interface DaoHelper {
  public <T> IBaseDao<T> getDao(Class<T> clazz);
}
