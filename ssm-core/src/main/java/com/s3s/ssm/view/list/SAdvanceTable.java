/*
 * SAdvanceTable
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

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.commons.lang.ClassUtils;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import com.s3s.ssm.util.UIConstants;
import com.s3s.ssm.view.factory.SCellEditorFactory;
import com.s3s.ssm.view.factory.SCellRendererFactory;

import de.javasoft.swing.JYTable;

public class SAdvanceTable extends JYTable {
  private static final long serialVersionUID = 6141561051329763125L;
  private final ListDataModel listDataModel;
  private final AdvanceTableModel<?> tableModel;

  public SAdvanceTable(AdvanceTableModel<?> tableModel, ListDataModel listDataModel) {
    super(tableModel);
    this.tableModel = tableModel;
    this.listDataModel = listDataModel;
    setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    int selectionMode = tableModel.isEditable() ? ListSelectionModel.SINGLE_SELECTION
            : ListSelectionModel.SINGLE_INTERVAL_SELECTION;
    setSelectionMode(selectionMode);
    getTableHeader().setFont(UIConstants.DEFAULT_BOLD_FONT);
    setRowHeight(UIConstants.ROW_HEIGHT);
    setShowHorizontalLines(true);
    setShowVerticalLines(true);
    if (tableModel.isEditable()) {
      setSorter();
      setShowGrid(true);
    } else {
      addHighlighter(HighlighterFactory.createSimpleStriping());
    }
    setRenderer();
    setHeaderRenderer();
    setEditor();
    setColumnWidth();
    setColumnControlVisible(true);
  }

  @Override
  public void packAll() {
    super.packAll();
    setColumnWidth();
  }

  private void setColumnWidth() {
    for (int i = 0; i < listDataModel.getColumns().size(); i++) {
      TableColumn tableColumn = getColumnModel().getColumn(i);
      ColumnModel columnModel = listDataModel.getColumn(i);
      if (columnModel.getWidth() != null) {
        tableColumn.setPreferredWidth(columnModel.getWidth());
        tableColumn.setWidth(columnModel.getWidth());
      }
      if (columnModel.getMaxWidth() != null) {
        tableColumn.setMaxWidth(columnModel.getMaxWidth());
      }
      if (columnModel.getMinWidth() != null) {
        tableColumn.setMinWidth(columnModel.getMinWidth());
      }
    }
  }

  /**
   * @param ldm
   * @param mainTable
   */
  private void setSorter() {
    TableRowSorter<TableModel> sorter = new TableRowSorter<>(getModel());
    // Swing API: The precedence of the columns in the sort is indicated by
    // the order of the sort keys in the sort
    // key list.
    List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
    List<ColumnModel> columnModels = listDataModel.getColumns();
    for (int i = 0; i < columnModels.size(); i++) {
      for (int j = 0; j < columnModels.size(); j++) {
        ColumnModel cm = columnModels.get(j);
        if (cm.isSorted() && (cm.getPrecedence() == i)) {
          sortKeys.add(new RowSorter.SortKey(j, cm.getSortOrder()));
        }
      }
    }
    sorter.setSortKeys(sortKeys);
    setRowSorter(sorter);
  }

  private void setRenderer() {
    for (int i = 0; i < listDataModel.getColumns().size(); i++) {
      final ColumnModel columnModel = listDataModel.getColumns().get(i);
      TableColumn column = getColumnModel().getColumn(i);
      column.setCellRenderer(SCellRendererFactory.createCellRenderer(columnModel.getRendererType()));
    }
  }

  private void setHeaderRenderer() {
    for (int i = 0; i < getColumnCount(); i++) {
      TableColumn tc = getColumns().get(i);
      tc.setHeaderRenderer(new STableHeaderRenderer(tableModel.getColumnClass(i)));
    }
  }

  private void setEditor() {
    for (int i = 0; i < getColumnCount(); i++) {
      setEditorForEachColumn(i);
    }
  }

  private void setEditorForEachColumn(int column) {
    ColumnModel columnModel = listDataModel.getColumns().get(column);
    TableCellEditor editor = SCellEditorFactory.createCellEditor(this, columnModel.getEditorType(), null,
            getValues(columnModel), columnModel.getName());
    updateEditor(editor, column);
  }

  public void updateEditor(TableCellEditor editor, int column) {
    getColumnModel().getColumn(column).setCellEditor(editor);
  }

  private Object[] getValues(ColumnModel columnModel) {
    return columnModel.getDataList() != null ? columnModel.getDataList().toArray() : null;
  }

  @Override
  public void changeSelection(int row, int column, boolean toggle, boolean extend) {
    int pColumn = column;
    int pRow = row;
    if (!listDataModel.getColumn(pColumn).isEditable()) {
      if (pColumn == listDataModel.getColumns().size() - 1) {
        pColumn = 0;
        if (pRow == getRowCount() - 1) {
          pRow = 0;
        }
      } else {
        pColumn++;
      }

    }
    super.changeSelection(pRow, pColumn, toggle, extend);
    // Place cell in edit mode when it 'gains focus'
    if (tableModel.isEditable() && listDataModel.getColumns().get(pColumn).isEditable()
            && editCellAt(pRow, pColumn)) {
      getEditorComponent().requestFocusInWindow();
    }
  }

  private class STableHeaderRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1L;
    private final Class<?> columnClass;

    public STableHeaderRenderer(Class<?> columnClass) {
      super();
      this.columnClass = columnClass;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
      TableCellRenderer defaultRenderer = getTableHeader().getDefaultRenderer();
      JLabel label = (JLabel) defaultRenderer.getTableCellRendererComponent(table, value, isSelected,
              hasFocus, row, column);
      setAligment(label);
      return label;
    }

    private void setAligment(JLabel label) {
      if (ClassUtils.isAssignable(columnClass, Boolean.class)
              || ClassUtils.isAssignable(columnClass, boolean.class)) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
      } else if (ClassUtils.isAssignable(columnClass, String.class)) {
        label.setHorizontalAlignment(SwingConstants.LEFT);
      } else if (ClassUtils.isAssignable(columnClass, Number.class)) {
        label.setHorizontalAlignment(SwingConstants.RIGHT);
      } else {
        label.setHorizontalAlignment(SwingConstants.LEFT);
      }
    }
  }
}
