package com.s3s.ssm.view.registry;

import com.s3s.ssm.dto.base.ICodeObject;

public class RegistryDto implements ICodeObject {
  private String name;
  private String company;
  private String machineCode;
  private String registryCode;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getMachineCode() {
    return machineCode;
  }

  public void setMachineCode(String machineCode) {
    this.machineCode = machineCode;
  }

  public String getRegistryCode() {
    return registryCode;
  }

  public void setRegistryCode(String registryCode) {
    this.registryCode = registryCode;
  }

  @Override
  public boolean isActive() {
    return false;
  }

  @Override
  public void setActive(boolean isActive) {
  }

  @Override
  public Long getId() {
    return null;
  }

  @Override
  public void setId(long code) {
  }

  @Override
  public String getCode() {
    return null;
  }

  @Override
  public void setCode(String code) {
  }

}
