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

package com.s3s.ssm.view.detail.sale;

import java.util.List;

import org.bushe.swing.event.EventBus;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.InvoiceDetailDto;
import com.s3s.ssm.dto.ProductDto;
import com.s3s.ssm.view.event.DetailEvent;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListInvoiceDetailComponent extends AListComponent<InvoiceDetailDto> {
  private static final long serialVersionUID = 9143672291866681219L;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    List<ProductDto> sellableProduct = ManagerContextProvider.getInstance().getProductService()
            .getSellableProduct();
    listDataModel.addColumn("product", ListRendererType.TEXT, ListEditorType.COMBOBOX).setDataList(
            sellableProduct);
    listDataModel.addColumn("uom", ListRendererType.TEXT).notEditable();
    listDataModel.addColumn("quantity", ListRendererType.NUMBER, ListEditorType.TEXTFIELD);
    listDataModel.addColumn("unitPrice", ListRendererType.NUMBER, ListEditorType.TEXTFIELD);
    listDataModel.addColumn("amount", ListRendererType.NUMBER, ListEditorType.TEXTFIELD);
  }

  @Override
  protected void doRowUpdated(String attributeName, InvoiceDetailDto entityUpdated) {
    ProductDto productDto = entityUpdated.getProduct();
    if (productDto != null) {
      if ("product".equals(attributeName)) {
        entityUpdated.setUnitPrice(productDto.getSellPrice());
        entityUpdated.setQuantity(1);
        entityUpdated.setUom(productDto.getUom());
      }
      entityUpdated.setAmount(entityUpdated.getQuantity() * entityUpdated.getUnitPrice());
      EventBus.publish(new DetailEvent(getEntities()));
    }
  }
}
