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

package com.s3s.crm.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.crm.dto.MaterialTypeDto;
import com.s3s.crm.repo.MaterialTypeRepository;
import com.s3s.crm.service.IMaterialTypeService;
import com.sunrise.xdoc.entity.crm.MaterialType;

@Component("materialTypeService")
@Transactional
public class MaterialTypeServiceImpl implements IMaterialTypeService {
  @Autowired
  private MaterialTypeRepository materaialTypeRepository;

  @Override
  public void saveOrUpdate(MaterialTypeDto dto) {
    MaterialType size = null;
    if (dto.getId() != null) {
      size = materaialTypeRepository.findOne(dto.getId());
    }
    if (size == null) {
      size = new MaterialType();
    }
    BeanUtils.copyProperties(dto, size);
    materaialTypeRepository.save(size);
  }

  @Override
  public long count() {
    return materaialTypeRepository.count();
  }

  @Override
  public MaterialTypeDto findOne(Long id) {
    if (materaialTypeRepository.exists(id)) {
      MaterialType MaterialType = materaialTypeRepository.findOne(id);
      return transformToDto(MaterialType);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return materaialTypeRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long customerId : ids) {
      if (materaialTypeRepository.exists(customerId)) {
        MaterialType MaterialType = materaialTypeRepository.findOne(customerId);
        MaterialType.setActive(false);
        materaialTypeRepository.save(MaterialType);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long areaId : ids) {
      MaterialType MaterialType = materaialTypeRepository.findOne(areaId);
      MaterialType.setActive(true);
      materaialTypeRepository.save(MaterialType);
    }

  }

  protected MaterialTypeDto transformToDto(MaterialType size) {
    MaterialTypeDto sizeDto = new MaterialTypeDto();
    BeanUtils.copyProperties(size, sizeDto);
    return sizeDto;
  }

  @Override
  public long countByActive() {
    return materaialTypeRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return materaialTypeRepository.countByActive(false);
  }

  @Override
  public List<MaterialTypeDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(materaialTypeRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(MaterialType -> transformToDto(MaterialType)).collect(Collectors.toList());
  }

  @Override
  public List<MaterialTypeDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport
            .stream(materaialTypeRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(MaterialType -> transformToDto(MaterialType)).collect(Collectors.toList());
  }

  @Override
  public List<MaterialTypeDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(materaialTypeRepository.findAll(pageRequest).spliterator(), false)
            .map(MaterialType -> transformToDto(MaterialType)).collect(Collectors.toList());
  }
}
