package com.s3s.ssm.widget;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.s3s.ssm.interfaces.IDirtiableObject;

public class VNumberSpinner extends JSpinner implements IDirtiableObject {
  private static final long serialVersionUID = 1L;
  private Integer fInitialContent;
  private List<ChangeListener> changeListeners = new ArrayList<ChangeListener>();

  public VNumberSpinner(int value) {
    this(value, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  public VNumberSpinner(int value, int minValue) {
    super(new SpinnerNumberModel(value, minValue, Integer.MAX_VALUE, 1));
    fInitialContent = value;
  }

  public VNumberSpinner(int value, int minValue, int maxValue) {
    super(new SpinnerNumberModel(value, minValue, maxValue, 1));
    fInitialContent = value;
  }

  @Override
  public void addChangeListener(ChangeListener listener) {
    super.addChangeListener(listener);
    if (changeListeners != null) {
      changeListeners.add(listener);
    }
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
    return !getValue().equals(fInitialContent);
  }

  public Integer getInitialContent() {
    return fInitialContent;
  }

  public void setInitialContent(Integer initialContent) {
    this.fInitialContent = initialContent;
  }
}
