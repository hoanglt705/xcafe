package com.s3s.ssm.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.s3s.ssm.service.IFoodTableService;
import com.s3s.ssm.service.IProductService;
import com.s3s.ssm.service.IReportService;

public class ReportContextProvider {
  private static ReportContextProvider configProvider;
  private final AnnotationConfigApplicationContext serviceContext;

  public static ReportContextProvider getInstance() {
    if (configProvider == null) {
      configProvider = new ReportContextProvider();
    }
    return configProvider;
  }

  private ReportContextProvider() {
    serviceContext = new AnnotationConfigApplicationContext(ReportHessanConfig.class);
  }

  public IReportService getReportService() {
    return serviceContext.getBean(IReportService.class);
  }

  public IFoodTableService getFoodTableService() {
    return serviceContext.getBean(IFoodTableService.class);
  }

  public IProductService getProductService() {
    return serviceContext.getBean(IProductService.class);
  }
}
