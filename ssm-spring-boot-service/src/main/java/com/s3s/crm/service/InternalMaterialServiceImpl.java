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

import com.s3s.crm.dto.InternalMaterialDto;
import com.s3s.crm.repo.InternalMaterialRepository;
import com.s3s.crm.service.IInternalMaterialService;
import com.sunrise.xdoc.entity.crm.InternalMaterial;

@Component("internalMaterialService")
@Transactional
public class InternalMaterialServiceImpl implements IInternalMaterialService {
  @Autowired
  private InternalMaterialRepository internalMaterialRepository;

  @Override
  public void saveOrUpdate(InternalMaterialDto dto) {
    InternalMaterial internalMaterial = null;
    if (dto.getId() != null) {
      internalMaterial = internalMaterialRepository.findOne(dto.getId());
    }
    if (internalMaterial == null) {
      internalMaterial = new InternalMaterial();
    }
    BeanUtils.copyProperties(dto, internalMaterial);
    internalMaterialRepository.save(internalMaterial);
  }

  @Override
  public long count() {
    return internalMaterialRepository.count();
  }

  @Override
  public InternalMaterialDto findOne(Long id) {
    if (internalMaterialRepository.exists(id)) {
      InternalMaterial InternalMaterial = internalMaterialRepository.findOne(id);
      return transformToDto(InternalMaterial);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return internalMaterialRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long customerId : ids) {
      if (internalMaterialRepository.exists(customerId)) {
        InternalMaterial InternalMaterial = internalMaterialRepository.findOne(customerId);
        InternalMaterial.setActive(false);
        internalMaterialRepository.save(InternalMaterial);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long areaId : ids) {
      InternalMaterial InternalMaterial = internalMaterialRepository.findOne(areaId);
      InternalMaterial.setActive(true);
      internalMaterialRepository.save(InternalMaterial);
    }

  }

  protected InternalMaterialDto transformToDto(InternalMaterial internalMaterial) {
    InternalMaterialDto internalMaterialDto = new InternalMaterialDto();
    BeanUtils.copyProperties(internalMaterial, internalMaterialDto);
    return internalMaterialDto;
  }

  @Override
  public long countByActive() {
    return internalMaterialRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return internalMaterialRepository.countByActive(false);
  }

  @Override
  public List<InternalMaterialDto> findByActive(int page, int internalMaterial) {
    PageRequest pageRequest = new PageRequest(page, internalMaterial);
    return StreamSupport
            .stream(internalMaterialRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(InternalMaterial -> transformToDto(InternalMaterial)).collect(Collectors.toList());
  }

  @Override
  public List<InternalMaterialDto> findByInactive(int page, int internalMaterial) {
    PageRequest pageRequest = new PageRequest(page, internalMaterial);
    return StreamSupport
            .stream(internalMaterialRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(InternalMaterial -> transformToDto(InternalMaterial)).collect(Collectors.toList());
  }

  @Override
  public List<InternalMaterialDto> findAll(int page, int internalMaterial) {
    PageRequest pageRequest = new PageRequest(page, internalMaterial);
    return StreamSupport.stream(internalMaterialRepository.findAll(pageRequest).spliterator(), false)
            .map(InternalMaterial -> transformToDto(InternalMaterial)).collect(Collectors.toList());
  }
}
