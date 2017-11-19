package com.s3s.ssm.service;

import java.util.List;

public interface IViewService<T> {
  void inactivate(long[] ids);

  void activate(long[] ids);

  void saveOrUpdate(T dto);

  long count();

  long countByActive();

  long countByInActive();

  T findOne(Long id);

  boolean exists(String code);

  List<T> findByActive(int page, int size);

  List<T> findByInactive(int page, int size);

  List<T> findAll(int page, int size);
}
