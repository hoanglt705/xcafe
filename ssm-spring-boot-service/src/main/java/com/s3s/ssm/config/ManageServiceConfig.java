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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.remoting.caucho.HessianServiceExporter;

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
@ComponentScan(basePackages = "com.s3s.ssm.service")
@EnableAutoConfiguration
@Import(InfrastructureConfig.class)
@EnableJpaRepositories(basePackages = "com.s3s.ssm.repo")
public class ManageServiceConfig {
  @Autowired
  private IAreaService areaService;

  @Autowired
  private IPaymentService paymentService;

  @Autowired
  private IReceiptService receiptService;

  @Autowired
  private IPaymentContentService paymentContentService;

  @Autowired
  private IInvoiceService invoiceService;

  @Autowired
  private IFoodTableService foodTableService;

  @Autowired
  private IProductTypeService productTypeService;

  @Autowired
  private IProductService productService;

  @Autowired
  private IFoodService foodService;

  @Autowired
  private ICompanyService companyService;

  @Autowired
  private ISequenceNumberService sequenceNumberService;

  @Autowired
  private IStoreService storeService;

  @Autowired
  private IUomCategoryService uomCategoryService;

  @Autowired
  private IUnitOfMeasureService unitOfMeasureService;

  @Autowired
  private IMaterialService materialService;

  @Autowired
  private IShiftService shiftService;

  @Autowired
  private IRoleService roleService;

  @Autowired
  private ISecurityRoleService securityRoleService;

  @Autowired
  private ISecurityUserService securityUserService;

  @Autowired
  private ISupplierService supplierService;

  @Autowired
  private IEmployeeService employeeService;

  @Autowired
  private IImportStoreFormService importStoreFormService;

  @Bean(name = "/AreaService")
  public HessianServiceExporter hessianAreaServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.areaService);
    exporter.setServiceInterface(IAreaService.class);
    return exporter;
  }

  @Bean(name = "/MaterialService")
  public HessianServiceExporter hessianMaterialServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.materialService);
    exporter.setServiceInterface(IMaterialService.class);
    return exporter;
  }

  @Bean(name = "/ImportStoreFormService")
  public HessianServiceExporter hessianImportStoreFormServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.importStoreFormService);
    exporter.setServiceInterface(IImportStoreFormService.class);
    return exporter;
  }

  @Bean(name = "/EmployeeService")
  public HessianServiceExporter hessianEmployeeServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.employeeService);
    exporter.setServiceInterface(IEmployeeService.class);
    return exporter;
  }

  @Bean(name = "/SupplierService")
  public HessianServiceExporter hessianSupplierServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.supplierService);
    exporter.setServiceInterface(ISupplierService.class);
    return exporter;
  }

  @Bean(name = "/ShiftService")
  public HessianServiceExporter hessianShiftServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.shiftService);
    exporter.setServiceInterface(IShiftService.class);
    return exporter;
  }

  @Bean(name = "/RoleService")
  public HessianServiceExporter hessianRoleServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.roleService);
    exporter.setServiceInterface(IRoleService.class);
    return exporter;
  }

  @Bean(name = "/SecurityRoleService")
  public HessianServiceExporter hessianSecurityRoleServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.securityRoleService);
    exporter.setServiceInterface(ISecurityRoleService.class);
    return exporter;
  }

  @Bean(name = "/SecurityUserService")
  public HessianServiceExporter hessianSecurityUserServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.securityUserService);
    exporter.setServiceInterface(ISecurityUserService.class);
    return exporter;
  }

  @Bean(name = "/FoodTableService")
  public HessianServiceExporter hessianFoodTableServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.foodTableService);
    exporter.setServiceInterface(IFoodTableService.class);
    return exporter;
  }

  @Bean(name = "/ProductTypeService")
  public HessianServiceExporter hessianProductTypeServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.productTypeService);
    exporter.setServiceInterface(IProductTypeService.class);
    return exporter;
  }

  @Bean(name = "/ProductService")
  public HessianServiceExporter hessianProductServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.productService);
    exporter.setServiceInterface(IProductService.class);
    return exporter;
  }

  @Bean(name = "/FoodService")
  public HessianServiceExporter hessianFoodServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.foodService);
    exporter.setServiceInterface(IFoodService.class);
    return exporter;
  }

  @Bean(name = "/UomCategoryService")
  public HessianServiceExporter hessianUomCategoryServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.uomCategoryService);
    exporter.setServiceInterface(IUomCategoryService.class);
    return exporter;
  }

  @Bean(name = "/UnitOfMeasureService")
  public HessianServiceExporter hessianUnitOfMeasureServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.unitOfMeasureService);
    exporter.setServiceInterface(IUnitOfMeasureService.class);
    return exporter;
  }

  @Bean(name = "/PaymentService")
  public HessianServiceExporter hessianPaymentServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.paymentService);
    exporter.setServiceInterface(IPaymentService.class);
    return exporter;
  }

  @Bean(name = "/ReceiptService")
  public HessianServiceExporter hessianReceiptServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.receiptService);
    exporter.setServiceInterface(IReceiptService.class);
    return exporter;
  }

  @Bean(name = "/PaymentContentService")
  public HessianServiceExporter hessianPaymentContentServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.paymentContentService);
    exporter.setServiceInterface(IPaymentContentService.class);
    return exporter;
  }

  @Bean(name = "/InvoiceService")
  public HessianServiceExporter hessianInvoiceServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.invoiceService);
    exporter.setServiceInterface(IInvoiceService.class);
    return exporter;
  }

  @Bean(name = "/CompanyService")
  public HessianServiceExporter hessianCompanyServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.companyService);
    exporter.setServiceInterface(ICompanyService.class);
    return exporter;
  }

  @Bean(name = "/SequenceNumberService")
  public HessianServiceExporter hessianSequenceNumberServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.sequenceNumberService);
    exporter.setServiceInterface(ISequenceNumberService.class);
    return exporter;
  }

  @Bean(name = "/StoreService")
  public HessianServiceExporter hessianStoreServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.storeService);
    exporter.setServiceInterface(IStoreService.class);
    return exporter;
  }
}
