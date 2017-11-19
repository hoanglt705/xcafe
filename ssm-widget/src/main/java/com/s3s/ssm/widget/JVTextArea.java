package com.s3s.ssm.widget;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXTextArea;

import com.s3s.ssm.interfaces.IDirtiableObject;

@SuppressWarnings("unused")
public class JVTextArea extends JXTextArea implements IDirtiableObject {
  private static final long serialVersionUID = 1L;
  private String fInitialContent = "";
  private final List<ChangeListener> changeListeners = new ArrayList<>();

  public JVTextArea() {
    super();
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
    return !getText().equals(getInitialContent());
  }

  public String getInitialContent() {
    if (fInitialContent == null) {
      return "";
    }
    return fInitialContent;
  }

  public void setInitialContent(String fInitialContent) {
    this.fInitialContent = fInitialContent;
  }

  @Override
  public void addChangeListener(ChangeListener listener) {
    changeListeners.add(listener);
  }
}
