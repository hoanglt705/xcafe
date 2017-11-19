package com.s3s.ssm.widget;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jidesoft.swing.CheckBoxList;
import com.s3s.ssm.interfaces.IDirtiableObject;

public class VCheckBoxList extends CheckBoxList implements IDirtiableObject {
  private static final long serialVersionUID = 1L;
  private Object[] selectedObjects;
  private List<ChangeListener> changeListeners = new ArrayList<ChangeListener>();

  public VCheckBoxList(Object[] listData) {
    super(listData);
    getCheckBoxListSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @SuppressWarnings("unused")
      @Override
      public void valueChanged(ListSelectionEvent e) {
        fireChangeEvent();
      }
    });
    setBackground(Color.white);
  }

  @Override
  protected void init() {
    super.init();
  }

  @Override
  protected Handler createHandler() {
    return new Handler1(this);
  }

  @SuppressWarnings("rawtypes")
  public VCheckBoxList(List listData) {
    this(listData.toArray());
  }

  @SuppressWarnings("rawtypes")
  public VCheckBoxList(ListModel dataModel) {
    super(dataModel);
    setBackground(Color.white);
  }

  @Override
  public void addChangeListener(ChangeListener listener) {
    if (changeListeners != null) {
      changeListeners.add(listener);
    }
  }

  @Override
  public void setSelectedObjects(Object[] elements) {
    super.setSelectedObjects(elements);
    selectedObjects = elements;
  }

  @Override
  public void fireChangeEvent() {
    ChangeEvent e = new ChangeEvent(this);
    for (ChangeListener listener : changeListeners) {
      listener.stateChanged(e);
    }
  }

  @Override
  public boolean isDirty() {
    return !Arrays.equals(selectedObjects, getCheckBoxListSelectedValues());
  }

  public Object[] getInitialContent() {
    return selectedObjects;
  }

  public void setInitialContent(Object[] initialContent) {
    this.selectedObjects = initialContent;
  }

  protected static class Handler1 extends Handler {

    public Handler1(CheckBoxList list) {
      super(list);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      super.mouseClicked(e);
      if (e.getClickCount() == 2) {
        int index = _list.locationToIndex(e.getPoint());
        toggleSelection(index);
      }
    }
  }
}
