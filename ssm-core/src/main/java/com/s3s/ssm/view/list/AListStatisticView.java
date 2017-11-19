package com.s3s.ssm.view.list;

import java.awt.BorderLayout;

public abstract class AListStatisticView<T> extends ASearchListView<T> {
  private static final long serialVersionUID = -7995535532858732146L;

  public AListStatisticView() {
    remove(tabbedPane);
    add(contentPane);
    getPagingNavigator().setVisible(false);
  }

  @Override
  protected int getTotalPages() {
    return 1;
  }

  @Override
  protected void addSearchPanel() {
    if (createSearchPanel() == null) {
      return;
    }
    contentPane.add(createSearchPanel(), BorderLayout.NORTH);
  }

  @Override
  protected void clearCriteria() {
  }

}
