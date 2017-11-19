package com.s3s.ssm.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.s3s.ssm.interfaces.IDirtiableObject;

public class JVCheckBox extends JCheckBox implements IDirtiableObject {
  private static final long serialVersionUID = 1L;
  private boolean fInitialContent;
  private List<ChangeListener> changeListeners = new ArrayList<ChangeListener>();

  public JVCheckBox() {
    this(false);
  }

  public JVCheckBox(boolean selected) {
    super();
    setSelected(selected);
    fInitialContent = selected;
    addActionListener(new ActionListener() {
      @SuppressWarnings("unused")
      @Override
      public void actionPerformed(ActionEvent e) {
        fireChangeEvent();
      }
    });
  }

  @Override
  public void addChangeListener(ChangeListener listener) {
    if (changeListeners == null) {
      changeListeners = new ArrayList<ChangeListener>();
    }
    changeListeners.add(listener);
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
    return !isSelected() == getInitialContent();
  }

  public boolean getInitialContent() {
    return fInitialContent;
  }

  public void setInitialContent(boolean initialContent) {
    this.fInitialContent = initialContent;
  }
}
