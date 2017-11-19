package com.s3s.ssm.widget;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jidesoft.spinner.DateSpinner;
import com.s3s.ssm.interfaces.IDirtiableObject;

public class VDateSpinner extends DateSpinner implements IDirtiableObject {
  private static final long serialVersionUID = 1L;
  private Date fInitialContent;
  private List<ChangeListener> changeListeners = new ArrayList<ChangeListener>();

  public VDateSpinner() {
    super();
  }

  public VDateSpinner(String format, Date date) {
    super(format, date);
  }

  public VDateSpinner(String format) {
    super(format);
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

  public Date getInitialContent() {
    return fInitialContent;
  }

  public void setInitialContent(Date initialContent) {
    this.fInitialContent = initialContent;
  }

  public Date getDate() {
    return (Date) getValue();
  }
}
