package com.s3s.ssm.export.dto;

import java.util.ArrayList;
import java.util.List;

import net.sf.jsefa.common.validator.LongValidator;
import net.sf.jsefa.csv.annotation.CsvDataType;
import net.sf.jsefa.csv.annotation.CsvField;
import net.sf.jsefa.csv.annotation.CsvSubRecordList;
import net.sf.jsefa.rbf.annotation.Record;

@CsvDataType(defaultPrefix = "Food")
public class FoodExporterDto extends ProductExporterDto {
  @CsvField(pos = 7, validatorType = LongValidator.class)
  private Long unitPrice;
  @CsvSubRecordList(pos = 1, records = {@Record(prefix = "    ")})
  private List<FoodIngredientExporterDto> ingredientDtos = new ArrayList<>();

  public Long getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Long unitPrice) {
    this.unitPrice = unitPrice;
  }

  public List<FoodIngredientExporterDto> getIngredientDtos() {
    return ingredientDtos;
  }

  public void setIngredientDtos(List<FoodIngredientExporterDto> ingredientDtos) {
    this.ingredientDtos = ingredientDtos;
  }

}
