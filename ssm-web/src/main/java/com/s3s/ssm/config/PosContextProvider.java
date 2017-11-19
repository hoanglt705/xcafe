package com.s3s.ssm.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.s3s.ssm.service.IAreaService;
import com.s3s.ssm.service.ICompanyService;
import com.s3s.ssm.service.IDataTestService;
import com.s3s.ssm.service.IFoodTableService;
import com.s3s.ssm.service.IInvoiceService;
import com.s3s.ssm.service.IProductService;
import com.s3s.ssm.service.ISequenceNumberService;

public class PosContextProvider {
  private static PosContextProvider configProvider;
  private final AnnotationConfigApplicationContext serviceContext;

  public static PosContextProvider getInstance() {
    if (configProvider == null) {
      configProvider = new PosContextProvider();
    }
    return configProvider;
  }

  private PosContextProvider() {
    serviceContext = new AnnotationConfigApplicationContext(PosHessanConfig.class);
  }

  public IAreaService getAreaService() {
    return serviceContext.getBean(IAreaService.class);
  }

  public ISequenceNumberService getSequenceNumberService() {
    return serviceContext.getBean(ISequenceNumberService.class);
  }

  public ICompanyService getCompanyService() {
    return serviceContext.getBean(ICompanyService.class);
  }

  public IFoodTableService getFoodTableService() {
    return serviceContext.getBean(IFoodTableService.class);
  }

  public IProductService getProductService() {
    return serviceContext.getBean(IProductService.class);
  }

  public IInvoiceService getInvoiceService() {
    return serviceContext.getBean(IInvoiceService.class);
  }

  public IDataTestService getDataTestService() {
    return serviceContext.getBean(IDataTestService.class);
  }
}
