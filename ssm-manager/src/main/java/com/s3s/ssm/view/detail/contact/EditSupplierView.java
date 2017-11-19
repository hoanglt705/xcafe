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
package com.s3s.ssm.view.detail.contact;

import java.util.Map;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.SupplierDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditSupplierView extends AEditServiceView<SupplierDto> {
  private static final long serialVersionUID = 728867266827208141L;

  public EditSupplierView(Map<String, Object> request) {
    super(request);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("image", DetailFieldType.IMAGE);
    detailDataModel.addAttribute("representer", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("position", DetailFieldType.TEXTBOX);
    detailDataModel.addAttribute("address", DetailFieldType.TEXTAREA).mandatory(true);
    detailDataModel.addAttribute("phone", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("email", DetailFieldType.TEXTBOX);
    detailDataModel.addAttribute("website", DetailFieldType.TEXTBOX);
    detailDataModel.addAttribute("contactPerson1", DetailFieldType.TEXTBOX);
    detailDataModel.addAttribute("phone1", DetailFieldType.TEXTBOX);
    detailDataModel.addAttribute("contactPerson2", DetailFieldType.TEXTBOX);
    detailDataModel.addAttribute("phone2", DetailFieldType.TEXTBOX);
  }

  @Override
  protected String getDefaultTitle(SupplierDto entity) {
    return entity.getName();
  }

  @Override
  public IViewService<SupplierDto> getViewService() {
    return ManagerContextProvider.getInstance().getSupplierService();
  }
}
