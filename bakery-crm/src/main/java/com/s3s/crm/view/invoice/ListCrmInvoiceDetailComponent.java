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

package com.s3s.crm.view.invoice;

import java.util.List;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.CrmInvoiceDetailDto;
import com.s3s.crm.dto.CrmProductDto;
import com.s3s.crm.dto.InternalMaterialDto;
import com.s3s.crm.dto.MaterialTypeDto;
import com.s3s.crm.dto.ShapeDto;
import com.s3s.crm.dto.SizeDto;
import com.s3s.crm.util.ProductUtils;
import com.s3s.ssm.view.event.DetailEvent;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListCrmInvoiceDetailComponent extends AListComponent<CrmInvoiceDetailDto> {
  private static final long serialVersionUID = 9143672291866681219L;

  public ListCrmInvoiceDetailComponent() {
    super();
    AnnotationProcessor.process(this);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    List<MaterialTypeDto> materialTypes = CrmContextProvider.getInstance().getMaterialTypeService()
            .findByActive(0, Integer.MAX_VALUE);
    materialTypes.add(0, null);
    List<SizeDto> sizes = CrmContextProvider.getInstance().getSizeService()
            .findByActive(0, Integer.MAX_VALUE);
    sizes.add(0, null);
    List<ShapeDto> shapes = CrmContextProvider.getInstance().getShapeService()
            .findByActive(0, Integer.MAX_VALUE);
    shapes.add(0, null);
    List<InternalMaterialDto> internalMaterial = CrmContextProvider.getInstance()
            .getInternalMaterialService().findByActive(0, Integer.MAX_VALUE);
    internalMaterial.add(0, null);

    listDataModel.addColumn("materialType", ListRendererType.TEXT, ListEditorType.COMBOBOX).width(200)
            .setDataList(materialTypes);
    listDataModel.addColumn("size", ListRendererType.TEXT, ListEditorType.COMBOBOX).width(200)
            .setDataList(sizes);
    listDataModel.addColumn("shape", ListRendererType.TEXT, ListEditorType.COMBOBOX).width(200)
            .setDataList(shapes);
    listDataModel.addColumn("internalMaterial", ListRendererType.TEXT, ListEditorType.COMBOBOX).width(200)
            .setDataList(internalMaterial);
    listDataModel.addColumn("unitPrice", ListRendererType.NUMBER, ListEditorType.TEXTFIELD).width(100);
    listDataModel.addColumn("quantity", ListRendererType.NUMBER, ListEditorType.TEXTFIELD).width(100);
    listDataModel.addColumn("amount", ListRendererType.NUMBER, ListEditorType.TEXTFIELD).width(100);
  }

  @Override
  protected void doRowUpdated(String attributeName, CrmInvoiceDetailDto entityUpdated) {
    switch (attributeName) {
      case "materialType":
      case "size":
      case "shape":
      case "internalMaterial":
        MaterialTypeDto materialType = entityUpdated.getMaterialType();
        SizeDto size = entityUpdated.getSize();
        ShapeDto shape = entityUpdated.getShape();
        InternalMaterialDto internalMaterial = entityUpdated.getInternalMaterial();
        String tag = ProductUtils.generateTag(materialType, size, shape, internalMaterial);
        List<CrmProductDto> products = CrmContextProvider.getInstance().getCrmProductService()
                .findByActive(0, Integer.MAX_VALUE);
        CrmProductDto foundProductDto = null;
        for (CrmProductDto crmProductDto : products) {
          if (tag.equals(crmProductDto.getTag())) {
            foundProductDto = crmProductDto;
            break;
          }
        }
        if (foundProductDto != null) {
          entityUpdated.setProduct(foundProductDto);
          entityUpdated.setUnitPrice(entityUpdated.getProduct().getSellPrice());
          entityUpdated.setAmount(entityUpdated.getUnitPrice() * entityUpdated.getQuantity());
          EventBus.publish(new DetailEvent(getEntities()));
        }
        break;
      default:
        if (entityUpdated.getProduct() != null) {
          entityUpdated.setAmount(entityUpdated.getUnitPrice() * entityUpdated.getQuantity());
        }
        EventBus.publish(new DetailEvent(getEntities()));
        break;
    }
  }
}
