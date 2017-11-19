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
package com.s3s.ssm.view.detail.security;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.SecurityRoleDto;
import com.s3s.ssm.dto.SecurityUserDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditSecurityUserView extends AEditServiceView<SecurityUserDto> {
  private static final long serialVersionUID = 728867266827208141L;

  public EditSecurityUserView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    List<SecurityRoleDto> roles = ManagerContextProvider.getInstance().getSecurityRoleService()
            .findByActive(0, Integer.MAX_VALUE);

    detailDataModel.addAttribute("username", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("password", DetailFieldType.PASSWORD).mandatory(true);
    detailDataModel.addAttribute("role", DetailFieldType.DROPDOWN_AUTOCOMPLETE).mandatory(true)
            .setDataList(roles);
  }

  @Override
  protected SecurityUserDto loadForCreate(Map<String, Object> request) {
    SecurityUserDto user = super.loadForCreate(request);
    user.setCode(RandomUtils.nextInt() + "");
    return user;
  }

  @Override
  protected String getDefaultTitle(SecurityUserDto entity) {
    return entity.getUsername();
  }

  @Override
  public IViewService<SecurityUserDto> getViewService() {
    return ManagerContextProvider.getInstance().getSecurityUserService();
  }
}
