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

import java.util.List;
import java.util.Map;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.EmployeeDto;
import com.s3s.ssm.dto.RoleDto;
import com.s3s.ssm.dto.ShiftDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditEmployeeView extends AEditServiceView<EmployeeDto> {
  private static final long serialVersionUID = 728867266827208141L;

  public EditEmployeeView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    List<ShiftDto> shifts = ManagerContextProvider.getInstance().getShiftService()
            .findByActive(0, Integer.MAX_VALUE);
    List<RoleDto> roles = ManagerContextProvider.getInstance().getRoleService()
            .findByActive(0, Integer.MAX_VALUE);
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX)
            .mandatory(true);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("birthday", DetailFieldType.DATE).mandatory(true);
    detailDataModel.addAttribute("role", DetailFieldType.DROPDOWN).setDataList(roles);
    detailDataModel.addAttribute("identityCard", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("salary", DetailFieldType.LONG_NUMBER).mandatory(true);
    detailDataModel.addAttribute("image", DetailFieldType.IMAGE);
    detailDataModel.addAttribute("address", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("email", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("phone", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("shifts", DetailFieldType.CHECKBOX_LIST).setDataList(shifts);
  }

  @Override
  protected String getDefaultTitle(EmployeeDto entity) {
    return entity.getName();
  }

  @Override
  protected EmployeeDto loadForCreate(Map<String, Object> request) {
    EmployeeDto employee = super.loadForCreate(request);
    String code = ManagerContextProvider.getInstance().getSequenceNumberService().getEmployeeNextSequence();
    employee.setCode(code);
    return employee;
  }

  @Override
  public IViewService<EmployeeDto> getViewService() {
    return ManagerContextProvider.getInstance().getEmployeeService();
  }
}
