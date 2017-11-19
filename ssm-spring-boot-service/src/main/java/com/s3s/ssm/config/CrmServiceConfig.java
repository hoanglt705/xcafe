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

@Configuration
@ComponentScan(basePackages = "com.s3s.crm.service")
@EnableAutoConfiguration
@Import(CrmInfrastructureConfig.class)
@EnableJpaRepositories(basePackages = "com.s3s.crm.repo")
public class CrmServiceConfig {
  @Autowired
  private IConfigService configService;
  @Autowired
  private ICustomerService customerService;
  @Autowired
  private ICustomerTypeService customerTypeService;
  @Autowired
  private ICustomerCardService customerCardService;
  @Autowired
  private IShapeService shapeService;
  @Autowired
  private ISizeService sizeService;
  @Autowired
  private IMaterialTypeService materialTypeService;
  @Autowired
  private ICrmProductService crmProductService;
  @Autowired
  private IPromotionService promotionService;
  @Autowired
  private ICrmInvoiceService invoiceService;
  @Autowired
  private IInternalMaterialService internalMaterialService;
  @Autowired
  private ICrmSequenceNumberService crmSequenceNumberService;

  @Bean(name = "/configService")
  public HessianServiceExporter hessianConfigServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.configService);
    exporter.setServiceInterface(IConfigService.class);
    return exporter;
  }

  @Bean(name = "/customerService")
  public HessianServiceExporter hessianCustomerServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.customerService);
    exporter.setServiceInterface(ICustomerService.class);
    return exporter;
  }

  @Bean(name = "/customerCardService")
  public HessianServiceExporter hessianCustomerCardServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.customerCardService);
    exporter.setServiceInterface(ICustomerCardService.class);
    return exporter;
  }

  @Bean(name = "/customerTypeService")
  public HessianServiceExporter hessianCustomerTypeServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.customerTypeService);
    exporter.setServiceInterface(ICustomerTypeService.class);
    return exporter;
  }

  @Bean(name = "/shapeService")
  public HessianServiceExporter hessianShapeServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.shapeService);
    exporter.setServiceInterface(IShapeService.class);
    return exporter;
  }

  @Bean(name = "/sizeService")
  public HessianServiceExporter hessianSizeServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.sizeService);
    exporter.setServiceInterface(ISizeService.class);
    return exporter;
  }

  @Bean(name = "/materialTypeService")
  public HessianServiceExporter hessianMaterialTypeServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.materialTypeService);
    exporter.setServiceInterface(IMaterialTypeService.class);
    return exporter;
  }

  @Bean(name = "/crmProductService")
  public HessianServiceExporter hessianCrmProductServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.crmProductService);
    exporter.setServiceInterface(ICrmProductService.class);
    return exporter;
  }

  @Bean(name = "/promotionService")
  public HessianServiceExporter hessianPromotionServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.promotionService);
    exporter.setServiceInterface(IPromotionService.class);
    return exporter;
  }

  @Bean(name = "/crmInvoiceService")
  public HessianServiceExporter hessianCrmInvoiceServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.invoiceService);
    exporter.setServiceInterface(ICrmInvoiceService.class);
    return exporter;
  }

  @Bean(name = "/internalMaterialService")
  public HessianServiceExporter hessianInternalMaterialServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.internalMaterialService);
    exporter.setServiceInterface(IInternalMaterialService.class);
    return exporter;
  }

  @Bean(name = "/crmSequenceNumberService")
  public HessianServiceExporter hessianCrmSequenceNumberServiceExporter() {
    HessianServiceExporter exporter = new HessianServiceExporter();
    exporter.setService(this.crmSequenceNumberService);
    exporter.setServiceInterface(ICrmSequenceNumberService.class);
    return exporter;
  }
}
