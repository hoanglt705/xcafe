package com.s3s.ssm.view.list;

import javax.swing.Icon;

import com.s3s.ssm.dto.IActiveObject;

public abstract class AConfiguredListEntityView<T extends IActiveObject> extends
        AActiveListEntityView<T> {
  private static final long serialVersionUID = 1L;

  public AConfiguredListEntityView(Icon icon, String label) {
    super(icon, label);
  }

  public AConfiguredListEntityView() {
    super();
  }

  @Override
  protected int getDefaultPageSize() {
    return 20;
  }
}
