/*
 * ACLPanel
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
package com.s3s.ssm.security;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.s3s.ssm.dto.ACLResourceEnum;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.layout.GridBagLayoutBuilder;

public class ACLPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private String principal;
  private List<CheckBoxInfo> checkBoxInfos = new ArrayList<ACLPanel.CheckBoxInfo>();

  public ACLPanel(String roleCode) {
    principal = roleCode;
    initialPresentationView();
  }

  public ACLPanel() {
    this(null);
  }

  private void initialPresentationView() {
    setBorder(BorderFactory.createTitledBorder(ControlConfigUtils.getString("label.Role.authorization")));
    GridBagLayoutBuilder builder = new GridBagLayoutBuilder();
    builder.setDefaultInsets(new Insets(5, 30, 5, 30));
    builder.setShowGuidelines(true);
    builder.append(new JLabel());
    builder.append(new JLabel("Admin"));
    builder.append(new JLabel("Create"));
    builder.append(new JLabel("Read"));
    builder.append(new JLabel("Write"));
    builder.append(new JLabel("Delete"));
    builder.nextLine();
    for (ACLResourceEnum resource : ACLResourceEnum.values()) {
      addRow(resource.getLabel(), resource.getOrder(), builder);
      builder.nextLine();
    }
    setLayout(new FlowLayout(FlowLayout.LEFT));
    add(builder.getPanel());
  }

  private void addRow(String resourceName, int identifier, GridBagLayoutBuilder builder) {
    boolean viewAdmin = false;
    boolean viewCreate = false;
    boolean viewRead = false;
    boolean viewWrite = false;
    boolean viewDelete = false;

    JLabel lblResource = new JLabel(ControlConfigUtils.getString(resourceName));

    JCheckBox chkAdmin = new JCheckBox("", viewAdmin);
    JCheckBox chkCreate = new JCheckBox("", viewCreate);
    JCheckBox chkRead = new JCheckBox("", viewRead);
    JCheckBox chkWrite = new JCheckBox("", viewWrite);
    JCheckBox chkDel = new JCheckBox("", viewDelete);

    builder.append(lblResource);
    builder.append(chkAdmin);
    builder.append(chkCreate);
    builder.append(chkRead);
    builder.append(chkWrite);
    builder.append(chkDel);

    CheckBoxInfo info = new CheckBoxInfo();
    info.setIdentifier(identifier);
    info.setChkAdmin(chkAdmin);
    info.setChkCreate(chkCreate);
    info.setChkRead(chkRead);
    info.setChkWrite(chkWrite);
    info.setChkDel(chkDel);

    checkBoxInfos.add(info);
  }

  public void saveOrUpdateACL() {
    for (CheckBoxInfo info : checkBoxInfos) {
    }
  }

  private class CheckBoxInfo {
    private int identifier;

    private JCheckBox chkAdmin;
    private JCheckBox chkCreate;
    private JCheckBox chkRead;
    private JCheckBox chkWrite;
    private JCheckBox chkDel;

    public boolean isAdminSelected() {
      return chkAdmin.isSelected();
    }

    public boolean isCreateSelected() {
      return chkCreate.isSelected();
    }

    public boolean isReadSelected() {
      return chkRead.isSelected();
    }

    public boolean isWriteSelected() {
      return chkWrite.isSelected();
    }

    public boolean isDelSelected() {
      return chkDel.isSelected();
    }

    public void setChkAdmin(JCheckBox chkAdmin) {
      this.chkAdmin = chkAdmin;
    }

    public void setChkCreate(JCheckBox chkCreate) {
      this.chkCreate = chkCreate;
    }

    public void setChkRead(JCheckBox chkRead) {
      this.chkRead = chkRead;
    }

    public void setChkWrite(JCheckBox chkWrite) {
      this.chkWrite = chkWrite;
    }

    public void setChkDel(JCheckBox chkDel) {
      this.chkDel = chkDel;
    }

    public int getIdentifier() {
      return identifier;
    }

    public void setIdentifier(int identifier) {
      this.identifier = identifier;
    }

  }

  public void setPrincipal(String code) {
    principal = code;
  }
}
