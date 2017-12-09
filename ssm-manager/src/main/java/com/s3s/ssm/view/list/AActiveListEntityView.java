/*
 * AbstractListView
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
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import com.s3s.ssm.dto.base.IActiveObject;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;

import de.javasoft.swing.SimpleDropDownButton;

public abstract class AActiveListEntityView<T extends IActiveObject> extends AListEntityView<T> {
  private static final long serialVersionUID = -7276967240967148821L;
  private JPopupMenu activePopupMenu;
  private JPopupMenu inactivePopupMenu;

  protected JRadioButtonMenuItem showDeletedMenuItem;
  protected JRadioButtonMenuItem showAllMenuItem;
  private SimpleDropDownButton btnShow;
  private JRadioButtonMenuItem showActiveMenuItem;

  public AActiveListEntityView() {
    super();
  }

  public AActiveListEntityView(Icon icon, String label) {
    super(icon, label);
  }

  public AActiveListEntityView(Map<String, Object> request) {
    super(request);
  }

  public AActiveListEntityView(Map<String, Object> request, Icon icon, String label) {
    super(request, icon, label);
  }

  @Override
  protected JToolBar createButtonToolBar() {
    JToolBar createButtonToolBar = super.createButtonToolBar();
    btnShow = new SimpleDropDownButton(getMessage("default.button.show"));
    btnShow.setName("btnShow");
    showActiveMenuItem = new JRadioButtonMenuItem(getMessage("default.button.showActive"));
    showActiveMenuItem.setSelected(true);

    showActiveMenuItem.addItemListener(e -> {
      getPagingNavigator().setCurrentPage(1);
      tblListEntities.setComponentPopupMenu(activePopupMenu);
    });

    showDeletedMenuItem = new JRadioButtonMenuItem(getMessage("default.button.showInactive"));
    showDeletedMenuItem.addItemListener(e -> {
      getPagingNavigator().setCurrentPage(1);
      tblListEntities.setComponentPopupMenu(inactivePopupMenu);
    });

    showAllMenuItem = new JRadioButtonMenuItem(getMessage("default.button.showAll"));
    showAllMenuItem.addItemListener(e -> {
      getPagingNavigator().setCurrentPage(1);
    });
    btnShow.getPopupMenu().add(showActiveMenuItem);
    btnShow.getPopupMenu().add(showDeletedMenuItem);
    btnShow.getPopupMenu().add(showAllMenuItem);

    ButtonGroup showButtonGroup = new ButtonGroup();
    showButtonGroup.add(showActiveMenuItem);
    showButtonGroup.add(showDeletedMenuItem);
    showButtonGroup.add(showAllMenuItem);
    createButtonToolBar.add(btnShow);
    return createButtonToolBar;
  }

  public enum ShowEnum {
    ACTIVE, INACTIVE, ALL
  }

  protected ShowEnum getShowEnum() {
    if (showActiveMenuItem.isSelected()) {
      return ShowEnum.ACTIVE;
    } else if (showDeletedMenuItem.isSelected()) {
      return ShowEnum.INACTIVE;
    }
    return ShowEnum.ALL;
  }

  protected void setVisibleShowButton(boolean visible) {
    btnShow.setVisible(visible);
  }

  private void initPopupMenu() {
    activePopupMenu = initActiveViewPopup();
    inactivePopupMenu = initInActiveViewPopup();
  }

  @Override
  protected void addComponents() {
    super.addComponents();
    initPopupMenu();
    tblListEntities.setComponentPopupMenu(activePopupMenu);
  }

  private JPopupMenu initInActiveViewPopup() {
    JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem menuItemInactive = new JMenuItem("Khoi phuc");
    menuItemInactive.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.ACTIVATE_ICON));
    menuItemInactive.addActionListener(e -> {
      int[] selectedColumns = tblListEntities.getSelectedRows();
      for (int i : selectedColumns) {
        T entity = mainTableModel.getEntity(i);
        if (!entity.isActive()) {
          activateEntity(entity);
          entity.setActive(true);
          afterActivateEntity(entity);
        }
      }
      refreshAndBackToFirstPage();
    });
    popupMenu.add(menuItemInactive);
    return popupMenu;
  }

  @Override
  protected void inactivateRows() {
    int option = JOptionPane.showConfirmDialog(SwingUtilities.getRoot(AActiveListEntityView.this),
            getMessage("default.ListView.deleteRow.confirm"),
            getMessage("default.ListView.deleteRow.title"), JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
    if (option == JOptionPane.YES_OPTION) {
      int[] selectedRows = tblListEntities.getSelectedRows();
      int[] selectedModelRows = new int[selectedRows.length];
      List<T> removedEntities = new ArrayList<>(selectedRows.length);
      for (int i = 0; i < selectedRows.length; i++) {
        int rowModelIndex = tblListEntities.convertRowIndexToModel(selectedRows[i]);
        removedEntities.add(entities.get(rowModelIndex));
        selectedModelRows[i] = rowModelIndex;
      }
      if (!removedEntities.isEmpty()) {
        beforeDeleteRow(removedEntities);
        long[] deletedIds = removedEntities.stream().mapToLong(entity -> entity.getId()).toArray();
        deleteEntity(deletedIds);
        mainTableModel.deleteRows(selectedModelRows);
        removedEntities.forEach(entity -> {
          entity.setActive(false);
        });
        afterDeleteRows(removedEntities);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        refreshAndBackToFirstPage();
      }
    }
  }

  protected void afterActivateEntity(T entity) {

  }

  private JPopupMenu initActiveViewPopup() {
    JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem menuItemInactive = new JMenuItem("Xoa");
    menuItemInactive.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.DELETE_ICON));
    menuItemInactive.addActionListener(e -> {
      inactivateRows();
    });

    popupMenu.add(menuItemInactive);
    return popupMenu;
  }

  protected abstract void activateEntity(T entity);
}
