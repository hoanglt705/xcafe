package com.s3s.ssm.view.detail.config;

import java.util.Map;

import com.s3s.ssm.dto.base.ICodeObject;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.edit.AbstractSingleEditView;

public abstract class AEditServiceView<T extends ICodeObject> extends AbstractSingleEditView<T> {
  private static final long serialVersionUID = 1L;
  private IViewService<T> viewService;

  public AEditServiceView(Map<String, Object> entity) {
    super(entity);
  }

  protected void setViewService(IViewService<T> viewService) {
    this.viewService = viewService;
  }

  @Override
  protected void saveOrUpdate(T entity) {
    getViewService().saveOrUpdate(entity);
  }

  @Override
  protected boolean exists(String code) {
    return getViewService().exists(code);
  }

  @Override
  protected T loadForEdit() {
    long id = (long) request.get(PARAM_ENTITY_ID);
    return getViewService().findOne(id);
  }

  public IViewService<T> getViewService() {
    return viewService;
  }

  protected void loadService() {

  }
}
