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

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.crm.dto.CustomerDto;
import com.s3s.crm.dto.CustomerTypeDto;
import com.s3s.crm.dto.LuckyCustomerDto;
import com.s3s.crm.dto.PotentialCustomerDto;
import com.s3s.crm.repo.CustomerRepository;
import com.s3s.crm.repo.CustomerTypeRepository;
import com.sunrise.xdoc.entity.crm.Customer;
import com.sunrise.xdoc.entity.crm.CustomerType;

@Component("customerService")
@Transactional
public class CustomerServiceImpl implements ICustomerService {
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private CustomerTypeRepository customerTypeRepository;
  @Autowired
  private IConfigService configService;

  @Override
  public void saveOrUpdate(CustomerDto dto) {
    Customer customer = null;
    if (dto.getId() != null) {
      customer = customerRepository.findOne(dto.getId());
    }
    if (customer == null) {
      customer = new Customer();
    }
    BeanUtils.copyProperties(dto, customer);
    CustomerTypeDto customerTypeDto = dto.getCustomerType();
    if (customerTypeDto != null) {
      CustomerType customerType = customerTypeRepository.findByCode(customerTypeDto.getCode());
      customer.setCustomerType(customerType);
    }
    customerRepository.save(customer);
  }

  @Override
  public long count() {
    return customerRepository.count();
  }

  @Override
  public CustomerDto findOne(Long id) {
    if (customerRepository.exists(id)) {
      Customer Customer = customerRepository.findOne(id);
      return transformToDto(Customer);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return customerRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long customerId : ids) {
      if (customerRepository.exists(customerId)) {
        Customer customer = customerRepository.findOne(customerId);
        customer.setActive(false);
        customerRepository.save(customer);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long areaId : ids) {
      Customer customer = customerRepository.findOne(areaId);
      customer.setActive(true);
      customerRepository.save(customer);
    }

  }

  protected CustomerDto transformToDto(Customer customer) {
    CustomerDto customerDto = new CustomerDto();
    BeanUtils.copyProperties(customer, customerDto, "customerType");
    if (customer.getCustomerType() != null) {
      CustomerTypeDto customerTypeDto = customerDto.getCustomerType();
      BeanUtils.copyProperties(customer.getCustomerType(), customerTypeDto);
      CustomerType nextCustomerType = customer.getCustomerType().getNextCustomerType();
      if (nextCustomerType != null) {
        CustomerTypeDto nextCustomerTypeDto = new CustomerTypeDto();
        BeanUtils.copyProperties(nextCustomerType, nextCustomerTypeDto);
        customerTypeDto.setNextCustomerType(nextCustomerTypeDto);
      }
    }
    return customerDto;
  }

  @Override
  public long countByActive() {
    return customerRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return customerRepository.countByActive(false);
  }

  @Override
  public List<CustomerDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(customerRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(Customer -> transformToDto(Customer)).collect(Collectors.toList());
  }

  @Override
  public List<CustomerDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(customerRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(Customer -> transformToDto(Customer)).collect(Collectors.toList());
  }

  @Override
  public List<CustomerDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(customerRepository.findAll(pageRequest).spliterator(), false)
            .map(Customer -> transformToDto(Customer)).collect(Collectors.toList());
  }

  @Override
  public List<LuckyCustomerDto> findLuckyCustomerDto() {
    List<LuckyCustomerDto> result = new ArrayList<>();
    int warningBirthdayBerore = configService.getConfig().getWarningBirthdayBerore();
    List<CustomerDto> customers = findByActive(0, Integer.MAX_VALUE);
    customers.forEach(customerDto -> {
      LocalDate today = LocalDate.now();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(customerDto.getBirthday());
      int day = calendar.get(Calendar.DAY_OF_MONTH);
      int month = calendar.get(Calendar.MONTH) + 1;
      LocalDate nextBirthday = LocalDate.of(today.getYear(), month, day);
      if (!nextBirthday.isBefore(today)) {
        Period p = Period.between(today, nextBirthday);
        if (p.getDays() <= warningBirthdayBerore) {
          LuckyCustomerDto luckyCustomerDto = new LuckyCustomerDto();
          BeanUtils.copyProperties(customerDto, luckyCustomerDto);
          luckyCustomerDto.setCustomerName(customerDto.getName());
          luckyCustomerDto.setCustomerType(customerDto.getCustomerType().getName());
          result.add(luckyCustomerDto);
        }
      }
    });
    return result;
  }

  @Override
  public List<PotentialCustomerDto> findPotentialCustomerDto() {
    List<PotentialCustomerDto> result = new ArrayList<>();
    List<CustomerDto> customers = findByActive(0, Integer.MAX_VALUE);
    customers.forEach(customerDto -> {
      CustomerTypeDto customerTypeDto = customerDto.getCustomerType();
      if (customerTypeDto != null) {
        CustomerTypeDto nextCustomerTypeDto = customerTypeDto.getNextCustomerType();
        if (nextCustomerTypeDto != null && customerDto.getPoint() >= nextCustomerTypeDto.getPointToRemind()) {
          PotentialCustomerDto potentialCustomerDto = new PotentialCustomerDto();
          potentialCustomerDto.setCustomerName(customerDto.getName());
          potentialCustomerDto.setCustomerType(customerTypeDto.getName());
          potentialCustomerDto.setNextCustomerType(nextCustomerTypeDto.getName());
          potentialCustomerDto.setTargetPoint(nextCustomerTypeDto.getMinPoint());
          potentialCustomerDto.setCurrentPoint(customerDto.getPoint());
          result.add(potentialCustomerDto);
        }
      }

    });
    return result;
  }
}
