/*
 * EditBanchView
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.s3s.ssm.view.detail.config;

import java.util.List;
import java.util.Map;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.AreaDto;
import com.s3s.ssm.dto.FoodTableDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditFoodTableView extends AEditServiceView<FoodTableDto> {
  private static final long serialVersionUID = 728867266827208141L;

  public EditFoodTableView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    List<AreaDto> areas = ManagerContextProvider.getInstance().getAreaService()
            .findByActive(0, Integer.MAX_VALUE);
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("area", DetailFieldType.DROPDOWN).mandatory(true)
            .setDataList(areas);
    detailDataModel.addAttribute("seatNum", DetailFieldType.POSITIVE_NUMBER_SPINNER).mandatory(true);
  }

  @Override
  protected String getDefaultTitle(FoodTableDto entity) {
    return entity.getName();
  }

  @Override
  public IViewService<FoodTableDto> getViewService() {
    return ManagerContextProvider.getInstance().getFoodTableService();
  }
}
