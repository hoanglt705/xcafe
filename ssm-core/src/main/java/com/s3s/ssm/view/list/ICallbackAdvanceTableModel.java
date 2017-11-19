package com.s3s.ssm.view.list;

public interface ICallbackAdvanceTableModel<T> {
  Object getAttributeValue(T entity, ColumnModel dataModel);

  void setAttributeValue(T entity, ColumnModel dataModel, Object aValue);
}
