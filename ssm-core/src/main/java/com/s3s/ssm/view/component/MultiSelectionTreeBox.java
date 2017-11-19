/*
 * MultiSelectionTreeBox
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

package com.s3s.ssm.view.component;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.commons.lang.StringUtils;

import com.s3s.ssm.widget.AbstractMultiSelectionBox;

/**
 * @author Phan Hong Phuc
 * @since Feb 26, 2012
 */
public class MultiSelectionTreeBox extends AbstractMultiSelectionBox {
  private static final long serialVersionUID = 3125395234804094978L;
  private JTree tree;
  private JList<List<MultiSelectableTreeNode>> list;
  private MultiSelectableTreeNode rootNode;

  public MultiSelectionTreeBox(MultiSelectableTreeNode rootNode) {
    super();
    this.rootNode = rootNode;
    initComponents();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected JList<List<MultiSelectableTreeNode>> createDestinationView() {
    DefaultListModel<List<MultiSelectableTreeNode>> model = new DefaultListModel<>();
    list = new JList<>(model);
    refreshDataForList();
    list.setCellRenderer(new DefaultListCellRenderer() {
      private static final long serialVersionUID = 3367484517532759939L;

      @SuppressWarnings("unchecked")
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value, int index,
              boolean isSelected,
              boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setText(StringUtils.join((List<MultiSelectableTreeNode>) value, '>'));
        return this;
      }
    });
    DefaultListSelectionModel selectionModel = new DefaultListSelectionModel();
    selectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    list.setSelectionModel(selectionModel);
    return list;
  }

  private void refreshDataForList() {
    DefaultListModel<List<MultiSelectableTreeNode>> model = (DefaultListModel<List<MultiSelectableTreeNode>>) list
            .getModel();
    model.removeAllElements();
    List<MultiSelectableTreeNode> leafNodes = rootNode.getAllSelectedLeafNodes(rootNode);
    for (MultiSelectableTreeNode node : leafNodes) {
      model.addElement(node.getPathToBeforeRoot());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected JTree createSourceView() {
    tree = new JTree(new DefaultTreeModel(rootNode));
    tree.setRootVisible(false);
    return tree;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSelectAll() {
    rootNode.setState(true);
    refreshDataForList();
    ((DefaultTreeModel) tree.getModel()).nodeStructureChanged(rootNode);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSelectSingle() {
    for (TreePath treePath : tree.getSelectionPaths()) {
      MultiSelectableTreeNode node = (MultiSelectableTreeNode) treePath.getLastPathComponent();
      node.setState(true);
    }
    refreshDataForList();
    ((DefaultTreeModel) tree.getModel()).nodeStructureChanged(rootNode);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doDeselectAll() {
    List<MultiSelectableTreeNode> selectedLeafNodes = rootNode.getAllSelectedLeafNodes(rootNode);
    for (MultiSelectableTreeNode node : selectedLeafNodes) {
      node.setState(false);
    }
    refreshDataForList();
    ((DefaultTreeModel) tree.getModel()).nodeStructureChanged(rootNode);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doDeselectSingle() {
    List<List<MultiSelectableTreeNode>> selectedList = list.getSelectedValuesList();
    for (List<MultiSelectableTreeNode> nodePath : selectedList) {
      nodePath.get(nodePath.size() - 1).setState(false);
    }
    refreshDataForList();
    ((DefaultTreeModel) tree.getModel()).nodeStructureChanged(rootNode);
  }

  /**
   * Get the selected values.
   * 
   * @return list of selected values.
   */
  public List<Object> getSelectedValues() {
    List<Object> values = new ArrayList<>();
    for (List<MultiSelectableTreeNode> ln : list.getSelectedValuesList()) {
      values.add(ln.get(ln.size() - 1).getValue());
    }
    return values;
  }

  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    tree.setEnabled(enabled);
    list.setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object[] getSelectedObjects() {
    return getSelectedValues().toArray();
  }
}
