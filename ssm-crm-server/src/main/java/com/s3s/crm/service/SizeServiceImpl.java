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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.crm.dto.SizeDto;
import com.s3s.crm.repo.SizeRepository;
import com.s3s.crm.service.ISizeService;
import com.sunrise.xdoc.entity.crm.Size;

@Component("sizeService")
@Transactional
public class SizeServiceImpl implements ISizeService {
  @Autowired
  private SizeRepository sizeRepository;

  @Override
  public void saveOrUpdate(SizeDto dto) {
    Size size = null;
    if (dto.getId() != null) {
      size = sizeRepository.findOne(dto.getId());
    }
    if (size == null) {
      size = new Size();
    }
    size.setId(dto.getId());
    size.setCode(dto.getCode());
    size.setActive(dto.isActive());
    size.setName(dto.getName());
    sizeRepository.save(size);
  }

  @Override
  public long count() {
    return sizeRepository.count();
  }

  @Override
  public SizeDto findOne(Long id) {
    if (sizeRepository.exists(id)) {
      Size Size = sizeRepository.findOne(id);
      return transformToDto(Size);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return sizeRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long customerId : ids) {
      if (sizeRepository.exists(customerId)) {
        Size Size = sizeRepository.findOne(customerId);
        Size.setActive(false);
        sizeRepository.save(Size);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long areaId : ids) {
      Size Size = sizeRepository.findOne(areaId);
      Size.setActive(true);
      sizeRepository.save(Size);
    }

  }

  protected SizeDto transformToDto(Size size) {
    SizeDto sizeDto = new SizeDto();
    sizeDto.setId(size.getId());
    sizeDto.setCode(size.getCode());
    sizeDto.setActive(size.isActive());
    sizeDto.setName(size.getName());
    return sizeDto;
  }

  @Override
  public long countByActive() {
    return sizeRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return sizeRepository.countByActive(false);
  }

  @Override
  public List<SizeDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(sizeRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(Size -> transformToDto(Size)).collect(Collectors.toList());
  }

  @Override
  public List<SizeDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(sizeRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(Size -> transformToDto(Size)).collect(Collectors.toList());
  }

  @Override
  public List<SizeDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(sizeRepository.findAll(pageRequest).spliterator(), false)
            .map(Size -> transformToDto(Size)).collect(Collectors.toList());
  }
}
