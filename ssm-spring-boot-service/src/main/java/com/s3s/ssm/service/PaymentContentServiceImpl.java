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

import com.s3s.ssm.dto.PaymentContentDto;
import com.s3s.ssm.repo.PaymentContentRepository;
import com.s3s.ssm.repo.PaymentRepository;
import com.sunrise.xdoc.entity.finance.PaymentContent;

@Component("paymentContentService")
@Transactional
public class PaymentContentServiceImpl implements IPaymentContentService {
  @Autowired
  private PaymentContentRepository paymentContentRepository;
  @Autowired
  private PaymentRepository paymentRepository;

  @Override
  public void saveOrUpdate(PaymentContentDto dto) {
    PaymentContent paymentContent = null;
    if (dto.getId() != null) {
      paymentContent = paymentContentRepository.findOne(dto.getId());
    }
    if (paymentContent == null) {
      paymentContent = new PaymentContent();
    }
    paymentContent.setCode(dto.getCode());
    paymentContent.setName(dto.getName());
    paymentContent.setActive(dto.isActive());
    paymentContent.setPaymentType(dto.getPaymentType());
    PaymentContentDto parentDto = dto.getParent();
    if (parentDto == null) {
      paymentContent.setParent(null);
    } else {
      paymentContent.setParent(paymentContentRepository.findByCode(parentDto.getCode()));
    }
    paymentContentRepository.save(paymentContent);
  }

  @Override
  public long count() {
    return paymentContentRepository.count();
  }

  @Override
  public PaymentContentDto findOne(Long id) {
    if (paymentContentRepository.exists(id)) {
      PaymentContent paymentContent = paymentContentRepository.findOne(id);
      return transformToDto(paymentContent);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return paymentContentRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long areaId : ids) {
      if (paymentContentRepository.exists(areaId)) {
        PaymentContent paymentContent = paymentContentRepository.findOne(areaId);
        paymentContent.setActive(false);
        paymentContentRepository.save(paymentContent);

        List<PaymentContent> children = paymentContentRepository.findByParent(paymentContent);
        for (PaymentContent child : children) {
          child.setActive(false);
          paymentContentRepository.save(child);
        }
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long id : ids) {
      if (paymentContentRepository.exists(id)) {
        PaymentContent paymentContent = paymentContentRepository.findOne(id);
        paymentContent.setActive(true);
        paymentContentRepository.save(paymentContent);

        List<PaymentContent> children = paymentContentRepository.findByParent(paymentContent);
        for (PaymentContent child : children) {
          child.setActive(true);
          paymentContentRepository.save(child);
        }
      }
    }

  }

  protected PaymentContentDto transformToDto(PaymentContent paymentContent) {
    PaymentContentDto dto = new PaymentContentDto();
    dto.setId(paymentContent.getId());
    dto.setCode(paymentContent.getCode());
    dto.setName(paymentContent.getName());
    dto.setActive(paymentContent.isActive());
    dto.setPaymentType(paymentContent.getPaymentType());
    PaymentContentDto parentDto = new PaymentContentDto();
    PaymentContent parent = paymentContent.getParent();
    if (parent != null) {
      parentDto.setCode(parent.getCode());
      parentDto.setName(parent.getName());
    }
    dto.setParent(parentDto);
    return dto;
  }

  @Override
  public long countByActive() {
    return paymentContentRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return paymentContentRepository.countByActive(false);
  }

  @Override
  public List<PaymentContentDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport
            .stream(paymentContentRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(area -> transformToDto(area)).collect(Collectors.toList());
  }

  @Override
  public List<PaymentContentDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport
            .stream(paymentContentRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(area -> transformToDto(area)).collect(Collectors.toList());
  }

  @Override
  public List<PaymentContentDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(paymentContentRepository.findAll(pageRequest).spliterator(), false)
            .map(area -> transformToDto(area)).collect(Collectors.toList());
  }
}
