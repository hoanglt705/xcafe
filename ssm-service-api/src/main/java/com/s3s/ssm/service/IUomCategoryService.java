package com.s3s.ssm.service;

import java.util.List;

import com.s3s.ssm.dto.UomCategoryDto;

public interface IUomCategoryService extends IViewService<UomCategoryDto> {

  List<UomCategoryDto> getSuggestParent(UomCategoryDto uomCategoryDto);
}
