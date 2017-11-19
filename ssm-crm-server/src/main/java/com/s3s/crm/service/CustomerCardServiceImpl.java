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

import com.s3s.crm.dto.CustomerCardDto;
import com.s3s.crm.dto.CustomerDto;
import com.s3s.crm.repo.CustomerCardRepository;
import com.s3s.crm.repo.CustomerRepository;
import com.sunrise.xdoc.entity.crm.Customer;
import com.sunrise.xdoc.entity.crm.CustomerCard;

@Component("customerCardService")
@Transactional
public class CustomerCardServiceImpl implements ICustomerCardService {
  @Autowired
  private CustomerCardRepository customerCardRepository;
  @Autowired
  private CustomerRepository customerRepository;

  @Override
  public void saveOrUpdate(CustomerCardDto dto) {
    CustomerCard customerCard = null;
    if (dto.getId() != null) {
      customerCard = customerCardRepository.findOne(dto.getId());
    }
    if (customerCard == null) {
      customerCard = new CustomerCard();
    }
    BeanUtils.copyProperties(dto, customerCard);
    CustomerDto customerDto = dto.getCustomer();
    if (customerDto != null) {
      customerCard.setCustomer(customerRepository.findByCode(customerDto.getCode()));
    }
    customerCardRepository.save(customerCard);
  }

  @Override
  public long count() {
    return customerCardRepository.count();
  }

  @Override
  public CustomerCardDto findOne(Long id) {
    if (customerCardRepository.exists(id)) {
      CustomerCard CustomerCard = customerCardRepository.findOne(id);
      return transformToDto(CustomerCard);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return customerCardRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long customerId : ids) {
      if (customerCardRepository.exists(customerId)) {
        CustomerCard CustomerCard = customerCardRepository.findOne(customerId);
        CustomerCard.setActive(false);
        customerCardRepository.save(CustomerCard);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long areaId : ids) {
      CustomerCard CustomerCard = customerCardRepository.findOne(areaId);
      CustomerCard.setActive(true);
      customerCardRepository.save(CustomerCard);
    }

  }

  protected CustomerCardDto transformToDto(CustomerCard customerCard) {
    CustomerCardDto customerCardDto = new CustomerCardDto();
    BeanUtils.copyProperties(customerCard, customerCardDto);
    Customer customer = customerCard.getCustomer();
    if (customer != null) {
      BeanUtils.copyProperties(customer, customerCardDto.getCustomer());
    }
    return customerCardDto;
  }

  @Override
  public long countByActive() {
    return customerCardRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return customerCardRepository.countByActive(false);
  }

  @Override
  public List<CustomerCardDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(customerCardRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(CustomerCard -> transformToDto(CustomerCard)).collect(Collectors.toList());
  }

  @Override
  public List<CustomerCardDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(customerCardRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(CustomerCard -> transformToDto(CustomerCard)).collect(Collectors.toList());
  }

  @Override
  public List<CustomerCardDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(customerCardRepository.findAll(pageRequest).spliterator(), false)
            .map(CustomerCard -> transformToDto(CustomerCard)).collect(Collectors.toList());
  }
}
