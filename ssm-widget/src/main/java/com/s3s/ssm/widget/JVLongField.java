package com.s3s.ssm.widget;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.softsmithy.lib.swing.JLongField;

import com.s3s.ssm.interfaces.IDirtiableObject;

@SuppressWarnings("unused")
public class JVLongField extends JLongField implements IDirtiableObject {
  private static final long serialVersionUID = 1L;
  private Long fInitialContent;
  private final List<ChangeListener> changeListeners = new ArrayList<>();

  public JVLongField() {
    this(0);
  }

  public JVLongField(long value) {
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

  public Long getInitialContent() {
    return fInitialContent;
  }

  public void setInitialContent(Long fInitialContent) {
    this.fInitialContent = fInitialContent;
  }

  @Override
  public void addChangeListener(ChangeListener listener) {
    changeListeners.add(listener);
  }
}
