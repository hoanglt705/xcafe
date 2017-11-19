package com.s3s.ssm.service;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.data.SSMDataLoader;

@Component("dataTestService")
@Transactional
public class DataTestServiceImpl implements IDataTestService {
  @Autowired
  private DataSource dataSource;

  @Override
  public void reloadData() {
    removingForeignKeys();
    SSMDataLoader.cleanDatabase();
    SSMDataLoader.initEmployeeModule();
    SSMDataLoader.initCompany();
    SSMDataLoader.initFoodTable(SSMDataLoader.initArea());
    SSMDataLoader.initProductType();
    SSMDataLoader.initUOMCategory();
    SSMDataLoader.initUOM();
    SSMDataLoader.initSupplier();
    SSMDataLoader.initProduct();
    SSMDataLoader.initPaymentContent();
  }

  protected void removingForeignKeys() {
    try {
      dataSource.getConnection().prepareStatement("SET DATABASE REFERENTIAL INTEGRITY FALSE").execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
