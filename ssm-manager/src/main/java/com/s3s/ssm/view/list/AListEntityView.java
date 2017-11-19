/*
 * AListEntityView1
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

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.s3s.ssm.dto.IIdObject;
import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.ISavedListener;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.ITitleView;
import com.s3s.ssm.view.event.SavedEvent;

public abstract class AListEntityView<T extends IIdObject> extends AListView<T> implements
        ISavedListener<T> {
  private static final long serialVersionUID = -1311942671249671111L;
  private static final Log logger = LogFactory.getLog(AListEntityView.class);

  protected JButton btnNew;
  protected JButton btnDelete;
  protected JButton btnEdit;
  protected JButton btnRefresh;

  private JToolBar actionToolbar;

  public AListEntityView() {
    super();
  }

  public AListEntityView(Icon icon, String label) {
    super(icon, label);
  }

  public AListEntityView(Map<String, Object> request) {
    super(request);
  }

  public AListEntityView(Map<String, Object> request, Icon icon, String label) {
    super(request, icon, label);
  }

  /**
   * List fields need to show on the view.
   * 
   * @param listDataModel
   * @param summaryFieldNames
   *          the fields want to show sum values in footer. They must be Number type.
   */
  @Override
  protected abstract void initialPresentationView(ListDataModel listDataModel);

  @Override
  protected void addButtonPanel() {
    actionToolbar = createButtonToolBar();
    tablePane.add(actionToolbar, "grow x, split 2");
    super.addButtonPanel();
  }

  @Override
  protected void addListenerForDoubleClickRow() {
    tblListEntities.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent event) {
        Point point = event.getPoint();
        int currentRow = tblListEntities.rowAtPoint(point);
        if (currentRow != -1) {
          if (event.getClickCount() == 2) {
            performEditAction();
          }
        }
      }

    });
  }

  @Override
  protected abstract List<T> loadData(int page, int size);

  @Override
  protected int getTotalPages() {
    int totalRow = getTotalRow();
    int totalPages = (totalRow % getDefaultPageSize() == 0) ? (totalRow / getDefaultPageSize())
            : (totalRow / getDefaultPageSize() + 1);
    return (totalPages == 0) ? 1 : totalPages;
  }

  /**
   * Get number of all of rows existing in database.
   */
  protected abstract int getTotalRow();

  protected abstract void inactivateRows();

  protected abstract void deleteEntity(long[] deletedIds);

  protected void beforeDeleteRow(List<T> removedEntities) {
    // template method
  }

  protected void afterDeleteRows(List<T> removedEntities) {
  }

  protected void showEditView(T entity, EditActionEnum action) {
    Map<String, Object> detailParams = new HashMap<>();
    detailParams.put(PARAM_ENTITY_ID, entity != null ? entity.getId() : null);
    detailParams.put(PARAM_ACTION, action);
    detailParams.put(PARAM_LIST_VIEW, this);

    if (!preShowEditView(entity, action, detailParams)) {
      return;
    }

    // if existing a new tab --> select it.
    if (action == EditActionEnum.NEW) {
      int idx = tabbedPane.indexOfTab(getMessage("label.tab.new"));
      if (idx != -1) {
        tabbedPane.setSelectedIndex(idx);
        return;
      }
    }
    Class<? extends AbstractEditView<T>> detailViewClass = getEditViewClass();
    try {
      final AbstractEditView<T> detailView = detailViewClass.getConstructor(Map.class).newInstance(
              detailParams);
      String tabTitle;
      int tabIndex;
      if (action == EditActionEnum.NEW) {
        tabTitle = getMessage("label.tab.new");
        tabbedPane.addTab(tabTitle, detailView);
        tabIndex = tabbedPane.getTabCount() - 1;
      } else {
        tabTitle = detailView.getTitle();
        tabIndex = tabbedPane.indexOfTab(tabTitle);
        if (tabIndex == -1) {
          tabbedPane.addTab(tabTitle, detailView);
          tabIndex = tabbedPane.getTabCount() - 1;
        }
      }
      tabbedPane.setSelectedIndex(tabIndex);
      detailView.requestFocusInWindow();
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      logger.error("Error in AListEntity when show edit view", e);
    }
  }

  protected boolean preShowEditView(T entity, EditActionEnum action, Map<String, Object> detailParams) {
    return true;
  }

  protected T initEntity(T entity) {
    // Template method
    return entity;
  }

  protected Class<? extends AbstractEditView<T>> getEditViewClass() {
    return null;
  }

  public void notifyFromDetailView(String title, final T entity, final boolean isNew) {
    replaceEntity(entity);
    if (isNew) {
      entities.add(0, entity);
      int tabIndex = tabbedPane.indexOfTab(ControlConfigUtils.getString("label.tab.new"));

      // TODO Phuc: This is a bug, set the title of tab is by getDefaultTitle() from editView
      tabbedPane.setTitleAt(tabIndex, title);
      mainTableModel.fireTableRowsInserted(entities.size() - 1, entities.size() - 1);
    } else {
      int index = entities.indexOf(entity);
      mainTableModel.fireTableRowsUpdated(index, index);
    }
  }

  /**
   * Replace the element in entities having ID equal the entity parameter.
   * 
   * @param entity
   */
  private void replaceEntity(T entity) {
    for (int i = 0; i < entities.size(); i++) {
      if (entities.get(i).getId().equals(entity.getId())) {
        entities.set(i, entity);
      }
    }
  }

  public void performNewAction() {
    showEditView(null, EditActionEnum.NEW);
  }

  @Override
  protected void initMainTable() {
    super.initMainTable();
    tblListEntities.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent event) {
        Point point = event.getPoint();
        int currentRow = tblListEntities.rowAtPoint(point);
        if (currentRow != -1) {
          tblListEntities.setRowSelectionInterval(currentRow, currentRow);
          btnDelete.setEnabled(true);
          btnEdit.setEnabled(true);
        } else {
          tblListEntities.getSelectionModel().clearSelection();
          btnDelete.setEnabled(false);
          btnEdit.setEnabled(false);
        }
      }

    });

  }

  protected JToolBar createButtonToolBar() {
    btnNew = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.NEW_ICON));
    btnNew.setName("btnNew");
    btnNew.setText(getMessage("default.button.create"));
    btnNew.addActionListener(e -> {
      performNewAction();
    });

    btnDelete = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.DELETE_ICON));
    btnDelete.setName("btnEntityDelete");
    btnDelete.setText(getMessage("default.button.delete"));
    btnDelete.setEnabled(false);
    btnDelete.addActionListener(e -> {
      inactivateRows();
      refreshAndBackToFirstPage();
    });

    btnEdit = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.EDIT_ICON));
    btnEdit.setName("btnEntityEdit");
    btnEdit.setText(getMessage("default.button.edit"));
    btnEdit.setEnabled(false);
    btnEdit.addActionListener(e -> {
      performEditAction();
    });

    btnRefresh = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.REFRESH_ICON));
    btnRefresh.setText(getMessage("default.button.refresh"));
    btnRefresh.setName("btnRefresh");
    btnRefresh.addActionListener(e -> {
      getPagingNavigator().setCurrentPage(1);
    });
    actionToolbar = new JToolBar();
    actionToolbar.add(btnNew);
    actionToolbar.add(Box.createHorizontalStrut(5));
    actionToolbar.add(btnEdit);
    actionToolbar.add(Box.createHorizontalStrut(5));
    actionToolbar.add(btnDelete);
    actionToolbar.add(Box.createHorizontalStrut(5));
    actionToolbar.add(btnRefresh);
    actionToolbar.add(Box.createHorizontalStrut(5));
    return actionToolbar;
  }

  @Override
  public void doPageChanged(ChangeEvent e) {
    super.doPageChanged(e);
    btnEdit.setEnabled(false);
    btnDelete.setEnabled(false);
  }

  protected void performEditAction() {
    int selectedRow = tblListEntities.getSelectedRow();
    if (selectedRow == -1) {
      JOptionPane.showConfirmDialog(SwingUtilities.getRoot(this), "Please select a row to edit", "Warning",
              JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
    } else {
      int rowModel = tblListEntities.convertRowIndexToModel(selectedRow);
      showEditView(entities.get(rowModel), EditActionEnum.EDIT);
    }
  }

  protected abstract void saveOrUpdate(T entity);

  /**
   * This function is intended to replace for {@link #notifyFromDetailView(AbstractIdOLObject, boolean)}.
   * {@inheritDoc}
   */
  @Override
  public void doSaved(SavedEvent<T> e) {
    String title = "";
    if (e.getSource() instanceof ITitleView) {
      title = ((ITitleView) e.getSource()).getTitle();
    }
    // TODO Phuc: consider add abstractListView as a listerner of EditView to make a clear code.
    notifyFromDetailView(title, e.getEntity(), e.isNew());
  }
}
