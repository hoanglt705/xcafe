/*
 * MultiSelectionListBox
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

import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ListCellRenderer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXList;

import com.s3s.ssm.interfaces.IDirtiableObject;

/**
 * The component multi-selection box. This component includes the source list box, the destination list box
 * and 4
 * buttons to move elements between these 2 list boxes. </p> The useful methods:
 * <ul>
 * <li>{@link #getSourceValues()}</li>
 * <li>{@link #getDestinationValues()}</li>
 * </ul>
 * 
 */
public class MultiSelectionListBox<T> extends AbstractMultiSelectionBox implements IDirtiableObject {

  private static final long serialVersionUID = 1L;

  // Subcomponents
  private JXList lstSource;
  private JXList lstDest;

  // Data
  private List<T> sources = new ArrayList<T>();
  private List<T> destinations = new ArrayList<T>();
  private List<T> initialDestinations = new ArrayList<T>();

  // Renderer
  private ListCellRenderer<T> cellRenderer;
  private final List<ChangeListener> changeListeners = new ArrayList<ChangeListener>();

  /**
   * Initialize multiselectbox with the source values and the destination values. And renderer of 2 list box
   * is {@link DefaultListCellRenderer}.
   * 
   * @param sources
   * @param destinations
   */
  public MultiSelectionListBox(List<T> sources, List<T> destinations) {
    this(sources, destinations, null);
  }

  /**
   * Initialize multiselectbox with the source values and the destination values, and the renderer of 2 list
   * box.
   * 
   * @param sources
   * @param destinations
   * @param cellRenderer
   */
  public MultiSelectionListBox(List<T> sources, List<T> destinations, ListCellRenderer<T> cellRenderer) {
    super();
    this.sources = new ArrayList<>(sources);
    this.destinations = new ArrayList<>(destinations);
    this.initialDestinations = new ArrayList<>(destinations);
    this.cellRenderer = cellRenderer;
    initComponents();
  }

  @Override
  protected void initComponents() {
    super.initComponents();
    // Move the selected element when double click to it.
    lstSource.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          move(lstSource, lstDest, false);
          fireItemChanged(ItemEvent.SELECTED);
        }
      }
    });
    lstDest.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          move(lstDest, lstSource, false);
          fireItemChanged(ItemEvent.DESELECTED);
        }
      }
    });
  }

  @Override
  protected void fireItemChanged(int stateChange) {
    super.fireItemChanged(stateChange);
    fireChangeEvent();
  }

  private JXList createJList(List<T> data, ListCellRenderer<T> renderer) {
    DefaultListModel<T> listModel = new DefaultListModel<>();
    for (T d : data) {
      listModel.addElement(d);
    }
    JXList jList = new JXList(listModel);
    jList.setCellRenderer(renderer);
    DefaultListSelectionModel selectionModel = new DefaultListSelectionModel();
    jList.setSelectionModel(selectionModel);
    return jList;
  }

  @SuppressWarnings("unchecked")
  private void enableDisableButtons() {
    DefaultListModel<T> sourceModel = (DefaultListModel<T>) lstSource.getModel();
    DefaultListModel<T> destModel = (DefaultListModel<T>) lstDest.getModel();
    getBtnDeselectAll().setEnabled(destModel.getSize() > 0);
    getBtnDeselectSingle().setEnabled(destModel.getSize() > 0);
    getBtnSelectAll().setEnabled(sourceModel.getSize() > 0);
    getBtnSelectSingle().setEnabled(sourceModel.getSize() > 0);
  }

  /**
   * Move elements from JList <code>from</code> to JList <code>to</code>.
   * 
   * @param from
   * @param to
   * @param allElements
   *          move all elements if <code>true</code>, else just move the selected values of <code>from</code>
   *          JList.
   */
  @SuppressWarnings("unchecked")
  private void move(JXList from, JXList to, boolean allElements) {
    DefaultListModel<T> fromModel = (DefaultListModel<T>) from.getModel();
    DefaultListModel<T> toModel = (DefaultListModel<T>) to.getModel();
    if (allElements) {
      for (int i = 0; i < fromModel.getSize(); i++) {
        toModel.addElement(fromModel.get(i));
      }
      fromModel.removeAllElements();
    } else {
      for (T o : (List<T>) from.getSelectedValuesList()) {
        fromModel.removeElement(o);
        toModel.addElement(o);
      }
    }
    enableDisableButtons();
  }

  @SuppressWarnings("unchecked")
  private List<T> getAllValuesOfJList(JXList jList) {
    DefaultListModel<T> sourceModel = (DefaultListModel<T>) jList.getModel();
    List<T> sourceData = new ArrayList<T>(sourceModel.size());
    for (int i = 0; i < sourceModel.size(); i++) {
      sourceData.add(sourceModel.get(i));
    }
    return sourceData;
  }

  /**
   * Get destination data.
   * 
   * @return the list of values in destination list box.
   */
  public List<T> getDestinationValues() {
    return getAllValuesOfJList(lstDest);
  }

  /**
   * Get source data.
   * 
   * @return the list of values in sources list box.
   */
  public List<T> getSourceValues() {
    return getAllValuesOfJList(lstSource);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected JXList createDestinationView() {
    lstDest = createJList(destinations, cellRenderer);
    lstDest.setName("lstDest");
    return lstDest;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected JXList createSourceView() {
    lstSource = createJList(sources, cellRenderer);
    lstSource.setName("lstSource");
    return lstSource;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSelectAll() {
    move(lstSource, lstDest, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSelectSingle() {
    move(lstSource, lstDest, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doDeselectAll() {
    move(lstDest, lstSource, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doDeselectSingle() {
    move(lstDest, lstSource, false);
  }

  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    lstSource.setEnabled(enabled);
    lstDest.setEnabled(enabled);
  }

  @Override
  public Object[] getSelectedObjects() {
    return getDestinationValues().toArray();
  }

  @Override
  public void fireChangeEvent() {
    ChangeEvent e = new ChangeEvent(this);
    for (ChangeListener listener : changeListeners) {
      listener.stateChanged(e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean isDirty() {
    DefaultListModel<T> model = (DefaultListModel<T>) lstDest.getModel();
    if (model.getSize() != initialDestinations.size()) {
      return true;
    }
    for (T t : initialDestinations) {
      if (!model.contains(t)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void addChangeListener(ChangeListener listener) {
    changeListeners.add(listener);
  }

  public Object getInitialContent() {
    return initialDestinations;
  }

  public void setInitialContent(List<T> initialContent) {
    this.initialDestinations = initialContent;
  }
}
