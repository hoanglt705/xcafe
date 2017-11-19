package com.s3s.crm.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.s3s.crm.service.IConfigService;
import com.s3s.crm.service.ICrmInvoiceService;
import com.s3s.crm.service.ICrmProductService;
import com.s3s.crm.service.ICrmSequenceNumberService;
import com.s3s.crm.service.ICustomerCardService;
import com.s3s.crm.service.ICustomerService;
import com.s3s.crm.service.ICustomerTypeService;
import com.s3s.crm.service.IInternalMaterialService;
import com.s3s.crm.service.IMaterialTypeService;
import com.s3s.crm.service.IPromotionService;
import com.s3s.crm.service.IShapeService;
import com.s3s.crm.service.ISizeService;
import com.s3s.ssm.config.CrmServerRunner;
import com.s3s.ssm.service.ISecurityUserService;

public class CrmContextProvider {
  private static CrmContextProvider configProvider;
  private final BeanFactory serviceContext;

  public static CrmContextProvider getInstance() {
    if (configProvider == null) {
      configProvider = new CrmContextProvider();
    }
    return configProvider;
  }

  private CrmContextProvider() {
    serviceContext = new AnnotationConfigApplicationContext(CrmServerRunner.class);
  }

  public IConfigService getConfigService() {
    return serviceContext.getBean(IConfigService.class);
  }

  public ICustomerService getCustomerService() {
    return serviceContext.getBean(ICustomerService.class);
  }

  public ICustomerTypeService getCustomerTypeService() {
    return serviceContext.getBean(ICustomerTypeService.class);
  }

  public ICustomerCardService getCustomerCardService() {
    return serviceContext.getBean(ICustomerCardService.class);
  }

  public IShapeService getShapeService() {
    return serviceContext.getBean(IShapeService.class);
  }

  public ISizeService getSizeService() {
    return serviceContext.getBean(ISizeService.class);
  }

  public IMaterialTypeService getMaterialTypeService() {
    return serviceContext.getBean(IMaterialTypeService.class);
  }

  public ICrmProductService getCrmProductService() {
    return serviceContext.getBean(ICrmProductService.class);
  }

  public IPromotionService getPromotionService() {
    return serviceContext.getBean(IPromotionService.class);
  }

  public ICrmInvoiceService getCrmInvoiceService() {
    return serviceContext.getBean(ICrmInvoiceService.class);
  }

  public IInternalMaterialService getInternalMaterialService() {
    return serviceContext.getBean(IInternalMaterialService.class);
  }

  public ICrmSequenceNumberService getCrmSequenceNumberService() {
    return serviceContext.getBean(ICrmSequenceNumberService.class);
  }

  public ISecurityUserService getSecurityUserService() {
    return serviceContext.getBean(ISecurityUserService.class);
  }
}
