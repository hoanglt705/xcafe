package com.s3s.ssm.dto;

import java.util.ArrayList;
import java.util.List;

public class MaterialDto extends ProductDto {
  private static final long serialVersionUID = 1L;
  private Long beginningQuantityInStock = 0l;
  private Boolean retailable = false;
  private List<ImportPriceDetailDto> importPrices = new ArrayList<>();

  public Long getBeginningQuantityInStock() {
    return beginningQuantityInStock;
  }

  public void setBeginningQuantityInStock(Long beginningQuantityInStock) {
    this.beginningQuantityInStock = beginningQuantityInStock;
  }

  public Boolean getRetailable() {
    return retailable;
  }

  public void setRetailable(Boolean retailable) {
    this.retailable = retailable;
  }

  public List<ImportPriceDetailDto> getImportPrices() {
    return importPrices;
  }

  public void setImportPrices(List<ImportPriceDetailDto> importPrices) {
    this.importPrices = importPrices;
  }
}
