/*
 * IBaseDao
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.s3s.ssm.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

public interface IBaseDao<T> {

  void setEntityClass(Class<T> clazz);

  @Deprecated
  T save(T entity);

  @Deprecated
  T update(T entity);

  void delete(T entity);

  T saveOrUpdate(T entity);

  void saveOrUpdateAll(Collection<T> list);

  T findById(Long id);

  T findByCode(String code);

  List<T> findAll();

  List<T> findAllActive();

  DetachedCriteria getCriteria();

  /**
   * A delegation of {@link HibernateTemplate#findByCriteria(DetachedCriteria, int, int)}.
   */
  List findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults);

  /**
   * A delegation of {@link HibernateTemplate#findByCriteria(DetachedCriteria)}.
   */
  List findByCriteria(DetachedCriteria dc);

  /**
   * Retrieves the first of domain objects matching the Hibernate criteria.
   * 
   * @param dc
   *          the criteria that the result has to fulfill <b>Note: Do not reuse criteria objects! They need to
   *          recreated (or cloned e.g. using <tt>SerializationUtils.clone()</tt>) per execution, due to the
   *          suboptimal design of Hibernate's criteria facility.</b>
   * @return the first of objects that fulfill the criteria
   */
  T findFirstByCriteria(DetachedCriteria dc);

  /**
   * Retrieves the number of domain objects matching the Hibernate criteria.
   * 
   * @param hibernateCriteria
   *          the criteria that the result has to fulfill <b>Note: Do not reuse criteria objects! They need to
   *          recreated (or cloned e.g. using <tt>SerializationUtils.clone()</tt>) per execution, due to the
   *          suboptimal design of Hibernate's criteria facility.</b>
   * @return the number of objects that fulfill the criteria
   * @throws DataAccessException
   * 
   * @see ConvenienceHibernateTemplate#findCountByCriteria(DetachedCriteria)
   */
  public int findCountByCriteria(DetachedCriteria hibernateCriteria) throws DataAccessException;

  void deleteAll(Collection<T> entities);

  void flush();

  /**
   * Get the next sequence of the <code>name</code>. If the sequence of <code>name</code> is not in the
   * sequence table
   * yet, it will automatically insert a row in to the table. It may raise the duplicate constraint exception
   * when
   * there are 2 users initialize the <code>name</code> at the same time.
   * 
   * @return the next sequence of <code>name</code>
   */
  long getNextSequence(String name);

  public abstract T uniqueResult(final DetachedCriteria criteria);

  void merge(T entity);
}
