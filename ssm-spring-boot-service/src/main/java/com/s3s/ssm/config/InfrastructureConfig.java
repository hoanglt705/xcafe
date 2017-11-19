/*
 * Copyright 2012-2014 the original author or authors.
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

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.s3s.ssm.security.entity.SecurityRole;
import com.sunrise.xdoc.entity.catalog.Product;
import com.sunrise.xdoc.entity.config.Area;
import com.sunrise.xdoc.entity.contact.Supplier;
import com.sunrise.xdoc.entity.employee.Employee;
import com.sunrise.xdoc.entity.finance.Payment;
import com.sunrise.xdoc.entity.sale.Invoice;
import com.sunrise.xdoc.entity.store.FinalPeriodSaleProcess;
import com.sunrise.xdoc.entity.system.SequenceNumber;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:properties/database.properties")
public class InfrastructureConfig {
  @Autowired
  Environment env;

  @Bean(name = "dataSource")
  public DataSource dataSource() {
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    builder.setType(EmbeddedDatabaseType.HSQL);
    return builder.build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setDatabase(Database.HSQL);
    vendorAdapter.setGenerateDdl(true);

    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setPackagesToScan(Area.class.getPackage().getName(), Payment.class.getPackage().getName(),
            Employee.class.getPackage().getName(), Invoice.class.getPackage().getName(), Product.class
                    .getPackage().getName(), Supplier.class.getPackage().getName(),
            FinalPeriodSaleProcess.class.getPackage().getName(), SequenceNumber.class.getPackage().getName(),
            SecurityRole.class.getPackage().getName());
    factory.setDataSource(dataSource());
    return factory;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return txManager;
  }
}
