/*
 * EditUnitOfMeasureView
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
package com.s3s.ssm.view.detail.config;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.UnitOfMeasureDto;
import com.s3s.ssm.dto.UomCategoryDto;
import com.s3s.ssm.service.IUnitOfMeasureService;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditUnitOfMeasureView extends AEditServiceView<UnitOfMeasureDto> {
  private static final long serialVersionUID = 931208446206176131L;

  public EditUnitOfMeasureView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    List<UomCategoryDto> uomCates = ManagerContextProvider.getInstance().getUomCategoryService()
            .findAll(0, Integer.MAX_VALUE);
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("shortName", DetailFieldType.TEXTBOX);
    detailDataModel.addAttribute("uomCategory", DetailFieldType.DROPDOWN)
            .setDataList(uomCates);
    detailDataModel.addAttribute("isBaseMeasure", DetailFieldType.CHECKBOX);
    detailDataModel.addAttribute("exchangeRate", DetailFieldType.TEXTBOX);
    detailDataModel.addRawAttribute("baseUom", DetailFieldType.LABEL).value(getBaseUomName(entity))
            .editable(false);
  }

  private String getBaseUomName(UnitOfMeasureDto entity) {
    UomCategoryDto uomCateDto = entity.getUomCategory();
    if (uomCateDto != null) {
      UnitOfMeasureDto baseUom = ((IUnitOfMeasureService) getViewService()).getBaseUnitOfMeasure(uomCateDto);
      if (baseUom != null) {
        return baseUom.getName();
      }
    }
    return "";
  }

  @Override
  protected void customizeComponents() {
    final JLabel lblBaseUom = (JLabel) getComponent("baseUom");
    @SuppressWarnings("unchecked")
    final JComboBox<UomCategoryDto> cbUomCate = (JComboBox<UomCategoryDto>) getComponent("uomCategory");

    resetBaseUom(entity.getUomCategory(), lblBaseUom);

    cbUomCate.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        UomCategoryDto cate = (UomCategoryDto) cbUomCate.getSelectedItem();
        resetBaseUom(cate, lblBaseUom);
      }
    });
  }

  private void resetBaseUom(UomCategoryDto uomCateDto, JLabel lblBaseUom) {
    String baseUomName = "";
    if (uomCateDto != null) {
      UnitOfMeasureDto baseUom = ((IUnitOfMeasureService) getViewService()).getBaseUnitOfMeasure(uomCateDto);
      baseUomName = baseUom != null ? baseUom.getName() : "";
    }
    lblBaseUom.setText(baseUomName);
  }

  @Override
  protected String getDefaultTitle(UnitOfMeasureDto entity) {
    return entity.getName();
  }

  @Override
  public IViewService<UnitOfMeasureDto> getViewService() {
    return ManagerContextProvider.getInstance().getUnitOfMeasureService();
  }
}
