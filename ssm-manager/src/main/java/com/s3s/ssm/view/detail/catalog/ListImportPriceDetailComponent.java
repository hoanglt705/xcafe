/*
 * ListImportDetailComponent
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.view.detail.catalog;

import java.util.List;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.ImportPriceDetailDto;
import com.s3s.ssm.dto.SupplierDto;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListImportPriceDetailComponent extends AListComponent<ImportPriceDetailDto> {
  private static final long serialVersionUID = 1L;

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    List<SupplierDto> suppliers = ManagerContextProvider.getInstance().getSupplierService()
            .findByActive(0, Integer.MAX_VALUE);
    listDataModel.addColumn("supplier", ListRendererType.TEXT, ListEditorType.COMBOBOX)
            .setDataList(suppliers);
    listDataModel.addColumn("price", ListRendererType.NUMBER, ListEditorType.LONG);
    listDataModel.addColumn("mainSupplier", ListRendererType.BOOLEAN, ListEditorType.CHECKBOX);
  }
}
