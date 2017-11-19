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
package com.s3s.ssm.view.list.sale;

import java.util.List;

import javax.swing.Icon;

import com.s3s.ssm.dto.InvoiceDto;
import com.s3s.ssm.export.dto.InvoiceExporterDto;
import com.s3s.ssm.util.UIConstants;
import com.s3s.ssm.view.detail.sale.EditRetailInvoiceView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AExportableListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListRetailInvoiceView extends AExportableListEntityView<InvoiceDto, InvoiceExporterDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListRetailInvoiceView() {
    super();
  }

  public ListRetailInvoiceView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("createdDate", ListRendererType.DATE);
    listDataModel.addColumn("totalAmount", ListRendererType.NUMBER).width(UIConstants.AMT_COLUMN_WIDTH);
    listDataModel.addColumn("totalReturnAmount", ListRendererType.NUMBER).width(UIConstants.AMT_COLUMN_WIDTH);
    listDataModel.addColumn("totalPaymentAmount", ListRendererType.NUMBER)
            .width(UIConstants.AMT_COLUMN_WIDTH);
  }

  @Override
  protected Class<? extends AbstractEditView<InvoiceDto>> getEditViewClass() {
    return EditRetailInvoiceView.class;
  }

  @Override
  protected InvoiceExporterDto transferToExportData(InvoiceDto entity) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected InvoiceDto transferToImportData(InvoiceExporterDto exportDto) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected List<String> exportHeader() {
    // TODO Auto-generated method stub
    return null;
  }
}
