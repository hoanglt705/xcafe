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
package com.s3s.ssm.view.detail.employee;

import java.util.Map;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.ShiftDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditShiftView extends AEditServiceView<ShiftDto> {
  private static final long serialVersionUID = 728867266827208141L;

  public EditShiftView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("startTime", DetailFieldType.HOUR_MIN).mandatory(true);
    detailDataModel.addAttribute("endTime", DetailFieldType.HOUR_MIN).mandatory(true);
  }

  @Override
  protected String getDefaultTitle(ShiftDto entity) {
    return entity.getName();
  }

  @Override
  public IViewService<ShiftDto> getViewService() {
    return ManagerContextProvider.getInstance().getShiftService();
  }
}
