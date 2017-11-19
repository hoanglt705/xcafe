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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.s3s.ssm.dto.CompanyDto;
import com.s3s.ssm.repo.CompanyRepository;
import com.sunrise.xdoc.entity.config.Company;

@Component("companyService")
@Transactional
class CompanyServiceImpl implements ICompanyService {
  @Autowired
  private CompanyRepository companyRepo;

  @Override
  public CompanyDto getCompany() {
    Company company = Lists.newArrayList(companyRepo.findAll()).get(0);
    if (company != null) {
      CompanyDto companyDto = new CompanyDto();
      BeanUtils.copyProperties(company, companyDto);
      return companyDto;
    }
    return null;
  }

  @Override
  public void inactivate(long[] ids) {
  }

  @Override
  public void activate(long[] ids) {
  }

  @Override
  public void saveOrUpdate(CompanyDto dto) {
    Company company = null;
    if (dto.getId() != null) {
      company = companyRepo.findOne(dto.getId());
    }
    if (company == null) {
      company = new Company();
    }
    BeanUtils.copyProperties(dto, company);
    companyRepo.save(company);
  }

  private CompanyDto transformToDto(Company company) {
    CompanyDto dto = new CompanyDto();
    BeanUtils.copyProperties(company, dto);
    return dto;
  }

  @Override
  public long count() {
    return companyRepo.count();
  }

  @Override
  public long countByActive() {
    return companyRepo.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return companyRepo.countByActive(false);
  }

  @Override
  public CompanyDto findOne(Long id) {
    if (companyRepo.exists(id)) {
      Company company = companyRepo.findOne(id);
      return transformToDto(company);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return companyRepo.findByCode(code) != null;
  }

  @Override
  public List<CompanyDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(companyRepo.findByActive(true, pageRequest).spliterator(), false)
            .map(company -> transformToDto(company)).collect(Collectors.toList());
  }

  @Override
  public List<CompanyDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(companyRepo.findByActive(false, pageRequest).spliterator(), false)
            .map(company -> transformToDto(company)).collect(Collectors.toList());
  }

  @Override
  public List<CompanyDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(companyRepo.findAll(pageRequest).spliterator(), false)
            .map(area -> transformToDto(area)).collect(Collectors.toList());
  }
}
