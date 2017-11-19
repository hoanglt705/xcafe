package com.s3s.ssm.view.factory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.StringValue;

import com.jidesoft.converter.DateConverter;
import com.s3s.ssm.renderer.BooleanRenderer;
import com.s3s.ssm.renderer.DateCellRenderer;
import com.s3s.ssm.renderer.ImageCellRenderer;
import com.s3s.ssm.util.WidgetConstant;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public final class SCellRendererFactory {

  private SCellRendererFactory() {
  }

  public static TableCellRenderer createCellRenderer(ListRendererType listRendererType) {
    switch (listRendererType) {
      case IMAGE:
        return new ImageCellRenderer();
      case HOUR_MIN_SECOND:
        StringValue stringValue = new StringValue() {
          private static final long serialVersionUID = 1L;

          @Override
          public String getString(Object value) {
            DateConverter converter = new DateConverter();
            converter.setDefaultTimeFormat(new SimpleDateFormat(WidgetConstant.HH_MM_SS_FORMAT));
            return converter.toString(value, DateConverter.TIME_CONTEXT);
          }
        };
        return new DefaultTableRenderer(stringValue);
      case HOUR_MIN:
        StringValue stringValue1 = new StringValue() {
          private static final long serialVersionUID = 1L;

          @Override
          public String getString(Object value) {

            DateConverter converter = new DateConverter();
            converter.setDefaultTimeFormat(new SimpleDateFormat(WidgetConstant.HH_MM_FORMAT));
            return converter.toString(value, DateConverter.TIME_CONTEXT);
          }
        };
        return new DefaultTableRenderer(stringValue1, SwingConstants.RIGHT);
      case DATE:
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        return new DateCellRenderer(formatter);
      case BOOLEAN:
        return new BooleanRenderer();
      default:
        break;
    }
    return null;
  }
}
