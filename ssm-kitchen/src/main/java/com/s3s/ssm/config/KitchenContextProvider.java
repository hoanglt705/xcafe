package com.s3s.ssm.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.s3s.ssm.service.ICompanyService;
import com.s3s.ssm.service.IKitchenService;

public class KitchenContextProvider {
  private static KitchenContextProvider configProvider;
  private final AnnotationConfigApplicationContext serviceContext;

  public static KitchenContextProvider getInstance() {
    if (configProvider == null) {
      configProvider = new KitchenContextProvider();
    }
    return configProvider;
  }

  private KitchenContextProvider() {
    serviceContext = new AnnotationConfigApplicationContext(KitchenHessanConfig.class);
  }

  public IKitchenService getKitchenService() {
    return serviceContext.getBean(IKitchenService.class);
  }

  public ICompanyService getCompanyService() {
    return serviceContext.getBean(ICompanyService.class);
  }
}
