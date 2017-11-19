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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.s3s.ssm.dto.EmployeeDto;
import com.s3s.ssm.dto.PaymentContentDto;
import com.s3s.ssm.dto.PaymentDto;
import com.s3s.ssm.dto.PaymentType;
import com.s3s.ssm.repo.EmployeeRepository;
import com.s3s.ssm.repo.PaymentContentRepository;
import com.s3s.ssm.repo.PaymentRepository;
import com.sunrise.xdoc.entity.employee.Employee;
import com.sunrise.xdoc.entity.finance.Payment;
import com.sunrise.xdoc.entity.finance.PaymentContent;
import com.sunrise.xdoc.entity.finance.QPayment;

@Component("paymentService")
class PaymentServiceImpl implements IPaymentService {
  @PersistenceContext
  protected EntityManager entityManager;
  @Autowired
  protected PaymentRepository paymentRepository;
  @Autowired
  private PaymentContentRepository paymentContentRepository;
  @Autowired
  private EmployeeRepository employeeRepository;

  @Override
  public void saveOrUpdate(PaymentDto dto) {
    Payment payment = null;
    if (dto.getId() != null) {
      payment = paymentRepository.findOne(dto.getId());
    }
    if (payment == null) {
      payment = new Payment();
    }
    payment.setCode(dto.getCode());

    payment.setId(dto.getId());
    payment.setCode(dto.getCode());
    payment.setActive(dto.isActive());
    PaymentContentDto paymentContentDto = dto.getPaymentContent();
    if (paymentContentDto == null) {
      payment.setPaymentContent(null);
    } else {
      PaymentContent paymentContent = paymentContentRepository.findByCode(paymentContentDto.getCode());
      payment.setPaymentContent(paymentContent);
    }

    payment.setPaymentDate(dto.getPaymentDate());

    EmployeeDto staffDto = dto.getStaff();
    if (staffDto == null) {
      payment.setStaff(null);
    } else {
      payment.setStaff(employeeRepository.findByCode(staffDto.getCode()));
    }

    payment.setPaymentMode(payment.getPaymentMode());
    payment.setAmount(payment.getAmount());
    payment.setNotes(payment.getNotes());

    paymentRepository.save(payment);
  }

  @Override
  public long count() {
    QPayment qPayment = QPayment.payment;
    return new JPAQuery(entityManager).from(qPayment)
            .where(qPayment.paymentContent.paymentType.eq(PaymentType.PAY)).count();
  }

  @Override
  public PaymentDto findOne(Long id) {
    if (paymentRepository.exists(id)) {
      Payment payment = paymentRepository.findOne(id);
      return transformToDto(payment);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return paymentRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long paymentId : ids) {
      if (paymentRepository.exists(paymentId)) {
        Payment payment = paymentRepository.findOne(paymentId);
        payment.setActive(false);
        paymentRepository.save(payment);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long paymentId : ids) {
      Payment payment = paymentRepository.findOne(paymentId);
      payment.setActive(true);
      paymentRepository.save(payment);
    }
  }

  protected PaymentDto transformToDto(Payment payment) {
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setId(payment.getId());
    paymentDto.setCode(payment.getCode());
    paymentDto.setActive(payment.isActive());

    PaymentContentDto paymentContentDto = new PaymentContentDto();
    PaymentContent paymentContent = payment.getPaymentContent();
    if (paymentContent != null) {
      paymentContentDto.setCode(paymentContent.getCode());
      paymentContentDto.setName(paymentContent.getName());
    }
    paymentDto.setPaymentContent(paymentContentDto);
    paymentDto.setPaymentDate(payment.getPaymentDate());

    EmployeeDto staffDto = new EmployeeDto();
    Employee staff = payment.getStaff();
    staffDto.setId(staff.getId());
    staffDto.setCode(staff.getCode());
    staffDto.setName(staff.getName());
    paymentDto.setStaff(staffDto);

    paymentDto.setPaymentMode(payment.getPaymentMode());
    paymentDto.setAmount(payment.getAmount());
    paymentDto.setNotes(payment.getNotes());
    return paymentDto;
  }

  @Override
  public long countByActive() {
    QPayment qPayment = QPayment.payment;
    BooleanExpression express = qPayment.active.eq(true);
    express = express.and(qPayment.paymentContent.paymentType.eq(PaymentType.PAY));
    return new JPAQuery(entityManager).from(qPayment).where(express).count();
  }

  @Override
  public long countByInActive() {
    QPayment qPayment = QPayment.payment;
    BooleanExpression express = qPayment.active.eq(false);
    express = express.and(qPayment.paymentContent.paymentType.eq(PaymentType.PAY));
    return new JPAQuery(entityManager).from(qPayment).where(express).count();
  }

  @Override
  public List<PaymentDto> findByActive(int page, int size) {
    QPayment qPayment = QPayment.payment;
    BooleanExpression express = qPayment.active.eq(true);
    express = express.and(qPayment.paymentContent.paymentType.eq(PaymentType.PAY));
    List<Payment> payments = new JPAQuery(entityManager).from(qPayment).where(express).limit(size)
            .offset(size * page)
            .list(qPayment);
    return StreamSupport.stream(payments.spliterator(), false).map(payment -> transformToDto(payment))
            .collect(Collectors.toList());
  }

  @Override
  public List<PaymentDto> findByInactive(int page, int size) {
    QPayment qPayment = QPayment.payment;
    BooleanExpression express = qPayment.active.eq(false);
    express = express.and(qPayment.paymentContent.paymentType.eq(PaymentType.PAY));
    List<Payment> payments = new JPAQuery(entityManager).from(qPayment).where(express).limit(size)
            .offset(size * page)
            .list(qPayment);
    return StreamSupport.stream(payments.spliterator(), false).map(payment -> transformToDto(payment))
            .collect(Collectors.toList());
  }

  @Override
  public List<PaymentDto> findAll(int page, int size) {
    QPayment qPayment = QPayment.payment;
    BooleanExpression express = qPayment.paymentContent.paymentType.eq(PaymentType.PAY);
    List<Payment> payments = new JPAQuery(entityManager).from(qPayment).where(express).limit(size)
            .offset(size * page)
            .list(qPayment);
    return StreamSupport.stream(payments.spliterator(), false).map(payment -> transformToDto(payment))
            .collect(Collectors.toList());
  }

  @Override
  public List<PaymentDto> getLatestPayment(int size) {
    PageRequest pageRequest = new PageRequest(0, size);
    return StreamSupport.stream(paymentRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(payment -> transformToDto(payment)).collect(Collectors.toList());
  }
}
