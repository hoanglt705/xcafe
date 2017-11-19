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

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.s3s.ssm.service.ICompanyService;
import com.s3s.ssm.service.IDashboardService;
import com.s3s.ssm.service.IReportService;
import com.s3s.ssm.util.Util;

@Configuration
public class DashboardHessanConfig {
  @Bean(name = "dashboardService")
  public HessianProxyFactoryBean hessianDashboardProxyFactoryBean() throws IOException {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(Util.getHubUrl() + "/DashboardService");
    bean.setServiceInterface(IDashboardService.class);
    return bean;
  }

  @Bean(name = "reportService")
  public HessianProxyFactoryBean hessianReportProxyFactoryBean() throws IOException {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(Util.getHubUrl() + "/ReportService");
    bean.setServiceInterface(IReportService.class);
    return bean;
  }

  @Bean(name = "companyService")
  public HessianProxyFactoryBean hessianCompanyProxyFactoryBean() throws IOException {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(Util.getHubUrl() + "/CompanyService");
    bean.setServiceInterface(ICompanyService.class);
    return bean;
  }

}
