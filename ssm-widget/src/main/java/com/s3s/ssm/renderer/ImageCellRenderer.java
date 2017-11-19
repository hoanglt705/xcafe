/*
 * FooterRenderer
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

package com.s3s.ssm.renderer;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.plaf.UIResource;
import javax.swing.table.DefaultTableCellRenderer;

import com.s3s.ssm.util.IziImageUtils;

public class ImageCellRenderer extends DefaultTableCellRenderer implements UIResource {
  private static final long serialVersionUID = -6965018746139464733L;

  @SuppressWarnings("unused")
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
          boolean hasFocus, int row, int column) {
    int size = table.getRowHeight();
    if (value != null) {
      ImageIcon icon = IziImageUtils.getIcon((byte[]) value, size);
      setIcon(icon);
      setHorizontalAlignment(SwingConstants.CENTER);
    } else {
      setIcon(null);
    }
    if (isSelected) {
      setBackground(table.getSelectionBackground());
    } else {
      setBackground(null);
    }
    return this;
  }
}
