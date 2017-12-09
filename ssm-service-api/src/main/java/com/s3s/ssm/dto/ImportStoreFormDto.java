package com.s3s.ssm.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class ImportStoreFormDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private Date createdDate = new Date();
  private String description;
  private EmployeeDto staff;
  private Long amountTotal;
  private Long quantityTotal;
  private SupplierDto supplier;
  private List<ImportStoreDetailDto> importDetails = new ArrayList<ImportStoreDetailDto>();

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public EmployeeDto getStaff() {
    return staff;
  }

  public void setStaff(EmployeeDto staff) {
    this.staff = staff;
  }

  public Long getAmountTotal() {
    return amountTotal;
  }

  public void setAmountTotal(Long amountTotal) {
    this.amountTotal = amountTotal;
  }

  public Long getQuantityTotal() {
    return quantityTotal;
  }

  public void setQuantityTotal(Long quantityTotal) {
    this.quantityTotal = quantityTotal;
  }

  public SupplierDto getSupplier() {
    return supplier;
  }

  public void setSupplier(SupplierDto supplier) {
    this.supplier = supplier;
  }

  public List<ImportStoreDetailDto> getImportDetails() {
    return importDetails;
  }

  public void setImportDetails(List<ImportStoreDetailDto> importDetails) {
    this.importDetails = importDetails;
  }

}
