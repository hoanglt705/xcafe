/*
 * EditBanchView
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
package com.s3s.ssm.view.detail.employee;

import java.util.Map;

import javax.swing.JPanel;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.RoleDto;
import com.s3s.ssm.security.ACLPanel;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditRoleView extends AEditServiceView<RoleDto> {
  private static final long serialVersionUID = 728867266827208141L;
  private ACLPanel aclPanel;

  public EditRoleView(Map<String, Object> request) {
    super(request);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("block", DetailFieldType.BLOCK).setRaw(true);
  }

  @Override
  protected String getDefaultTitle(RoleDto entity) {
    return entity.getName();
  }

  @Override
  protected void addAditionalComponent(JPanel pnlEdit) {
    // if (!isCreateNew()) {
    // aclPanel = new ACLPanel(entity.getCode());
    // } else {
    // aclPanel = new ACLPanel();
    // }
    // pnlEdit.add(aclPanel, "newline, spanx , width 650");
  }

  @Override
  protected void afterSaveOrUpdate(RoleDto entity) {
    // super.afterSaveOrUpdate(entity);
    // if (isCreateNew()) {
    // aclPanel.setPrincipal(entity.getCode());
    // }
    // aclPanel.saveOrUpdateACL();
  }

  // @Override
  // public boolean isDirty() {
  // return true;
  // }

  @Override
  public IViewService<RoleDto> getViewService() {
    return ManagerContextProvider.getInstance().getRoleService();
  }
}