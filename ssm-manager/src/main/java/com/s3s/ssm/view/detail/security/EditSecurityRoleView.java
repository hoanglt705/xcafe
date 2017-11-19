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
package com.s3s.ssm.view.detail.security;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.commons.lang.math.RandomUtils;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.SecurityRoleDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

import de.javasoft.swing.JYCheckBoxTree;

public class EditSecurityRoleView extends AEditServiceView<SecurityRoleDto> {
  private static final long serialVersionUID = 728867266827208141L;
  private static final String SYSTEM_SETTING = "Cai dat he thong";
  private JYCheckBoxTree tree;

  public EditSecurityRoleView(Map<String, Object> request) {
    super(request);
    getBtnSave().addActionListener(new ActionListener() {
      @SuppressWarnings("unused")
      @Override
      public void actionPerformed(ActionEvent e) {
        EditSecurityRoleView.this.doSave();
      }
    });
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    if (entity.getCode() == null) {
      entity.setCode(RandomUtils.nextInt() + "");
    }
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("note", DetailFieldType.TEXTAREA);
    detailDataModel.addAttribute("block", DetailFieldType.BLOCK).setRaw(true);
  }

  @Override
  protected void addAditionalComponent(JPanel pnlEdit) {
    tree = new JYCheckBoxTree(createTreeNode());
    tree.addTreeSelectionListener(new TreeSelectionListener() {
      @SuppressWarnings("unused")
      @Override
      public void valueChanged(TreeSelectionEvent e) {
        setVisibleSaveButton(true);
      }
    });
    initSelectionOfTree();
    tree.setShowsRootHandles(true);
    tree.setCheckBoxSelectableByNodeClick(false);
    tree.setToggleClickCount(2);
    for (int i = 0; i < tree.getRowCount(); i++) {
      tree.expandRow(i);
    }
    pnlEdit.add(new JScrollPane(tree), "newline, spanx , width 300");
  }

  private void initSelectionOfTree() {
    if (entity.isSettingAuth()) {
      tree.getCheckBoxSelectionModel().addSelectionPath(tree.getPathForRow(1));
    }
  }

  private TreeNode createTreeNode() {
    DefaultMutableTreeNode allNode = new DefaultMutableTreeNode("Tat ca");
    DefaultMutableTreeNode settingAuth = new DefaultMutableTreeNode(SYSTEM_SETTING);
    DefaultMutableTreeNode tableManageAuth = new DefaultMutableTreeNode("Quan ly ban");
    allNode.add(settingAuth);
    allNode.add(tableManageAuth);
    return allNode;
  }

  @Override
  protected void saveOrUpdate(SecurityRoleDto entity) {
    TreePath[] selectionPaths = tree.getCheckBoxSelectionModel().getSelectionPaths();
    entity.setSettingAuth(false);
    for (TreePath p : selectionPaths) {
      if (p.getPathCount() == 2) {
        boolean selected = tree.getCheckBoxSelectionModel().isPathSelected(p);
        String auth = (String) ((DefaultMutableTreeNode) p.getLastPathComponent()).getUserObject();
        switch (auth) {
          case SYSTEM_SETTING:
            entity.setSettingAuth(selected);
            break;

          default:
            break;
        }
      }
    }
    super.saveOrUpdate(entity);
  }

  @Override
  protected String getDefaultTitle(SecurityRoleDto entity) {
    return entity.getName();
  }

  @Override
  public IViewService<SecurityRoleDto> getViewService() {
    return ManagerContextProvider.getInstance().getSecurityRoleService();
  }
}