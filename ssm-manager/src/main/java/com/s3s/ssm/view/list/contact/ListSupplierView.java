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
package com.s3s.ssm.view.list.contact;

import java.util.List;

import javax.swing.Icon;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.SupplierDto;
import com.s3s.ssm.export.dto.SupplierExportableDto;
import com.s3s.ssm.view.detail.contact.EditSupplierView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AExportableListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListSupplierView extends AExportableListEntityView<SupplierDto, SupplierExportableDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListSupplierView() {
    super();
  }

  public ListSupplierView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("image", ListRendererType.IMAGE);
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("name", ListRendererType.TEXT);
    listDataModel.addColumn("phone", ListRendererType.TEXT);
    listDataModel.addColumn("address", ListRendererType.TEXT);
  }

  @Override
  protected Class<? extends AbstractEditView<SupplierDto>> getEditViewClass() {
    return EditSupplierView.class;
  }

  @Override
  protected SupplierExportableDto transferToExportData(SupplierDto entity) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected SupplierDto transferToImportData(SupplierExportableDto exportDto) {
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
    setViewService(ManagerContextProvider.getInstance().getSupplierService());
  }
}
