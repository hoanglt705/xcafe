/*
 * Copyright 2012-2013 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.s3s.ssm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

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

@Configuration
public class ManagerHessanConfig {
  private static final String SERVER_URL = "http://localhost:8081";

  @Bean(name = "companyService")
  public HessianProxyFactoryBean hessianCompanyProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/CompanyService");
    bean.setServiceInterface(ICompanyService.class);
    return bean;
  }

  @Bean(name = "areaService")
  public HessianProxyFactoryBean hessianAreaProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/AreaService");
    bean.setServiceInterface(IAreaService.class);
    return bean;
  }

  @Bean(name = "materialService")
  public HessianProxyFactoryBean hessianMaterialProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/MaterialService");
    bean.setServiceInterface(IMaterialService.class);
    return bean;
  }

  @Bean(name = "importStoreFormService")
  public HessianProxyFactoryBean hessianImportStoreFormProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/ImportStoreFormService");
    bean.setServiceInterface(IImportStoreFormService.class);
    return bean;
  }

  @Bean(name = "foodTableService")
  public HessianProxyFactoryBean hessianFoodTableProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/FoodTableService");
    bean.setServiceInterface(IFoodTableService.class);
    return bean;
  }

  @Bean(name = "paymentContentService")
  public HessianProxyFactoryBean hessianPaymentContentServiceProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/PaymentContentService");
    bean.setServiceInterface(IPaymentContentService.class);
    return bean;
  }

  @Bean(name = "paymentService")
  public HessianProxyFactoryBean hessianPaymentServiceProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/PaymentService");
    bean.setServiceInterface(IPaymentService.class);
    return bean;
  }

  @Bean(name = "receiptService")
  public HessianProxyFactoryBean hessianReceiptServiceProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/ReceiptService");
    bean.setServiceInterface(IReceiptService.class);
    return bean;
  }

  @Bean(name = "roleService")
  public HessianProxyFactoryBean hessianRoleProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/RoleService");
    bean.setServiceInterface(IRoleService.class);
    return bean;
  }

  @Bean(name = "employeeService")
  public HessianProxyFactoryBean hessianEmployeeProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/EmployeeService");
    bean.setServiceInterface(IEmployeeService.class);
    return bean;
  }

  @Bean(name = "uomCategoryService")
  public HessianProxyFactoryBean hessianUomCategoryProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/UomCategoryService");
    bean.setServiceInterface(IUomCategoryService.class);
    return bean;
  }

  @Bean(name = "unitOfMeasureService")
  public HessianProxyFactoryBean hessianUnitOfMeasureServiceProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/UnitOfMeasureService");
    bean.setServiceInterface(IUnitOfMeasureService.class);
    return bean;
  }

  @Bean(name = "supplierService")
  public HessianProxyFactoryBean hessianSupplierServiceProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/SupplierService");
    bean.setServiceInterface(ISupplierService.class);
    return bean;
  }

  @Bean(name = "productTypeService")
  public HessianProxyFactoryBean hessianProductTypeServiceProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/ProductTypeService");
    bean.setServiceInterface(IProductTypeService.class);
    return bean;
  }

  @Bean(name = "productService")
  public HessianProxyFactoryBean hessianProductServiceProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/ProductService");
    bean.setServiceInterface(IProductService.class);
    return bean;
  }

  @Bean(name = "foodService")
  public HessianProxyFactoryBean hessianFoodServiceProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/FoodService");
    bean.setServiceInterface(IFoodService.class);
    return bean;
  }

  @Bean(name = "shiftService")
  public HessianProxyFactoryBean hessianShiftServiceProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/ShiftService");
    bean.setServiceInterface(IShiftService.class);
    return bean;
  }

  @Bean(name = "sequenceNumberService")
  public HessianProxyFactoryBean hessianSequenceNumberProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/SequenceNumberService");
    bean.setServiceInterface(ISequenceNumberService.class);
    return bean;
  }

  @Bean(name = "storeService")
  public HessianProxyFactoryBean hessianStoreProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/StoreService");
    bean.setServiceInterface(IStoreService.class);
    return bean;
  }

  @Bean(name = "invoiceService")
  public HessianProxyFactoryBean hessianInvoiceProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/InvoiceService");
    bean.setServiceInterface(IInvoiceService.class);
    return bean;
  }

  @Bean(name = "securityUserService")
  public HessianProxyFactoryBean hessianSecurityUserProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/SecurityUserService");
    bean.setServiceInterface(ISecurityUserService.class);
    return bean;
  }

  @Bean(name = "securityRoleService")
  public HessianProxyFactoryBean hessianSecurityRoleProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/SecurityRoleService");
    bean.setServiceInterface(ISecurityRoleService.class);
    return bean;
  }
}
