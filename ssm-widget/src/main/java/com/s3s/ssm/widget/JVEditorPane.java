package com.s3s.ssm.widget;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.s3s.ssm.interfaces.IDirtiableObject;

public class JVEditorPane extends JEditorPane implements IDirtiableObject {
  private static final long serialVersionUID = 1L;
  private String fInitialContent;
  private List<ChangeListener> changeListeners = new ArrayList<ChangeListener>();

  public JVEditorPane(String content) {
    super();
    setText(content);
    fInitialContent = content;
    getDocument().addDocumentListener(new DocumentListener() {

      @SuppressWarnings("unused")
      @Override
      public void removeUpdate(DocumentEvent e) {
        fireChangeEvent();
      }

      @SuppressWarnings("unused")
      @Override
      public void insertUpdate(DocumentEvent e) {
        fireChangeEvent();
      }

      @SuppressWarnings("unused")
      @Override
      public void changedUpdate(DocumentEvent e) {
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
    return !getText().equals(getInitialContent());
  }

  public String getInitialContent() {
    return fInitialContent;
  }

  public void setInitialContent(String initialContent) {
    this.fInitialContent = initialContent;
  }
}
