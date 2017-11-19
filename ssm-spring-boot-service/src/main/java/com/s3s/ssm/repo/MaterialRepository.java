package com.s3s.ssm.repo;

import org.springframework.data.repository.query.Param;

import com.sunrise.xdoc.entity.catalog.Material;

public interface MaterialRepository extends CommonRepository<Material, Long> {
  @Override
  Iterable<Material> findByActive(@Param(value = "active") boolean b);
}
