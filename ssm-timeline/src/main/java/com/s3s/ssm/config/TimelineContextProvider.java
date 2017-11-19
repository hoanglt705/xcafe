package com.s3s.ssm.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.s3s.ssm.service.ICompanyService;
import com.s3s.ssm.service.ITimelineService;

public class TimelineContextProvider {
  private static TimelineContextProvider configProvider;
  private final AnnotationConfigApplicationContext serviceContext;

  public static TimelineContextProvider getInstance() {
    if (configProvider == null) {
      configProvider = new TimelineContextProvider();
    }
    return configProvider;
  }

  private TimelineContextProvider() {
    serviceContext = new AnnotationConfigApplicationContext(TimelineHessanConfig.class);
  }

  public ICompanyService getCompanyService() {
    return serviceContext.getBean(ICompanyService.class);
  }

  public ITimelineService getTimelineService() {
    return serviceContext.getBean(ITimelineService.class);
  }
}
