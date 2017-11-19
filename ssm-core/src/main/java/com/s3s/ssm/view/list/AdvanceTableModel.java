/*
 * AdvanceTableModel
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

import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang.ObjectUtils;

import com.s3s.ssm.util.IziClassUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

public class AdvanceTableModel<T> extends AbstractTableModel {
  private static final long serialVersionUID = -4720974982417224609L;

  private final ListDataModel listDataModel;
  private final List<T> entities;
  private final boolean isEditable;
  private final Class<T> clazz;
  private final int visibleRowCount;
  protected ICallbackAdvanceTableModel<T> iCallback;

  public AdvanceTableModel(ListDataModel listDataModel, List<T> entities, Class<T> clazz, boolean isEditable,
          int visibleRowCount, ICallbackAdvanceTableModel<T> iCallbackAdvanceTableModel) {
    this.listDataModel = listDataModel;
    this.entities = entities;
    this.isEditable = isEditable;
    this.clazz = clazz;
    this.visibleRowCount = visibleRowCount;
    iCallback = iCallbackAdvanceTableModel;
  }

  @Override
  public int getRowCount() {
    if (isEditable) {
      return entities.size() < visibleRowCount ? visibleRowCount : entities.size();
    }
    return entities.size();

  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    if (rowIndex >= entities.size()) {
      return null;
    }
    T entity = entities.get(rowIndex);

    ColumnModel dataModel = listDataModel.getColumns().get(columnIndex);
    return iCallback.getAttributeValue(entity, dataModel);
  }

  @Override
  public int getColumnCount() {
    return listDataModel.getColumns().size();
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return isEditable && listDataModel.getColumns().get(columnIndex).isEditable()
            && rowIndex < entities.size();
  }

  @Override
  public String getColumnName(int column) {
    return ControlConfigUtils.getString("label." + clazz.getSimpleName() + "."
            + listDataModel.getColumns().get(column).getName());
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    if (rowIndex >= entities.size()) {
      return;
    }

    T entity = entities.get(rowIndex);

    ColumnModel dataModel = listDataModel.getColumns().get(columnIndex);

    Object value = iCallback.getAttributeValue(entity, dataModel);
    if (!ObjectUtils.equals(value, aValue)) {
      iCallback.setAttributeValue(entity, dataModel, aValue);
      fireTableCellUpdated(rowIndex, columnIndex);
    }

  }

  public void addRowAt(int index, T entity) {
    entities.add(index, entity);
    fireTableRowsInserted(index, index);
  }

  public void setData(Collection<T> entities) {
    this.entities.clear();
    this.entities.addAll(entities);
    fireTableDataChanged();
  }

  public List<T> getData() {
    return entities;
  }

  /**
   * Delete a range of rows.
   * 
   * @param firstRow
   *          first index of row, inclusive
   * @param lastRow
   *          last index of row, inclusive
   */
  public void deleteRows(int[] deletedIndex) {
    for (int i = 0; i < deletedIndex.length; i++) {
      entities.remove(i);
    }
    fireTableRowsDeleted(deletedIndex[0], deletedIndex[deletedIndex.length - 1]);
  }

  public T getEntity(int index) {
    return entities.get(index);
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    if (!listDataModel.getColumns().get(columnIndex).isRaw()) {
      return IziClassUtils.getClassOfField(listDataModel.getColumns().get(columnIndex).getName(), clazz);
    }
    return null;
  }

  public boolean isEditable() {
    return isEditable;
  }

}
