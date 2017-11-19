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

import com.s3s.crm.dto.CustomerTypeDto;
import com.s3s.crm.repo.CustomerTypeRepository;
import com.s3s.crm.service.ICustomerTypeService;
import com.sunrise.xdoc.entity.crm.CustomerType;

@Component("customerTypeService")
@Transactional
public class CustomerTypeServiceImpl implements ICustomerTypeService {
  @Autowired
  private CustomerTypeRepository customerTypeRepository;

  @Override
  public void saveOrUpdate(CustomerTypeDto dto) {
    CustomerType customerType = null;
    if (dto.getId() != null) {
      customerType = customerTypeRepository.findOne(dto.getId());
    }
    if (customerType == null) {
      customerType = new CustomerType();
    }
    BeanUtils.copyProperties(dto, customerType, "nextCustomerType");
    BeanUtils.copyProperties(dto.getNextCustomerType(), customerType.getNextCustomerType());
    customerTypeRepository.save(customerType);
  }

  @Override
  public long count() {
    return customerTypeRepository.count();
  }

  @Override
  public CustomerTypeDto findOne(Long id) {
    if (customerTypeRepository.exists(id)) {
      CustomerType CustomerType = customerTypeRepository.findOne(id);
      return transformToDto(CustomerType);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return customerTypeRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long customerId : ids) {
      if (customerTypeRepository.exists(customerId)) {
        CustomerType CustomerType = customerTypeRepository.findOne(customerId);
        CustomerType.setActive(false);
        customerTypeRepository.save(CustomerType);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long areaId : ids) {
      CustomerType CustomerType = customerTypeRepository.findOne(areaId);
      CustomerType.setActive(true);
      customerTypeRepository.save(CustomerType);
    }

  }

  protected CustomerTypeDto transformToDto(CustomerType customerType) {
    CustomerTypeDto customerTypeDto = new CustomerTypeDto();
    BeanUtils.copyProperties(customerType, customerTypeDto, "nextCustomerType");
    CustomerType nextCustomerType = customerType.getNextCustomerType();
    if (nextCustomerType != null) {
      customerTypeDto.setNextCustomerType(new CustomerTypeDto());
      BeanUtils.copyProperties(nextCustomerType, customerTypeDto.getNextCustomerType());
    }
    return customerTypeDto;
  }

  @Override
  public long countByActive() {
    return customerTypeRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return customerTypeRepository.countByActive(false);
  }

  @Override
  public List<CustomerTypeDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(customerTypeRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(CustomerType -> transformToDto(CustomerType)).collect(Collectors.toList());
  }

  @Override
  public List<CustomerTypeDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(customerTypeRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(CustomerType -> transformToDto(CustomerType)).collect(Collectors.toList());
  }

  @Override
  public List<CustomerTypeDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(customerTypeRepository.findAll(pageRequest).spliterator(), false)
            .map(CustomerType -> transformToDto(CustomerType)).collect(Collectors.toList());
  }
}
