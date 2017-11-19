package com.s3s.ssm.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.s3s.ssm.service.ICompanyService;
import com.s3s.ssm.service.IDashboardService;
import com.s3s.ssm.service.IReportService;

public class DashboardContextProvider {
  private static DashboardContextProvider configProvider;
  private final AnnotationConfigApplicationContext serviceContext;

  public static DashboardContextProvider getInstance() {
    if (configProvider == null) {
      configProvider = new DashboardContextProvider();
    }
    return configProvider;
  }

  private DashboardContextProvider() {
    serviceContext = new AnnotationConfigApplicationContext(DashboardHessanConfig.class);
  }

  public IDashboardService getDashboardService() {
    return serviceContext.getBean(IDashboardService.class);
  }

  public IReportService getReportService() {
    return serviceContext.getBean(IReportService.class);
  }

  public ICompanyService getCompanyService() {
    return serviceContext.getBean(ICompanyService.class);
  }
}
