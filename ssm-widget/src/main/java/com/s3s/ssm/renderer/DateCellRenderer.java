package com.s3s.ssm.renderer;

import java.awt.Component;
import java.text.DateFormat;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.plaf.UIResource;
import javax.swing.table.DefaultTableCellRenderer;

public class DateCellRenderer extends DefaultTableCellRenderer implements UIResource {
  private static final long serialVersionUID = 1L;
  private DateFormat formatter;

  public DateCellRenderer() {
    this(DateFormat.getDateTimeInstance());
  }

  public DateCellRenderer(DateFormat formatter) {
    this.formatter = formatter;
    setHorizontalAlignment(SwingConstants.LEFT);
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
          boolean hasFocus, int row, int column) {
    JLabel renderer = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
            column);
    if (value != null) {
      renderer.setText(this.formatter.format(value));
    }
    else {
      renderer.setText("");
    }
    if (isSelected) {
      renderer.setBackground(table.getSelectionBackground());
    } else {
      renderer.setBackground(null);
    }
    return renderer;
  }
}