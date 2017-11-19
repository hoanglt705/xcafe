/*
 * ListDataModel
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

package com.s3s.ssm.view.list;

import java.util.ArrayList;
import java.util.List;

/**
 * @since Mar 19, 2012
 */
public class ListDataModel {
  public enum ListRendererType {
    TEXT, BOOLEAN, NUMBER, DATE, LINK, IMAGE, HOUR_MIN, HOUR_MIN_SECOND
  }

  public enum ListEditorType {
    TEXTFIELD, CHECKBOX, COMBOBOX, DATE_PICKER, POSITIVE_NUMBER_SPINNER, LONG
  }

  private List<ColumnModel> columns = new ArrayList<>();
  private boolean isEditable = false;

  public ColumnModel addColumn(String property, ListRendererType rendererType) {
    ColumnModel columnModel = new ColumnModel(property, rendererType);
    columns.add(columnModel);
    return columnModel;
  }

  public ColumnModel addColumn(String name, ListRendererType rendererType, ListEditorType editorType) {
    ColumnModel columnModel = new ColumnModel(name, rendererType, editorType);
    columns.add(columnModel);
    return columnModel;
  }

  public List<ColumnModel> getColumns() {
    return columns;
  }

  public ColumnModel getColumn(int index) {
    return columns.get(index);
  }

  public ColumnModel getColumn(String property) {
    for (ColumnModel model : columns) {
      if (model.getName().equals(property)) {
        return model;
      }
    }
    return null;
  }

  public boolean isEditable() {
    return isEditable;
  }

  /**
   * Set the entire table is editable or not.
   * 
   * @param isEditable
   */
  public void setEditable(boolean isEditable) {
    this.isEditable = isEditable;
  }

  public int getWidth() {
    int width = 0;
    for (ColumnModel model : columns) {
      width += model.getWidth();
    }
    return width;
  }
}
