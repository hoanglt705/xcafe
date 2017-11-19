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

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.plaf.UIResource;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.renderer.JRendererLabel;

import com.s3s.ssm.util.UIConstants;

@SuppressWarnings("unchecked")
public class FooterRenderer extends JRendererLabel implements TableCellRenderer, UIResource {
  private static final long serialVersionUID = -6965018746139464733L;

  @SuppressWarnings("unused")
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
          boolean hasFocus, int row, int column) {
    if (value != null) {
      setText(value.toString());
      setHorizontalAlignment(SwingConstants.RIGHT);
      setFont(UIConstants.DEFAULT_BOLD_FONT);
      setBorder(BorderFactory.createEtchedBorder());
    } else {
      setText(null);
      setBorder(BorderFactory.createEmptyBorder());
    }
    setOpaque(true);
    return this;
  }
}