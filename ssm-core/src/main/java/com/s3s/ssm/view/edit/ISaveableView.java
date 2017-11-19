package com.s3s.ssm.view.edit;

public interface ISaveableView {
  public boolean isDirty();

  public boolean doSave();
}
