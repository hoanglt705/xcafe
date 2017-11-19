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

import com.s3s.crm.dto.ShapeDto;
import com.s3s.crm.repo.ShapeRepository;
import com.s3s.crm.service.IShapeService;
import com.sunrise.xdoc.entity.crm.Shape;

@Component("shapeService")
@Transactional
public class ShapeServiceImpl implements IShapeService {
  @Autowired
  private ShapeRepository shapeRepository;

  @Override
  public void saveOrUpdate(ShapeDto dto) {
    Shape shape = null;
    if (dto.getId() != null) {
      shape = shapeRepository.findOne(dto.getId());
    }
    if (shape == null) {
      shape = new Shape();
    }
    shape.setId(dto.getId());
    shape.setCode(dto.getCode());
    shape.setActive(dto.isActive());
    shape.setName(dto.getName());
    shapeRepository.save(shape);
  }

  @Override
  public long count() {
    return shapeRepository.count();
  }

  @Override
  public ShapeDto findOne(Long id) {
    if (shapeRepository.exists(id)) {
      Shape Shape = shapeRepository.findOne(id);
      return transformToDto(Shape);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return shapeRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long customerId : ids) {
      if (shapeRepository.exists(customerId)) {
        Shape Shape = shapeRepository.findOne(customerId);
        Shape.setActive(false);
        shapeRepository.save(Shape);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long areaId : ids) {
      Shape Shape = shapeRepository.findOne(areaId);
      Shape.setActive(true);
      shapeRepository.save(Shape);
    }

  }

  protected ShapeDto transformToDto(Shape shape) {
    ShapeDto customerCardDto = new ShapeDto();
    customerCardDto.setId(shape.getId());
    customerCardDto.setCode(shape.getCode());
    customerCardDto.setActive(shape.isActive());
    customerCardDto.setName(shape.getName());
    return customerCardDto;
  }

  @Override
  public long countByActive() {
    return shapeRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return shapeRepository.countByActive(false);
  }

  @Override
  public List<ShapeDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(shapeRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(Shape -> transformToDto(Shape)).collect(Collectors.toList());
  }

  @Override
  public List<ShapeDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(shapeRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(Shape -> transformToDto(Shape)).collect(Collectors.toList());
  }

  @Override
  public List<ShapeDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(shapeRepository.findAll(pageRequest).spliterator(), false)
            .map(Shape -> transformToDto(Shape)).collect(Collectors.toList());
  }
}
