package com.s3s.ssm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.s3s.ssm.service.ICompanyService;
import com.s3s.ssm.service.ITimelineService;

@Configuration
@PropertySource("classpath:application.properties")
public class TimelineHessanConfig {
  @Autowired
  private Environment env;

  @Bean(name = "companyService")
  public HessianProxyFactoryBean hessianCompanyProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(env.getProperty("server.url") + "/CompanyService");
    bean.setServiceInterface(ICompanyService.class);
    return bean;
  }

  @Bean(name = "timelineService")
  public HessianProxyFactoryBean hessianTimelineProxyFactoryBean() {
    HessianProxyFactoryBean bean = new HessianProxyFactoryBean();
    bean.setServiceUrl(env.getProperty("server.url") + "/TimelineService");
    bean.setServiceInterface(ITimelineService.class);
    return bean;
  }
}
