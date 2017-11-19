package com.s3s.ssm.view.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Icon;

import com.s3s.ssm.dto.IActiveObject;
import com.s3s.ssm.service.IViewService;

public abstract class AListServiceView<T extends IActiveObject> extends AConfiguredListEntityView<T> {
  private static final long serialVersionUID = 1L;
  private IViewService<T> viewService;

  public AListServiceView(Icon icon, String label) {
    super(icon, label);
  }

  public AListServiceView() {
    super();
  }

  @Override
  protected void activateEntity(T entity) {
    getViewService().activate(new long[] {entity.getId()});
  }

  @Override
  protected void deleteEntity(long[] deletedIds) {
    getViewService().inactivate(deletedIds);
  }

  @Override
  protected void saveOrUpdate(T entity) {
    getViewService().saveOrUpdate(entity);
  }

  @Override
  protected int getTotalRow() {
    switch (getShowEnum()) {
      case ACTIVE:
        return (int) getViewService().countByActive();
      case INACTIVE:
        return (int) getViewService().countByInActive();
      default:
        return (int) getViewService().count();
    }
  }

  @Override
  protected List<T> loadData(int page, int size) {
    if (!isInitialized) {
      return Collections.emptyList();
    }
    List<T> dtos = Collections.emptyList();
    switch (getShowEnum()) {
      case ACTIVE:
        dtos = getViewService().findByActive(page, size);
        break;
      case INACTIVE:
        dtos = getViewService().findByInactive(page, size);
        break;
      case ALL:
        dtos = getViewService().findAll(page, size);
        break;
      default:
        break;
    }
    List<T> entities = new ArrayList<T>();
    dtos.forEach(dto -> {
      entities.add(dto);
    });
    return entities;
  }

  public IViewService<T> getViewService() {
    return viewService;
  }

  public void setViewService(IViewService<T> viewService) {
    this.viewService = viewService;
  }
}
