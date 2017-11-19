/*
 * AbstractMultiSelectionBox
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

package com.s3s.ssm.widget;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

/**
 * @author Phan Hong Phuc
 * @since Feb 26, 2012
 */
@SuppressWarnings("unused")
public abstract class AbstractMultiSelectionBox extends JPanel implements ItemSelectable {
  private static final long serialVersionUID = 2407086597110014517L;
  private static final Dimension DEFAULT_LIST_SIZE = new Dimension(100, 200);
  private JButton btnSelectSingle;
  private JButton btnSelectAll;
  private JButton btnDeselectSingle;
  private JButton btnDeselectAll;

  private List<ItemListener> itemListeners = new ArrayList<>();

  protected void initComponents() {
    setLayout(new MigLayout("ins 0", "[grow,fill]0[center]0[grow,fill]", "[center]"));

    // ////////// Init the buttons /////////////
    btnDeselectSingle = new JButton("<");
    btnDeselectSingle.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        doDeselectSingle();
        fireItemChanged(ItemEvent.DESELECTED);
      }
    });

    btnDeselectAll = new JButton("<<");
    btnDeselectAll.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        doDeselectAll();
        fireItemChanged(ItemEvent.DESELECTED);
      }
    });

    btnSelectSingle = new JButton(">");
    btnSelectSingle.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        doSelectSingle();
        fireItemChanged(ItemEvent.SELECTED);
      }
    });

    btnSelectAll = new JButton(">>");
    btnSelectAll.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        doSelectAll();
        fireItemChanged(ItemEvent.SELECTED);
      }
    });

    JScrollPane sourceScrollpane = new JScrollPane(createSourceView());
    sourceScrollpane.setPreferredSize(DEFAULT_LIST_SIZE);
    JScrollPane desScrollpane = new JScrollPane(createDestinationView());
    desScrollpane.setPreferredSize(DEFAULT_LIST_SIZE);
    add(sourceScrollpane, "cell 0 0");
    add(btnSelectAll, "flowy, cell 1 0, growx");
    add(btnSelectSingle, "cell 1 0, growx");
    add(btnDeselectSingle, "cell 1 0, growx");
    add(btnDeselectAll, "cell 1 0, growx");
    add(desScrollpane, "cell 2 0");
  }

  /**
   * @return
   */
  protected abstract Component createDestinationView();

  /**
   * @return
   */
  protected abstract Component createSourceView();

  /**
     * 
     */
  protected abstract void doSelectAll();

  /**
     * 
     */
  protected abstract void doSelectSingle();

  /**
     * 
     */
  protected abstract void doDeselectAll();

  /**
     * 
     */
  protected abstract void doDeselectSingle();

  protected JButton getBtnSelectSingle() {
    return btnSelectSingle;
  }

  protected JButton getBtnSelectAll() {
    return btnSelectAll;
  }

  protected JButton getBtnDeselectSingle() {
    return btnDeselectSingle;
  }

  protected JButton getBtnDeselectAll() {
    return btnDeselectAll;
  }

  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    btnDeselectAll.setEnabled(enabled);
    btnDeselectSingle.setEnabled(enabled);
    btnSelectAll.setEnabled(enabled);
    btnSelectSingle.setEnabled(enabled);
  }

  protected void fireItemChanged(int stateChange) {
    ItemEvent ie = new ItemEvent(this, 0, null, stateChange);
    for (ItemListener i : itemListeners) {
      i.itemStateChanged(ie);
    }
  }

  @Override
  public void addItemListener(ItemListener itemListener) {
    itemListeners.add(itemListener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract Object[] getSelectedObjects();

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeItemListener(ItemListener l) {
    itemListeners.remove(l);
  }
}
