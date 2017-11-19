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

package com.s3s.ssm.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dto.AreaDto;
import com.s3s.ssm.repo.AreaRepository;
import com.s3s.ssm.repo.FoodTableRepository;
import com.sunrise.xdoc.entity.config.Area;
import com.sunrise.xdoc.entity.config.FoodTable;

@Component("areaService")
@Transactional
public class AreaServiceImpl implements IAreaService {
  @Autowired
  private AreaRepository areaRepository;
  @Autowired
  private FoodTableRepository foodTableRepository;

  @Override
  // @CacheEvict(value = "areaCache")
  public void saveOrUpdate(AreaDto dto) {
    Area area = null;
    if (dto.getId() != null) {
      area = areaRepository.findOne(dto.getId());
    }
    if (area == null) {
      area = new Area();
    }
    area.setCode(dto.getCode());
    area.setName(dto.getName());

    areaRepository.save(area);
  }

  @Override
  public long count() {
    return areaRepository.count();
  }

  @Override
  public AreaDto findOne(Long id) {
    if (areaRepository.exists(id)) {
      Area area = areaRepository.findOne(id);
      return transformToDto(area);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return areaRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long areaId : ids) {
      if (areaRepository.exists(areaId)) {
        Area area = areaRepository.findOne(areaId);
        area.setActive(false);
        areaRepository.save(area);
        Iterable<FoodTable> foodTables = foodTableRepository.findByArea(area);
        for (FoodTable foodTable : foodTables) {
          foodTable.setActive(false);
        }

        foodTableRepository.save(foodTables);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long areaId : ids) {
      Area area = areaRepository.findOne(areaId);
      area.setActive(true);
      areaRepository.save(area);
      Iterable<FoodTable> foodTables = foodTableRepository.findByArea(area);
      for (FoodTable foodTable : foodTables) {
        foodTable.setActive(true);
      }
      foodTableRepository.save(foodTables);
    }

  }

  protected AreaDto transformToDto(Area area) {
    AreaDto areaDto = new AreaDto();
    areaDto.setId(area.getId());
    areaDto.setCode(area.getCode());
    areaDto.setName(area.getName());
    areaDto.setActive(area.isActive());
    return areaDto;
  }

  @Override
  public long countByActive() {
    return areaRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return areaRepository.countByActive(false);
  }

  @Override
  public List<AreaDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(areaRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(area -> transformToDto(area)).collect(Collectors.toList());
  }

  @Override
  public List<AreaDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(areaRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(area -> transformToDto(area)).collect(Collectors.toList());
  }

  @Override
  public List<AreaDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(areaRepository.findAll(pageRequest).spliterator(), false)
            .map(area -> transformToDto(area)).collect(Collectors.toList());
  }
}
