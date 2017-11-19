package com.s3s.ssm.service;

import java.util.ArrayList;
import java.util.List;

import com.s3s.ssm.dto.FinalPeriodSaleProcessDto;
import com.s3s.ssm.dto.report.FinalPeriodFoodTableProcessDto;
import com.s3s.ssm.dto.report.FinalPeriodProductProcessDto;
import com.sunrise.xdoc.entity.store.FinalPeriodProductProcess;
import com.sunrise.xdoc.entity.store.FinalPeriodSaleProcess;
import com.sunrise.xdoc.entity.store.FinalPeriodTableProcess;

class DtoHelper {
  final static List<FinalPeriodProductProcessDto> generateFinalPeriodProductProcessDto(
          List<FinalPeriodProductProcess> objects) {
    List<FinalPeriodProductProcessDto> result = new ArrayList<>();
    for (FinalPeriodProductProcess finalPeriodProductProcess : objects) {
      result.add(generateFinalPeriodProductProcessDto(finalPeriodProductProcess));
    }
    return result;
  }

  final static FinalPeriodProductProcessDto generateFinalPeriodProductProcessDto(
          FinalPeriodProductProcess object) {
    FinalPeriodProductProcessDto dto = new FinalPeriodProductProcessDto();
    dto.setProcessingDate(object.getProcessingDate());
    dto.setProductName(object.getProduct().getName());
    dto.setUomName(object.getProduct().getUom().getName());
    dto.setQuantityInStock(object.getQuantityInStock());
    dto.setTotalAmount(object.getTotalAmount());
    dto.setImportQuantity(object.getImportQuantity());
    dto.setExportQuantity(object.getExportQuantity());
    dto.setSoldQuantity(object.getSoldQuantity());
    return dto;
  }

  final static List<FinalPeriodFoodTableProcessDto> generateFinalPeriodFoodTableProcessDto(
          List<FinalPeriodTableProcess> objects) {
    List<FinalPeriodFoodTableProcessDto> result = new ArrayList<>();
    for (FinalPeriodTableProcess finalPeriodTableProcess : objects) {
      result.add(generateFinalPeriodFoodTableProcessDto(finalPeriodTableProcess));
    }
    return result;
  }

  final static FinalPeriodFoodTableProcessDto generateFinalPeriodFoodTableProcessDto(
          FinalPeriodTableProcess object) {
    FinalPeriodFoodTableProcessDto dto = new FinalPeriodFoodTableProcessDto();
    dto.setProcessingDate(object.getProcessingDate());
    dto.setFoodTable(object.getFoodTable().getName());
    dto.setTotalAmount(object.getTotalAmount());
    return dto;
  }

  final static FinalPeriodSaleProcessDto generateFinalPeriodSaleProcessDto(FinalPeriodSaleProcess object) {
    FinalPeriodSaleProcessDto dto = new FinalPeriodSaleProcessDto();
    dto.setProcessingDate(object.getProcessingDate());
    dto.setSaleTotal(object.getSaleTotal());
    dto.setInvoiceTotal(object.getInvoiceTotal());
    return dto;
  }
}
