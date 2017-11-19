package com.s3s.ssm.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CrmServerContextProvider {
  private static CrmServerContextProvider configProvider;
  private final AnnotationConfigApplicationContext applicationContext;

  public static CrmServerContextProvider getInstance() {
    if (configProvider == null) {
      configProvider = new CrmServerContextProvider();
    }
    return configProvider;
  }

  private CrmServerContextProvider() {
    applicationContext = new AnnotationConfigApplicationContext(CrmServerRunner.class);
  }

  @Deprecated
  public AnnotationConfigApplicationContext getApplicationContext() {
    return applicationContext;
  }
}
