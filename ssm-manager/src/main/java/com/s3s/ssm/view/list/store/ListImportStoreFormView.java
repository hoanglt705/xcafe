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
package com.s3s.ssm.view.list.store;

import java.util.List;

import javax.swing.Icon;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.ImportStoreFormDto;
import com.s3s.ssm.export.dto.ImportStoreFormExporterDto;
import com.s3s.ssm.util.UIConstants;
import com.s3s.ssm.view.detail.store.EditImportStoreFormView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AExportableListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListImportStoreFormView extends
        AExportableListEntityView<ImportStoreFormDto, ImportStoreFormExporterDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListImportStoreFormView() {
    super();
  }

  public ListImportStoreFormView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("createdDate", ListRendererType.DATE);
    listDataModel.addColumn("staff.name", ListRendererType.TEXT);
    listDataModel.addColumn("supplier.name", ListRendererType.TEXT);
    listDataModel.addColumn("amountTotal", ListRendererType.NUMBER).width(UIConstants.AMT_COLUMN_WIDTH);
  }

  @Override
  protected Class<? extends AbstractEditView<ImportStoreFormDto>> getEditViewClass() {
    return EditImportStoreFormView.class;
  }

  @Override
  protected void afterDeleteRows(List<ImportStoreFormDto> removedEntities) {
    super.afterDeleteRows(removedEntities);
    // ManagerContextProvider.getInstance().getStoreService().recoverProductInStore(removedEntities);
  }

  @Override
  protected ImportStoreFormExporterDto transferToExportData(ImportStoreFormDto entity) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected ImportStoreFormDto transferToImportData(ImportStoreFormExporterDto exportDto) {
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
    setViewService(ManagerContextProvider.getInstance().getImportStoreFormService());
  }

}
