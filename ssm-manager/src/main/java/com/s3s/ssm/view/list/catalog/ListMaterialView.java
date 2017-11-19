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
package com.s3s.ssm.view.list.catalog;

import java.util.List;

import javax.swing.Icon;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.MaterialDto;
import com.s3s.ssm.export.dto.MaterialExporterDto;
import com.s3s.ssm.view.detail.catalog.EditMaterialView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AExportableListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListMaterialView extends AExportableListEntityView<MaterialDto, MaterialExporterDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListMaterialView() {
    super();
  }

  public ListMaterialView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("image", ListRendererType.IMAGE);
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("name", ListRendererType.TEXT);
    listDataModel.addColumn("productType.name", ListRendererType.TEXT);
    listDataModel.addColumn("retailable", ListRendererType.BOOLEAN);
    listDataModel.addColumn("quantityInStock", ListRendererType.NUMBER);
    listDataModel.addColumn("minimumQuantity", ListRendererType.NUMBER);
  }

  @Override
  protected Class<? extends AbstractEditView<MaterialDto>> getEditViewClass() {
    return EditMaterialView.class;
  }

  @Override
  protected MaterialExporterDto transferToExportData(MaterialDto entity) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected MaterialDto transferToImportData(MaterialExporterDto exportDto) {
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
    setViewService(ManagerContextProvider.getInstance().getMaterialService());
  }
}
