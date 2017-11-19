/*
 * SearchCellEditor
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

package com.s3s.ssm.view.list.editor;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.view.component.ASearchComponent;

public class SearchCellEditor<T extends AbstractBaseIdObject> extends AbstractCellEditor implements
        TableCellEditor {
  private static final long serialVersionUID = 9212064914606975148L;
  private ASearchComponent<T> searchComponent;

  public SearchCellEditor(ASearchComponent<T> searchComponent) {
    this.searchComponent = searchComponent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getCellEditorValue() {
    return searchComponent.getSelectedEntity();
  }

  @Override
  public boolean stopCellEditing() {
    return super.stopCellEditing();
  }

  @SuppressWarnings({"unused", "unchecked"})
  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
          int column) {
    searchComponent.setSelectedEntity((T) value);
    return searchComponent;
  }
}
