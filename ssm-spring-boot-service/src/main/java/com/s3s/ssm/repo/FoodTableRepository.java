package com.s3s.ssm.repo;

import org.springframework.data.repository.query.Param;

import com.sunrise.xdoc.entity.config.Area;
import com.sunrise.xdoc.entity.config.FoodTable;

public interface FoodTableRepository extends CommonRepository<FoodTable, Long> {
  Iterable<FoodTable> findByArea(@Param(value = "area") Area area);
}
