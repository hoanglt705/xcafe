package com.s3s.ssm.service;

import java.util.List;

import com.s3s.ssm.dto.UnitOfMeasureDto;
import com.s3s.ssm.dto.UomCategoryDto;

public interface IUnitOfMeasureService extends IViewService<UnitOfMeasureDto> {

  UnitOfMeasureDto getBaseUnitOfMeasure(UomCategoryDto uomCategoryDto);

  List<UnitOfMeasureDto> findRelatingUom(UnitOfMeasureDto uom);
}
