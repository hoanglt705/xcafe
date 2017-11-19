package com.s3s.ssm.widget;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXComboBox;

import com.s3s.ssm.interfaces.IDirtiableObject;

public class JVComboBox extends JXComboBox implements IDirtiableObject {
  private static final long serialVersionUID = 1L;
  private Object fInitialContent;
  private final List<ChangeListener> changeListeners = new ArrayList<>();

  public JVComboBox(Object[] items) {
    super(items);
    addItemListener(new ItemListener() {
      @SuppressWarnings("unused")
      @Override
      public void itemStateChanged(ItemEvent paramItemEvent) {
        fireChangeEvent();
      }
    });
  }

  @Override
  public void fireChangeEvent() {
    for (ChangeListener listener : changeListeners) {
      listener.stateChanged(new ChangeEvent(this));
    }
  }

  @Override
  public boolean isDirty() {
    if (getSelectedItem() == null && getInitialContent() == null) {
      return false;
    }
    if (getSelectedItem() == null || getInitialContent() == null) {
      return true;
    }
    return !getSelectedItem().equals(getInitialContent());
  }

  public Object getInitialContent() {
    return fInitialContent;
  }

  public void setInitialContent(Object fInitialContent) {
    this.fInitialContent = fInitialContent;
  }

  @Override
  public void addChangeListener(ChangeListener listener) {
    changeListeners.add(listener);
  }
}
