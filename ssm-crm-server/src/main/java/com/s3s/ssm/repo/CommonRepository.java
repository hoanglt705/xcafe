package com.s3s.ssm.repo;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface CommonRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

  Iterable<T> findByActive(@Param(value = "active") boolean active);

  Page<T> findByActive(@Param(value = "active") boolean active, Pageable pageable);

  T findByCode(@Param(value = "code") String code);

  long countByActive(@Param(value = "active") boolean active);
}
