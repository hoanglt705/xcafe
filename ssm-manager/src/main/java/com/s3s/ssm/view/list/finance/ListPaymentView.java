/*
 * ListBankView
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
package com.s3s.ssm.view.list.finance;

import java.util.List;

import javax.swing.Icon;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.PaymentDto;
import com.s3s.ssm.export.dto.PaymentExporter;
import com.s3s.ssm.view.detail.finance.EditPaymentView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AExportableListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListPaymentView extends AExportableListEntityView<PaymentDto, PaymentExporter> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListPaymentView() {
    super();
  }

  public ListPaymentView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("paymentContent.name", ListRendererType.TEXT);
    listDataModel.addColumn("paymentDate", ListRendererType.DATE);
    listDataModel.addColumn("paymentMode", ListRendererType.TEXT);
    listDataModel.addColumn("amount", ListRendererType.NUMBER);
  }

  @Override
  protected Class<? extends AbstractEditView<PaymentDto>> getEditViewClass() {
    return EditPaymentView.class;
  }

  @Override
  protected PaymentExporter transferToExportData(PaymentDto entity) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected PaymentDto transferToImportData(PaymentExporter exportDto) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected List<String> exportHeader() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected void loadService() {
    setViewService(ManagerContextProvider.getInstance().getPaymentService());
  }
}
