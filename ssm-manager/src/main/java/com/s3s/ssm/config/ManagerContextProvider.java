package com.s3s.ssm.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.s3s.ssm.service.IAreaService;
import com.s3s.ssm.service.ICompanyService;
import com.s3s.ssm.service.IEmployeeService;
import com.s3s.ssm.service.IFoodService;
import com.s3s.ssm.service.IFoodTableService;
import com.s3s.ssm.service.IImportStoreFormService;
import com.s3s.ssm.service.IInvoiceService;
import com.s3s.ssm.service.IMaterialService;
import com.s3s.ssm.service.IPaymentContentService;
import com.s3s.ssm.service.IPaymentService;
import com.s3s.ssm.service.IProductService;
import com.s3s.ssm.service.IProductTypeService;
import com.s3s.ssm.service.IReceiptService;
import com.s3s.ssm.service.IRoleService;
import com.s3s.ssm.service.ISecurityRoleService;
import com.s3s.ssm.service.ISecurityUserService;
import com.s3s.ssm.service.ISequenceNumberService;
import com.s3s.ssm.service.IShiftService;
import com.s3s.ssm.service.IStoreService;
import com.s3s.ssm.service.ISupplierService;
import com.s3s.ssm.service.IUnitOfMeasureService;
import com.s3s.ssm.service.IUomCategoryService;

public class ManagerContextProvider {
  private static ManagerContextProvider configProvider = null;
  private final AnnotationConfigApplicationContext serviceContext;

  public static synchronized ManagerContextProvider getInstance() {
    if (configProvider == null) {
      configProvider = new ManagerContextProvider();
    }
    return configProvider;
  }

  private ManagerContextProvider() {
    serviceContext = new AnnotationConfigApplicationContext(ManagerHessanConfig.class);
  }

  public ICompanyService getCompanyService() {
    return serviceContext.getBean(ICompanyService.class);
  }

  public IAreaService getAreaService() {
    return serviceContext.getBean(IAreaService.class);
  }

  public ISupplierService getSupplierService() {
    return serviceContext.getBean(ISupplierService.class);
  }

  public IUomCategoryService getUomCategoryService() {
    return serviceContext.getBean(IUomCategoryService.class);
  }

  public IUnitOfMeasureService getUnitOfMeasureService() {
    return serviceContext.getBean(IUnitOfMeasureService.class);
  }

  public IFoodTableService getFoodTableService() {
    return serviceContext.getBean(IFoodTableService.class);
  }

  public IFoodService getFoodService() {
    return serviceContext.getBean(IFoodService.class);
  }

  public IMaterialService getMaterialService() {
    return serviceContext.getBean(IMaterialService.class);
  }

  public IPaymentContentService getPaymentContentService() {
    return serviceContext.getBean(IPaymentContentService.class);
  }

  public ISequenceNumberService getSequenceNumberService() {
    return serviceContext.getBean(ISequenceNumberService.class);
  }

  public IStoreService getStoreService() {
    return serviceContext.getBean(IStoreService.class);
  }

  public IProductTypeService getProductTypeService() {
    return serviceContext.getBean(IProductTypeService.class);
  }

  public IProductService getProductService() {
    return serviceContext.getBean(IProductService.class);
  }

  public IInvoiceService getInvoiceService() {
    return serviceContext.getBean(IInvoiceService.class);
  }

  public IRoleService getRoleService() {
    return serviceContext.getBean(IRoleService.class);
  }

  public ISecurityRoleService getSecurityRoleService() {
    return serviceContext.getBean(ISecurityRoleService.class);
  }

  public IShiftService getShiftService() {
    return serviceContext.getBean(IShiftService.class);
  }

  public IEmployeeService getEmployeeService() {
    return serviceContext.getBean(IEmployeeService.class);
  }

  public ISecurityUserService getSecurityUserService() {
    return serviceContext.getBean(ISecurityUserService.class);
  }

  public IPaymentService getPaymentService() {
    return serviceContext.getBean(IPaymentService.class);
  }

  public IReceiptService getReceiptService() {
    return serviceContext.getBean(IReceiptService.class);
  }

  public IImportStoreFormService getImportStoreFormService() {
    return serviceContext.getBean(IImportStoreFormService.class);
  }
}
