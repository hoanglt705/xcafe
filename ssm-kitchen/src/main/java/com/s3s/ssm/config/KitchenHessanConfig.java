package com.s3s.ssm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.s3s.ssm.service.ICompanyService;
import com.s3s.ssm.service.IKitchenService;

@Configuration
public class KitchenHessanConfig {
  private static final String SERVER_URL = "http://localhost:8080";

  @Bean(name = "kitchenService")
  public HessianProxyFactoryBean hessianKitchenProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/KitchenService");
    bean.setServiceInterface(IKitchenService.class);
    return bean;
  }

  @Bean(name = "companyService")
  public HessianProxyFactoryBean hessianCompanyProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(SERVER_URL + "/CompanyService");
    bean.setServiceInterface(ICompanyService.class);
    return bean;
  }
}
