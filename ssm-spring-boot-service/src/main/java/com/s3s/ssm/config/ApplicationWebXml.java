package com.s3s.ssm.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import com.s3s.ssm.data.SSMDataLoader;

public class ApplicationWebXml extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(ApplicationConfig.class);
  }

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    super.onStartup(servletContext);
    SSMDataLoader.initDatabase();
  }

}
