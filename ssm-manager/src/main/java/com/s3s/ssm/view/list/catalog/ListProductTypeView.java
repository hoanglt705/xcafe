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

import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;

import org.springframework.beans.BeanUtils;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.ProductTypeDto;
import com.s3s.ssm.export.dto.ProductTypeExporterDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.catalog.EditProductTypeView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AExportableListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListProductTypeView extends AExportableListEntityView<ProductTypeDto, ProductTypeExporterDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListProductTypeView() {
    super();
  }

  public ListProductTypeView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("image", ListRendererType.IMAGE);
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("name", ListRendererType.TEXT);
  }

  @Override
  protected Class<? extends AbstractEditView<ProductTypeDto>> getEditViewClass() {
    return EditProductTypeView.class;
  }

  @Override
  protected List<String> exportHeader() {
    return Arrays.asList("code", "name");
  }

  @Override
  protected ProductTypeExporterDto transferToExportData(ProductTypeDto entity) {
    ProductTypeExporterDto dto = new ProductTypeExporterDto();
    BeanUtils.copyProperties(entity, dto);
    return dto;
  }

  @Override
  protected ProductTypeDto transferToImportData(ProductTypeExporterDto exportDto) {
    ProductTypeDto productType = new ProductTypeDto();
    BeanUtils.copyProperties(exportDto, productType);
    return productType;
  }

  @Override
  public IViewService<ProductTypeDto> getViewService() {
    return ManagerContextProvider.getInstance().getProductTypeService();
  }
}
