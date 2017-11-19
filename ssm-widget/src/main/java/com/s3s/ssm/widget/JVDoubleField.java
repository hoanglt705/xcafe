package com.s3s.ssm.widget;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.softsmithy.lib.swing.JDoubleField;

import com.s3s.ssm.interfaces.IDirtiableObject;

@SuppressWarnings("unused")
public class JVDoubleField extends JDoubleField implements IDirtiableObject {
  private static final long serialVersionUID = 1L;
  private Double fInitialContent;
  private final List<ChangeListener> changeListeners = new ArrayList<>();

  public JVDoubleField() {
    this(0);
  }

  public JVDoubleField(double value) {
    super(value);
    fInitialContent = value;
    getDocument().addDocumentListener(new DocumentListener() {

      @Override
      public void removeUpdate(DocumentEvent e) {
        fireChangeEvent();
      }

      @Override
      public void insertUpdate(DocumentEvent e) {
        fireChangeEvent();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
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
    if (getValue() == null) {
      return false;
    }
    return !getValue().equals(getInitialContent());
  }

  public Double getInitialContent() {
    return fInitialContent;
  }

  public void setInitialContent(Double fInitialContent) {
    this.fInitialContent = fInitialContent;
  }

  @Override
  public void addChangeListener(ChangeListener listener) {
    changeListeners.add(listener);
  }
}
