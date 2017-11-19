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

package com.s3s.crm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

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
public class CrmHessanConfig {
  private static final String SERVER_URL = "http://localhost:8081";

  @Bean(name = "configService")
  public HessianProxyFactoryBean hessianConfigProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/configService");
    bean.setServiceInterface(IConfigService.class);
    return bean;
  }

  @Bean(name = "customerService")
  public HessianProxyFactoryBean hessianCustomerProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/customerService");
    bean.setServiceInterface(ICustomerService.class);
    return bean;
  }

  @Bean(name = "customerTypeService")
  public HessianProxyFactoryBean hessianCustomerTypeProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/customerTypeService");
    bean.setServiceInterface(ICustomerTypeService.class);
    return bean;
  }

  @Bean(name = "customerCardService")
  public HessianProxyFactoryBean hessianCustomerCardProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/customerCardService");
    bean.setServiceInterface(ICustomerCardService.class);
    return bean;
  }

  @Bean(name = "shapeService")
  public HessianProxyFactoryBean hessianShapeProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/shapeService");
    bean.setServiceInterface(IShapeService.class);
    return bean;
  }

  @Bean(name = "sizeService")
  public HessianProxyFactoryBean hessianSizeProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/sizeService");
    bean.setServiceInterface(ISizeService.class);
    return bean;
  }

  @Bean(name = "materialTypeService")
  public HessianProxyFactoryBean hessianMaterialTypeProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/materialTypeService");
    bean.setServiceInterface(IMaterialTypeService.class);
    return bean;
  }

  @Bean(name = "crmProductService")
  public HessianProxyFactoryBean hessianCrmProductProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/crmProductService");
    bean.setServiceInterface(ICrmProductService.class);
    return bean;
  }

  @Bean(name = "promotionService")
  public HessianProxyFactoryBean hessianPromotionProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/promotionService");
    bean.setServiceInterface(IPromotionService.class);
    return bean;
  }

  @Bean(name = "crmInvoiceService")
  public HessianProxyFactoryBean hessianCrmInvoiceProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/crmInvoiceService");
    bean.setServiceInterface(ICrmInvoiceService.class);
    return bean;
  }

  @Bean(name = "internalMaterialService")
  public HessianProxyFactoryBean hessianInternalMaterialProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/internalMaterialService");
    bean.setServiceInterface(IInternalMaterialService.class);
    return bean;
  }

  @Bean(name = "crmSequenceNumberService")
  public HessianProxyFactoryBean hessianCrmSequenceNumberServiceProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/crmSequenceNumberService");
    bean.setServiceInterface(ICrmSequenceNumberService.class);
    return bean;
  }
}
