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

import java.util.Map;

import javax.swing.JOptionPane;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.CompanyDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditCompanyView extends AEditServiceView<CompanyDto> {
  private static final long serialVersionUID = 728867266827208141L;
  private static String infoTab = ControlConfigUtils.getString("label.CompanyDto.infoTab");
  private static String ruleCodeTab = ControlConfigUtils.getString("label.CompanyDto.ruleCodeTab");
  private static String posTab = ControlConfigUtils.getString("label.CompanyDto.posTab");

  public EditCompanyView(Map<String, Object> request) {
    super(request);
  }

  @Override
  protected boolean exists(@SuppressWarnings("unused") String code) {
    return false;
  }

  @SuppressWarnings("unused")
  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    detailDataModel.tab(infoTab, infoTab, null);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("phone", DetailFieldType.TEXTBOX);
    detailDataModel.addAttribute("fixPhone", DetailFieldType.TEXTBOX);
    detailDataModel.addAttribute("address", DetailFieldType.TEXTAREA);
    detailDataModel.addAttribute("openTime", DetailFieldType.HOUR_MIN);
    detailDataModel.addAttribute("closeTime", DetailFieldType.HOUR_MIN);

    detailDataModel.tab(ruleCodeTab, ruleCodeTab, null);
    detailDataModel.addAttribute("codeLength", DetailFieldType.POSITIVE_NUMBER_SPINNER).mandatory(true);
    detailDataModel.addAttribute("invoiceCodeRule", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("importFormCodeRule", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("exportFormCodeRule", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("paymentCodeRule", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("receiptCodeRule", DetailFieldType.TEXTBOX).mandatory(true);

    detailDataModel.tab(posTab, posTab, null);
    detailDataModel.addAttribute("tableViewAlertTime", DetailFieldType.HOUR_MIN_SEC);
  }

  @Override
  protected String getDefaultTitle(CompanyDto entity) {
    return entity.getName();
  }

  @Override
  public IViewService<CompanyDto> getViewService() {
    return ManagerContextProvider.getInstance().getCompanyService();
  }

  @Override
  protected void afterSaveOrUpdate(@SuppressWarnings("unused") CompanyDto entity) {
    JOptionPane.showMessageDialog(this.getRootPane(),
            ControlConfigUtils.getString("label.CompanyDto.message.beforeSave"));
  }

}
