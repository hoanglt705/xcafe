/*
 * AbstractListComponent
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
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.divxdede.swing.busy.DefaultBusyModel;
import org.divxdede.swing.busy.JBusyComponent;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.s3s.ssm.interfaces.IDirtiableObject;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.renderer.RowHeaderRenderer;
import com.s3s.ssm.util.IziClassUtils;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.UIConstants;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * The list component, turn off the break page function.
 * 
 * @since Apr 21, 2012
 */
public abstract class AListComponent<T> extends JPanel implements TableModelListener,
        ICallbackAdvanceTableModel<T>, IDirtiableObject {
  private static final long serialVersionUID = -1311942671249671111L;
  private static final int NUM_ROW_VISIBLE = 10;
  private static final Log LOGGER = LogFactory.getLog(AListComponent.class);

  protected SAdvanceTable mainTable;
  private JList<String> rowHeader;
  private JBusyComponent<JScrollPane> busyPane;

  private final Action insertAction;
  private final Action deleteAction;

  private boolean isInsertRowAllowed = true;
  private boolean isDeleteRowAllowed = true;

  private JButton insertBtn;
  private JButton deleteBtn;

  private AdvanceTableModel<T> mainTableModel;
  private final List<ChangeListener> listeners = new ArrayList<>();
  private boolean isDirty;

  // This model is used by sub classes.
  protected ListDataModel listDataModel = new ListDataModel();
  private Component actionButtonToolbar;

  public AListComponent() {
    initialPresentationView(listDataModel);
    this.setLayout(new MigLayout("ins 0, wrap", "grow, fill", "[]0[]0[]0[]"));
    insertAction = new InsertRowAction();
    deleteAction = new DeleteRowAction();

    addComponents();
    addKeyBindings();
  }

  @Override
  public Object getAttributeValue(T entity, ColumnModel dataModel) {
    BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
    return dataModel.isRaw() ? dataModel.getValue() : beanWrapper.getPropertyValue(dataModel.getName());
  }

  @Override
  public void setAttributeValue(T entity, ColumnModel dataModel, Object aValue) {
    // do not bind the property if it's raw. The sub class must bind this property manual
    if (!dataModel.isRaw()) {
      BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
      try {
        beanWrapper.setPropertyValue(dataModel.getName(), aValue);
      } catch (Exception ex) {
        LOGGER.error(ex.getStackTrace());
      }
    } else {
      dataModel.value(aValue);
    }
  }

  @Override
  public void fireChangeEvent() {
    ChangeEvent e = new ChangeEvent(this);
    for (ChangeListener listener : listeners) {
      listener.stateChanged(e);
    }
  }

  public List<T> getEntities() {
    return mainTableModel.getData();
  }

  protected void addKeyBindings() {
    InputMap inputMap = mainTable.getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    KeyStroke addShortkey = KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK);
    KeyStroke deleteShortkey = KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK);

    inputMap.put(addShortkey, "insKeyAction");
    inputMap.put(deleteShortkey, "delKeyAction");

    ActionMap actionMap = mainTable.getActionMap();
    actionMap.put("insKeyAction", insertAction);
    actionMap.put("delKeyAction", deleteAction);
  }

  protected void addRowAt(int index, T entity) {
    mainTableModel.addRowAt(index, entity);
  }

  protected void replaceCellEditor(TableCellEditor editor, int column) {
    mainTable.updateEditor(editor, column);
  }

  /**
   * Return the number of rows visibled. The default return value is {@link #getDefaultPageSize()}. It should
   * be
   * overrided.
   * 
   * @return a number of visible rows.
   */
  protected int getVisibleRowCount() {
    return NUM_ROW_VISIBLE;
  }

  @Override
  public boolean requestFocusInWindow() {
    return mainTable.requestFocusInWindow();
  }

  protected abstract void initialPresentationView(ListDataModel listDataModel);

  /**
   * The subclass should override this method to set data model for the editor component when the cell in edit
   * mode.
   * 
   * @param rdm
   */
  protected ReferenceDataModel initReferenceDataModel() {
    return new ReferenceDataModel();
  }

  public void setVisibleToolbar(boolean visible) {
    actionButtonToolbar.setVisible(visible);
  }

  @SuppressWarnings("unchecked")
  protected void addComponents() {
    actionButtonToolbar = createButtonsToolbar();
    add(actionButtonToolbar);
    mainTableModel = createMainTableModel();
    mainTable = initMainTable();
    rowHeader = initRowHeader();

    JScrollPane mainScrollpane = new JScrollPane(mainTable);
    mainScrollpane.setRowHeaderView(rowHeader);
    busyPane = createBusyPane(mainScrollpane);
    add(busyPane);
    mainScrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

    JTableHeader th = new JTableHeader();
    th.setTable(mainTable);
    mainScrollpane.setCorner(ScrollPaneConstants.UPPER_LEADING_CORNER, th);

  }

  private JList<String> initRowHeader() {
    JList<String> rowHeader = new JList<String>(new AbstractListModel<String>() {
      private static final long serialVersionUID = -771503812711976068L;

      @Override
      public int getSize() {
        return mainTableModel.getRowCount();
      }

      @Override
      public String getElementAt(int index) {
        int numOfEntities = getEntities().size();
        if (index >= numOfEntities) {
          return "";
        }
        return String.valueOf(index + 1);
      }
    });

    rowHeader.setCellRenderer(new RowHeaderRenderer(mainTable));
    rowHeader.setFixedCellWidth(UIConstants.DEFAULT_ROW_HEADER_WIDTH);
    rowHeader.setFixedCellHeight(mainTable.getRowHeight());
    return rowHeader;
  }

  @SuppressWarnings("rawtypes")
  private AdvanceTableModel createMainTableModel() {
    AdvanceTableModel mainTableModel = new AdvanceTableModel<T>(listDataModel, new ArrayList<T>(),
            getEntityClass(), true, getVisibleRowCount(), this);
    mainTableModel.addTableModelListener(this);
    return mainTableModel;
  }

  private SAdvanceTable initMainTable() {
    SAdvanceTable mainTable = new SAdvanceTable(mainTableModel, listDataModel);
    mainTable.setName("AListComponent");
    mainTable.setVisibleRowCount(getVisibleRowCount());
    mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    return mainTable;
  }

  /**
   * @return
   */
  protected JPanel createButtonsToolbar() {
    JPanel tb = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    initInsertBtn();
    initDeleteBtn();
    tb.add(insertBtn);
    tb.add(deleteBtn);
    return tb;
  }

  private void initDeleteBtn() {
    deleteBtn = new JButton(deleteAction);
    deleteBtn.setName("deleteBtn");
    deleteBtn.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.DELETE_ICON));
    deleteBtn.setToolTipText(ControlConfigUtils.getString("AListComponent.delRow") + " (Ctrl+D)");
  }

  private void initInsertBtn() {
    insertBtn = new JButton(insertAction);
    insertBtn.setName("insertBtn");
    insertBtn.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.NEW_ICON));
    insertBtn.setToolTipText(ControlConfigUtils.getString("AListComponent.insertRow") + " (Ctrl+I)");
  }

  @Override
  public void tableChanged(TableModelEvent e) {
    List<T> entities = getEntities();
    if (e.getType() == TableModelEvent.UPDATE) {
      if (e.getColumn() == TableModelEvent.ALL_COLUMNS) {
        return;
      }
      String attributeName = listDataModel.getColumn(e.getColumn()).getName();
      doRowUpdated(attributeName, entities.get(e.getFirstRow()));
    } else if (e.getType() == TableModelEvent.INSERT) {
      doRowInsert(entities.get(e.getFirstRow()), entities);
    }
    fireStateChange();
    mainTable.repaint();
    mainTable.revalidate();
    rowHeader.repaint();
    rowHeader.revalidate();
  }

  /**
   * Perform after a row is inserted.
   * 
   * @param rowIndex
   * @param entities
   */
  @SuppressWarnings("unused")
  protected void doRowInsert(T entityInserted, List<T> entities) {
    // Template method
  }

  /**
   * Perform after a row is updated.
   * 
   * @param attributeName
   *          the name of
   * @param entityUpdated
   * @param value
   * @param entities
   */
  protected void doRowUpdated(String attributeName, T entityUpdated) {
    // Template method
  }

  /**
   * Perform delete rows.
   * 
   * @param deletedIndex
   *          the index of rows to delete
   */
  protected void doRowDelete(int[] deletedIndex) {
    mainTableModel.deleteRows(deletedIndex);
  }

  protected void deleteAllRow() {
    // TODO: customize
    while (mainTableModel.getColumnCount() > 0) {
      int[] temp = new int[1];
      temp[0] = 0;
      mainTableModel.deleteRows(temp);
    }
  }

  public void setInsertRowAllowed(boolean allowed) {
    isInsertRowAllowed = allowed;
    insertBtn.setVisible(allowed);
  }

  public void setDeleteRowAllowed(boolean allowed) {
    isDeleteRowAllowed = allowed;
    deleteBtn.setVisible(allowed);
  }

  /**
   * Override this method to add the footer panel to the list view.
   * 
   * @return
   */
  @SuppressWarnings("unused")
  protected JPanel createFooterPanel(TableModel tableModel) {
    return new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Just create a panel with no size
  }

  private JBusyComponent<JScrollPane> createBusyPane(JScrollPane mainScrollpane) {
    JBusyComponent<JScrollPane> bp = new JBusyComponent<JScrollPane>(mainScrollpane);
    ((DefaultBusyModel) bp.getBusyModel()).setDescription(ControlConfigUtils.getString("label.loading"));
    return bp;
  }

  /**
   * Override this function to add some other attribute to the new entity. When new a row, a new entity is
   * created and
   * initialize some field by this function.
   * 
   * @category template method
   */
  protected T createNewEntity() {
    // Template method
    try {
      return getEntityClass().newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException("Fail to initialize new entity!");
    }
  }

  /**
   * Add the listener for the main table data changed.
   * 
   * @param tableModelListener
   */
  public void addTableModelListener(TableModelListener tableModelListener) {
    mainTableModel.addTableModelListener(tableModelListener);
  }

  private void fireStateChange() {
    ChangeEvent e = new ChangeEvent(this);
    for (ChangeListener listener : listeners) {
      listener.stateChanged(e);
    }
  }

  @Override
  public void addChangeListener(ChangeListener listener) {
    listeners.add(listener);
  }

  @SuppressWarnings("unchecked")
  protected Class<T> getEntityClass() {
    return (Class<T>) IziClassUtils.getArgumentClass(getClass());
  }

  @Override
  public boolean isDirty() {
    return isDirty;
  }

  public void setEditable(boolean editable) {
    mainTable.setEditable(editable);
    setInsertRowAllowed(editable);
    setDeleteRowAllowed(editable);
  }

  public List<T> getData() {
    return getEntities();
  }

  public void setData(Collection<T> data) {
    mainTableModel.setData(data);
    packAll();
  }

  public void packAll() {
    mainTable.packAll();
  }

  public AdvanceTableModel<T> getMainTableModel() {
    return mainTableModel;
  }

  private class InsertRowAction extends AbstractAction {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Override
    public void actionPerformed(ActionEvent e) {
      if (isInsertRowAllowed) {
        isDirty = true;
        int lastRow = getEntities().size();
        addRowAt(lastRow, createNewEntity());
        mainTable.setRowSelectionInterval(lastRow, lastRow);
      }
    }

  }

  private class DeleteRowAction extends AbstractAction {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Override
    public void actionPerformed(ActionEvent e) {
      if (isDeleteRowAllowed) {
        isDirty = true;
        int option = JOptionPane.showConfirmDialog(SwingUtilities.getRoot(AListComponent.this),
                "Are you sure want to delete the selected row?", "Confirm delete",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
          int[] selectedRows = mainTable.getSelectedRows();
          int[] selectedModelRows = new int[selectedRows.length];

          for (int i = 0; i < selectedRows.length; i++) {
            int rowModelIndex = mainTable.convertRowIndexToModel(selectedRows[i]);
            selectedModelRows[i] = rowModelIndex;
          }
          doRowDelete(selectedModelRows);
        }
      }
    }
  }

  @Override
  public int getWidth() {
    return listDataModel.getWidth() + 41;
  }
}
