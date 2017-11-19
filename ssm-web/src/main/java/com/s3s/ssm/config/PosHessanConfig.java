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
import com.s3s.ssm.service.IDataTestService;
import com.s3s.ssm.service.IFoodTableService;
import com.s3s.ssm.service.IInvoiceService;
import com.s3s.ssm.service.IPaymentService;
import com.s3s.ssm.service.IProductService;
import com.s3s.ssm.service.ISequenceNumberService;

@Configuration
public class PosHessanConfig {
  private static final String SERVER_URL = "http://localhost:8081";

  @Bean(name = "paymentService")
  public HessianProxyFactoryBean hessianPaymentProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/PaymentService");
    bean.setServiceInterface(IPaymentService.class);
    return bean;
  }

  @Bean(name = "invoiceService")
  public HessianProxyFactoryBean hessianInvoiceProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/InvoiceService");
    bean.setServiceInterface(IInvoiceService.class);
    return bean;
  }

  @Bean(name = "areaService")
  public HessianProxyFactoryBean hessianAreaProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/AreaService");
    bean.setServiceInterface(IAreaService.class);
    return bean;
  }

  @Bean(name = "companyService")
  public HessianProxyFactoryBean hessianCompanyProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/CompanyService");
    bean.setServiceInterface(ICompanyService.class);
    return bean;
  }

  @Bean(name = "foodTableService")
  public HessianProxyFactoryBean hessianFoodTableProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/FoodTableService");
    bean.setServiceInterface(IFoodTableService.class);
    return bean;
  }

  @Bean(name = "productService")
  public HessianProxyFactoryBean hessianProductProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/ProductService");
    bean.setServiceInterface(IProductService.class);
    return bean;
  }
  
  @Bean(name = "sequenceNumberService")
  public HessianProxyFactoryBean hessianSequenceNumberProxyFactoryBean() {
	  HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
	  bean.setServiceUrl(SERVER_URL + "/SequenceNumberService");
	  bean.setServiceInterface(ISequenceNumberService.class);
	  return bean;
  }
  
  

  @Bean(name = "dataTestService")
  public HessianProxyFactoryBean hessianDataTestProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/DataTestService");
    bean.setServiceInterface(IDataTestService.class);
    return bean;
  }
}
