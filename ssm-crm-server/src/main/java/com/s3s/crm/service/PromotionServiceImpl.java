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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.s3s.crm.dto.ConfigDto;
import com.s3s.crm.dto.CustomerDto;
import com.s3s.crm.dto.PromotionDto;
import com.s3s.crm.repo.PromotionRepository;
import com.sunrise.xdoc.entity.crm.Promotion;
import com.sunrise.xdoc.entity.crm.QPromotion;

@Component("promotionService")
@Transactional
public class PromotionServiceImpl implements IPromotionService {
  @Autowired
  private PromotionRepository promotionRepository;
  @PersistenceContext
  protected EntityManager entityManager;
  @Autowired
  private IConfigService configService;
  @Autowired
  private ICustomerService customerService;

  @Override
  public void saveOrUpdate(PromotionDto dto) {
    Promotion promotion = null;
    if (dto.getId() != null) {
      promotion = promotionRepository.findOne(dto.getId());
    }
    if (promotion == null) {
      promotion = new Promotion();
    }
    BeanUtils.copyProperties(dto, promotion);
    promotionRepository.save(promotion);
    sendMailToCustomer(dto);
  }

  private void sendMailToCustomer(PromotionDto dto) {
    HtmlEmail email = new HtmlEmail();
    ConfigDto configDto = configService.getConfig();
    email.setSubject(dto.getTitle());
    email.setHostName(configDto.getEmailHostName());
    email.setSmtpPort(configDto.getSmtpPort());
    email.setAuthenticator(new DefaultAuthenticator(configDto.getEmailUsername(), configDto
            .getEmailPassword()));
    email.setSSLOnConnect(true);
    try {
      email.setHtmlMsg(dto.getContent());
      email.setFrom(configDto.getEmailUsername(), configDto.getName());
      List<CustomerDto> customerDtos = customerService.findByActive(0, Integer.MAX_VALUE);
      for (CustomerDto customerDto : customerDtos) {
        email.addTo(customerDto.getEmail(), customerDto.getName());
      }
      email.send();
    } catch (EmailException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Email email = new SimpleEmail();
    email.setHostName("smtp.gmail.com");
    email.setSmtpPort(465);
    email.setAuthenticator(new DefaultAuthenticator("hoanglt705@gmail.com", "Abc0974656005"));
    email.setSSLOnConnect(true);
    try {
      email.setFrom("hoanglt705@gmail.com");
      email.setSubject("TestMail");
      email.setMsg("This is a test mail ... :-)");
      email.addTo("hoanglt705@gmail.com");
      email.send();
    } catch (EmailException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public long count() {
    return promotionRepository.count();
  }

  @Override
  public PromotionDto findOne(Long id) {
    if (promotionRepository.exists(id)) {
      Promotion promotion = promotionRepository.findOne(id);
      return transformToDto(promotion);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return promotionRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long areaId : ids) {
      if (promotionRepository.exists(areaId)) {
        Promotion promotion = promotionRepository.findOne(areaId);
        promotion.setActive(false);
        promotionRepository.save(promotion);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long areaId : ids) {
      Promotion promotion = promotionRepository.findOne(areaId);
      promotion.setActive(true);
      promotionRepository.save(promotion);
    }

  }

  protected PromotionDto transformToDto(Promotion promotion) {
    PromotionDto promotionDto = new PromotionDto();
    BeanUtils.copyProperties(promotion, promotionDto);
    return promotionDto;
  }

  @Override
  public long countByActive() {
    return promotionRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return promotionRepository.countByActive(false);
  }

  @Override
  public List<PromotionDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(promotionRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(promotion -> transformToDto(promotion)).collect(Collectors.toList());
  }

  @Override
  public List<PromotionDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(promotionRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(promotion -> transformToDto(promotion)).collect(Collectors.toList());
  }

  @Override
  public List<PromotionDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(promotionRepository.findAll(pageRequest).spliterator(), false)
            .map(promotion -> transformToDto(promotion)).collect(Collectors.toList());
  }

  @Override
  public PromotionDto findPromotion(Date date) {
    QPromotion qPromotion = QPromotion.promotion;
    BooleanExpression express = qPromotion.fromDate.loe(date);
    express = express.and(qPromotion.toDate.goe(date));
    Promotion promotion = new JPAQuery(entityManager).from(qPromotion).where(express)
            .singleResult(qPromotion);
    return transformToDto(promotion);
  }
}
