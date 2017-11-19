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

import com.s3s.ssm.dto.RoleDto;
import com.s3s.ssm.repo.RoleRepository;
import com.sunrise.xdoc.entity.employee.Role;

@Component("roleService")
@Transactional
public class RoleServiceImpl implements IRoleService {
  @Autowired
  private RoleRepository roleRepository;

  @Override
  public void saveOrUpdate(RoleDto dto) {
    Role role = null;
    if (dto.getId() != null) {
      role = roleRepository.findOne(dto.getId());
    }
    if (role == null) {
      role = new Role();
    }
    role.setCode(dto.getCode());
    role.setName(dto.getName());

    roleRepository.save(role);
  }

  @Override
  public long count() {
    return roleRepository.count();
  }

  @Override
  public RoleDto findOne(Long id) {
    if (roleRepository.exists(id)) {
      Role role = roleRepository.findOne(id);
      return transformToDto(role);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return roleRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long roleId : ids) {
      if (roleRepository.exists(roleId)) {
        Role role = roleRepository.findOne(roleId);
        role.setActive(false);
        roleRepository.save(role);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long roleId : ids) {
      Role role = roleRepository.findOne(roleId);
      role.setActive(true);
      roleRepository.save(role);
    }

  }

  public static RoleDto transformToDto(Role role) {
    RoleDto roleDto = new RoleDto();
    roleDto.setId(role.getId());
    roleDto.setCode(role.getCode());
    roleDto.setName(role.getName());
    roleDto.setActive(role.isActive());
    return roleDto;
  }

  @Override
  public long countByActive() {
    return roleRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return roleRepository.countByActive(false);
  }

  @Override
  public List<RoleDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(roleRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(role -> transformToDto(role)).collect(Collectors.toList());
  }

  @Override
  public List<RoleDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(roleRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(role -> transformToDto(role)).collect(Collectors.toList());
  }

  @Override
  public List<RoleDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(roleRepository.findAll(pageRequest).spliterator(), false)
            .map(role -> transformToDto(role)).collect(Collectors.toList());
  }
}
