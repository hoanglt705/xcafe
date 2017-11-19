package com.s3s.ssm.renderer;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.plaf.UIResource;

public class NullListCellRenderer extends DefaultListCellRenderer implements UIResource {
  private static final long serialVersionUID = 1L;

  @Override
  public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
          boolean isSelected, boolean cellHasFocus) {
    JLabel component = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
            cellHasFocus);
    if (value == null) {
      component.setText(" ");
    }
    return component;
  }

}
