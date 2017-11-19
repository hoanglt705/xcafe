/*
 * MainProgram
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
package com.s3s.ssm.view;

import javax.swing.UIManager;

import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;

import com.s3s.ssm.view.LookAndFeelUtil.LookAndFeel;

public class ManagerView extends FrameView {

  public ManagerView(Application application) {
    super(application);
    setComponent(new ManagePanel());
  }

  public static void setLooknFeel() {
    LookAndFeelUtil.installLF(LookAndFeel.SYNTHETICA_SKY_METALLIC);
    LookAndFeelUtil.setLF(LookAndFeel.SYNTHETICA_SKY_METALLIC);
    UIManager.put("TabbedPane.background", UIManager.get("Panel.background"));
    UIManager.put("Synthetica.tabbedPane.keepOpacity", true);
    UIManager.put("Synthetica.dialog.icon.enabled", true);
  }
}
