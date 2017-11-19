/*
 * MultiSelectableTreeNode
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

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class MultiSelectableTreeNode extends DefaultMutableTreeNode {
  private static final long serialVersionUID = -1123892586397651230L;
  private SelectableData nodeData;

  public MultiSelectableTreeNode(String label, Object value, boolean isSelected) {
    super();
    nodeData = new SelectableData(label, isSelected, value);
    setUserObject(nodeData);
  }

  /**
   * {@inheritDoc}</br> Just take into account the selected node.
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    int pChildIndex = childIndex;
    while (true) {
      MultiSelectableTreeNode node = (MultiSelectableTreeNode) children.get(pChildIndex);
      if (!node.isSelected()) {
        return node;
      }
      pChildIndex++;
    }
  }

  /**
   * {@inheritDoc}</br> Just count the selected Nodes.
   */
  @Override
  public int getChildCount() {
    int count = 0;
    for (int i = 0; i < super.getChildCount(); i++) {
      MultiSelectableTreeNode node = (MultiSelectableTreeNode) super.getChildAt(i);
      if (!node.isSelected()) {
        count++;
      }
    }
    return count;
  }

  @Override
  public void add(MutableTreeNode newChild) {
    super.add(newChild);
    MultiSelectableTreeNode child = (MultiSelectableTreeNode) newChild;
    if (!child.isSelected()) {
      unselectParent(child);
    }
  }

  @Override
  public void insert(MutableTreeNode newChild, int childIndex) {
    super.insert(newChild, childIndex);
    MultiSelectableTreeNode child = (MultiSelectableTreeNode) newChild;
    if (!child.isSelected()) {
      unselectParent(child);
    }
  }

  @Override
  public void remove(int childIndex) {
    super.remove(childIndex);
    MultiSelectableTreeNode child = (MultiSelectableTreeNode) super.getChildAt(childIndex);
    if (!child.isSelected()) {
      updateParentToSelect(child);
    }
  }

  @Override
  public void removeFromParent() {
    super.removeFromParent();
    if (!isSelected()) {
      updateParentToSelect(this);
    }
  }

  @Override
  public void remove(MutableTreeNode aChild) {
    super.remove(aChild);
    MultiSelectableTreeNode child = (MultiSelectableTreeNode) aChild;
    if (!child.isSelected()) {
      updateParentToSelect(child);
    }
  }

  @Override
  public boolean isLeaf() {
    return super.getChildCount() == 0;
  }

  /**
   * Get all selected leaf nodes from the node. If the node is a selected leaf node, return a list contains
   * that one.
   * 
   * @param node
   *          the node wanted to find all selected leaf nodes from it.
   * @return a list of selected leaf nodes.
   */
  public List<MultiSelectableTreeNode> getAllSelectedLeafNodes(MultiSelectableTreeNode node) {
    List<MultiSelectableTreeNode> list = new ArrayList<>();
    if (node.isLeaf()) {
      if (node.isSelected()) {
        list.add(node);
      }
    } else {
      for (int i = 0; i < node.children.size(); i++) {
        list.addAll(getAllSelectedLeafNodes((MultiSelectableTreeNode) node.children.get(i)));
      }
    }
    return list;
  }

  /**
   * Unselect all parent of parent... up to the unselected parent node.
   * 
   * @param currentNode
   * @return the node, the branch from which has been changed.
   */
  private MultiSelectableTreeNode unselectParent(MultiSelectableTreeNode currentNode) {
    MultiSelectableTreeNode parentNode = (MultiSelectableTreeNode) currentNode.getParent();
    if (parentNode != null && parentNode.isSelected()) {
      parentNode.setSelected(false);
      return unselectParent(parentNode);
    }
    return parentNode;
  }

  /**
   * Set the state of node. This action automatically update the parent and child of this node to ensure the
   * tree is
   * Multi-Selectable Tree (Please refer to the document to know the definition).
   * 
   * @param selected
   * @return the node, the branch from which has been changed.
   */
  public MultiSelectableTreeNode setState(boolean selected) {
    if (selected == isSelected()) {
      return null;
    }

    if (isLeaf()) {
      nodeData.setSelected(selected);
      if (!selected) {
        return unselectParent(this);
      }
      return updateParentToSelect(this);
    }
    if (!selected) {
      throw new IllegalStateException("Do not allow to unselect the intermediate node");
    }
    nodeData.setSelected(selected);
    MultiSelectableTreeNode parent = updateParentToSelect(this); // update the state of parent
    selectAllChildBranch(this); // select all child node
    return parent;
  }

  private void selectAllChildBranch(MultiSelectableTreeNode currentNode) {
    if (currentNode.children == null) {
      return;
    }
    for (int i = 0; i < currentNode.children.size(); i++) {
      MultiSelectableTreeNode child = (MultiSelectableTreeNode) currentNode.children.get(i);
      child.setSelected(true);
      selectAllChildBranch(child);
    }
  }

  /**
   * If the children are all selected --> the parent is set selected.
   * 
   * @param currentNode
   * @return the node, the branch from which has been changed.
   */
  private MultiSelectableTreeNode updateParentToSelect(MultiSelectableTreeNode currentNode) {
    MultiSelectableTreeNode parentNode = (MultiSelectableTreeNode) currentNode.getParent();
    if (parentNode != null && !parentNode.isSelected() && parentNode.isAllChildSelected()) {
      parentNode.setSelected(true);
      return updateParentToSelect(parentNode);
    }
    return parentNode;
  }

  private boolean isAllChildSelected() {
    for (int i = 0; i < children.size(); i++) {
      MultiSelectableTreeNode node = (MultiSelectableTreeNode) children.get(i);
      if (!node.isSelected()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Builds the parents of node up to and including the node before root node, where the original node is the
   * last
   * element in the returned list.
   * 
   * @return a list of TreeNodes giving the path from the node before the root node to the current node
   *         <b>(not
   *         include the root node)</b>.
   */
  public List<MultiSelectableTreeNode> getPathToBeforeRoot() {
    List<MultiSelectableTreeNode> list = new ArrayList<>();
    MultiSelectableTreeNode currentNode = this;
    while (currentNode != null && !currentNode.isRoot()) {
      list.add(0, currentNode);
      currentNode = (MultiSelectableTreeNode) currentNode.getParent();
    }
    return list;
  }

  private void setSelected(boolean selected) {
    nodeData.setSelected(selected);
  }

  public boolean isSelected() {
    return nodeData.isSelected();
  }

  public Object getValue() {
    return nodeData.getValue();
  }

  public class SelectableData {
    private String label;
    private boolean isSelected;
    private Object value;

    public SelectableData(String label, boolean isSelected, Object value) {
      super();
      this.label = label;
      this.isSelected = isSelected;
      this.value = value;
    }

    public String getLabel() {
      return label;
    }

    public void setLabel(String label) {
      this.label = label;
    }

    public boolean isSelected() {
      return isSelected;
    }

    public void setSelected(boolean isSelected) {
      this.isSelected = isSelected;
    }

    public Object getValue() {
      return value;
    }

    public void setValue(Object value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return label;
    }
  }
}
