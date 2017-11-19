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

import com.s3s.ssm.service.IFoodTableService;
import com.s3s.ssm.service.IProductService;
import com.s3s.ssm.service.IReportService;

@Configuration
public class ReportHessanConfig {
  private static final String SERVER_URL = "http://localhost:8081";

  @Bean(name = "reportService")
  public HessianProxyFactoryBean hessianReportProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/ReportService");
    bean.setServiceInterface(IReportService.class);
    return bean;
  }

  @Bean(name = "productService")
  public HessianProxyFactoryBean hessianProductProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/ProductService");
    bean.setServiceInterface(IProductService.class);
    return bean;
  }

  @Bean(name = "foodTableService")
  public HessianProxyFactoryBean hessianFoodTableProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/FoodTableService");
    bean.setServiceInterface(IFoodTableService.class);
    return bean;
  }
}
