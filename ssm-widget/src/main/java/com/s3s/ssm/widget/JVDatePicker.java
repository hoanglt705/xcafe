package com.s3s.ssm.widget;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.lang.time.DateUtils;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;

import com.s3s.ssm.interfaces.IDirtiableObject;

@SuppressWarnings("unused")
public class JVDatePicker extends JXDatePicker implements IDirtiableObject {
  private static final long serialVersionUID = 1L;
  private Date fInitialContent;
  private final List<ChangeListener> changeListeners = new ArrayList<ChangeListener>();

  static {
    UIManager.put(CalendarHeaderHandler.uiControllerID,
            "org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler");
  }

  public JVDatePicker() {
    super();
    getEditor().getDocument().addDocumentListener(new DocumentListener() {

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
  public void addChangeListener(ChangeListener listener) {
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
    return !DateUtils.isSameDay(getDate(), getInitialContent());
  }

  public Date getInitialContent() {
    return fInitialContent;
  }

  public void setInitialContent(Date fInitialContent) {
    this.fInitialContent = fInitialContent;
  }

  @Override
  public void setName(String name) {
    super.setName(name);
    getEditor().setName(name);
  }

}
