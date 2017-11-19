package com.s3s.ssm.service;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public abstract class AbstractViewService<T> implements IViewService<T> {

  @Override
  public void inactivate(long[] ids) {
  }

  @Override
  public void activate(long[] ids) {
  }

  @Override
  public void saveOrUpdate(T dto) {
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public long countByActive() {
    return 0;
  }

  @Override
  public long countByInActive() {
    return 0;
  }

  @Override
  public T findOne(Long id) {
    return null;
  }

  @Override
  public boolean exists(String code) {
    return false;
  }

  @Override
  public List<T> findByActive(int page, int size) {
    return Collections.emptyList();
  }

  @Override
  public List<T> findByInactive(int page, int size) {
    return Collections.emptyList();
  }

  @Override
  public List<T> findAll(int page, int size) {
    return Collections.emptyList();
  }

}
