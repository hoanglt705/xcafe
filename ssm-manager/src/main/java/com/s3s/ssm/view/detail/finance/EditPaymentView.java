/*
 * EditPaymentTypeView
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
package com.s3s.ssm.view.detail.finance;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.EmployeeDto;
import com.s3s.ssm.dto.PaymentContentDto;
import com.s3s.ssm.dto.PaymentDto;
import com.s3s.ssm.dto.PaymentMode;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditPaymentView extends AEditServiceView<PaymentDto> {
  private static final long serialVersionUID = -4763938512698675549L;

  public EditPaymentView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void
          initialPresentationView(DetailDataModel detailDataModel) {
    List<EmployeeDto> employees = ManagerContextProvider.getInstance().getEmployeeService()
            .findByActive(0, Integer.MAX_VALUE);
    List<PaymentContentDto> contents = ManagerContextProvider.getInstance().getPaymentContentService()
            .findByActive(0, Integer.MAX_VALUE);
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true).editable(false);
    detailDataModel.addAttribute("paymentDate", DetailFieldType.DATE).mandatory(true);
    detailDataModel.addAttribute("staff", DetailFieldType.DROPDOWN_AUTOCOMPLETE).mandatory(true)
            .setDataList(employees);
    detailDataModel.addAttribute("paymentContent", DetailFieldType.DROPDOWN_AUTOCOMPLETE).mandatory(true)
            .setDataList(contents);
    detailDataModel.addAttribute("paymentMode", DetailFieldType.DROPDOWN).setDataList(
            Arrays.asList(PaymentMode.values()));
    detailDataModel.addAttribute("amount", DetailFieldType.LONG_NUMBER);
    detailDataModel.addAttribute("notes", DetailFieldType.TEXTAREA);
  }

  @Override
  protected PaymentDto loadForCreate(Map<String, Object> request) {
    PaymentDto form = super.loadForCreate(request);
    String code = ManagerContextProvider.getInstance().getSequenceNumberService().getPaymentNextSequence();
    form.setCode(code);
    return form;
  }

  @Override
  public IViewService<PaymentDto> getViewService() {
    return ManagerContextProvider.getInstance().getPaymentService();
  }
}