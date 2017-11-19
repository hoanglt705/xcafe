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

import com.s3s.ssm.dto.ShiftDto;
import com.s3s.ssm.repo.ShiftRepository;
import com.sunrise.xdoc.entity.employee.Shift;

@Component("shiftService")
@Transactional
public class ShiftServiceImpl implements IShiftService {
  @Autowired
  private ShiftRepository shiftRepository;

  @Override
  public void saveOrUpdate(ShiftDto dto) {
    Shift shift = null;
    if (dto.getId() != null) {
      shift = shiftRepository.findOne(dto.getId());
    }
    if (shift == null) {
      shift = new Shift();
    }
    shift.setCode(dto.getCode());
    shift.setName(dto.getName());
    shift.setStartTime(dto.getStartTime());
    shift.setEndTime(dto.getEndTime());
    shiftRepository.save(shift);
  }

  @Override
  public long count() {
    return shiftRepository.count();
  }

  @Override
  public ShiftDto findOne(Long id) {
    if (shiftRepository.exists(id)) {
      Shift area = shiftRepository.findOne(id);
      return transformToDto(area);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return shiftRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long areaId : ids) {
      if (shiftRepository.exists(areaId)) {
        Shift area = shiftRepository.findOne(areaId);
        area.setActive(false);
        shiftRepository.save(area);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long areaId : ids) {
      Shift area = shiftRepository.findOne(areaId);
      area.setActive(true);
      shiftRepository.save(area);
    }

  }

  public static ShiftDto transformToDto(Shift shift) {
    ShiftDto dto = new ShiftDto();
    dto.setId(shift.getId());
    dto.setCode(shift.getCode());
    dto.setName(shift.getName());
    dto.setActive(shift.isActive());
    dto.setStartTime(shift.getStartTime());
    dto.setEndTime(shift.getEndTime());
    return dto;
  }

  @Override
  public long countByActive() {
    return shiftRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return shiftRepository.countByActive(false);
  }

  @Override
  public List<ShiftDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(shiftRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(shift -> transformToDto(shift)).collect(Collectors.toList());
  }

  @Override
  public List<ShiftDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(shiftRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(area -> transformToDto(area)).collect(Collectors.toList());
  }

  @Override
  public List<ShiftDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(shiftRepository.findAll(pageRequest).spliterator(), false)
            .map(area -> transformToDto(area)).collect(Collectors.toList());
  }
}
