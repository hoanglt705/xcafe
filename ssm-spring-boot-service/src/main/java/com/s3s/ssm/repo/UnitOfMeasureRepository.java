/*
 * Copyright 2012-2013 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.s3s.ssm.repo;

import org.springframework.data.repository.query.Param;

import com.sunrise.xdoc.entity.config.UnitOfMeasure;
import com.sunrise.xdoc.entity.config.UomCategory;

public interface UnitOfMeasureRepository extends CommonRepository<UnitOfMeasure, Long> {
  Iterable<UnitOfMeasure> findByUomCategory(@Param(value = "uomCategory") UomCategory uomCategory);

  Iterable<UnitOfMeasure> findByIsBaseMeasure(@Param(value = "isBaseMeasure") boolean isBaseMeasure);
}
