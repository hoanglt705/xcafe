package com.s3s.ssm.interfaces;

import javax.swing.event.ChangeListener;

public interface IDirtiableObject {

  public abstract void fireChangeEvent();

  public abstract boolean isDirty();

  void addChangeListener(ChangeListener listener);

}
