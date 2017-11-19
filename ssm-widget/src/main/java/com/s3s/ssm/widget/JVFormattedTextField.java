package com.s3s.ssm.widget;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.s3s.ssm.interfaces.IDirtiableObject;

@SuppressWarnings("unused")
public class JVFormattedTextField extends JFormattedTextField implements IDirtiableObject {
  private static final long serialVersionUID = 1L;
  private Object fInitialContent;
  private final List<ChangeListener> changeListeners = new ArrayList<>();

  public JVFormattedTextField(AbstractFormatter paramAbstractFormatter) {
    super(paramAbstractFormatter);
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
