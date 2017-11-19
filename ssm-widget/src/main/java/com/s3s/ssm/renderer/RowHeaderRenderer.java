/*
 * RowHeaderRenderer
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
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.plaf.UIResource;
import javax.swing.table.JTableHeader;

import org.jdesktop.swingx.renderer.JRendererLabel;

@SuppressWarnings("unchecked")
public class RowHeaderRenderer extends JRendererLabel implements ListCellRenderer<Object>, UIResource {
  private static final long serialVersionUID = -1537178297118197956L;

  public RowHeaderRenderer(JTable table) {
    JTableHeader header = table.getTableHeader();
    // setOpaque(true);
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    setHorizontalAlignment(CENTER);
    setForeground(header.getForeground());
    setBackground(header.getBackground());
    setFont(header.getFont());
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unused")
  @Override
  public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
          boolean isSelected, boolean cellHasFocus) {
    setText((value == null) ? "" : value.toString());
    setBorder(BorderFactory.createRaisedSoftBevelBorder());
    setOpaque(true);
    return this;
  }
}
