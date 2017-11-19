/*
 * ConfigProvider
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

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;

public class ConfigProvider {
  private static ConfigProvider configProvider;
  private static ApplicationContext appContext;

  public static ConfigProvider getInstance() {
    if (configProvider == null) {
      configProvider = new ConfigProvider();
    }
    return configProvider;
  }

  private ConfigProvider() {
    appContext = new AnnotationConfigApplicationContext();
  }

  public ApplicationContext getApplicationContext() {
    return appContext;
  }

  public DaoHelper getDaoHelper() {
    return (DaoHelper) appContext.getBean("daoHelper");
  }

  public ServiceProvider getServiceProvider() {
    return (ServiceProvider) appContext.getBean("serviceProvider");
  }

  public ICacheDataService getCacheDataService() {
    return (ICacheDataService) appContext.getBean("cacheDataService");
  }

  public PlatformTransactionManager getTransactionManager() {
    return (PlatformTransactionManager) appContext.getBean("transactionManager");
  }

  public static List<?> getReferenceDataList(String cacheId) {
    return getInstance().getCacheDataService()
            .getReferenceDataList(cacheId);
  }

  public static Object getReferenceData(String cacheId) {
    return getInstance().getCacheDataService().getReferenceData(cacheId);
  }
}
