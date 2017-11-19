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

import org.springframework.stereotype.Component;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.s3s.ssm.dto.PaymentDto;
import com.s3s.ssm.dto.PaymentType;
import com.sunrise.xdoc.entity.finance.Payment;
import com.sunrise.xdoc.entity.finance.QPayment;

@Component("receiptService")
class ReceiptServiceImpl extends PaymentServiceImpl implements IReceiptService {

  @Override
  public long countByActive() {
    QPayment qPayment = QPayment.payment;
    BooleanExpression express = qPayment.active.eq(true);
    express = express.and(qPayment.paymentContent.paymentType.eq(PaymentType.RECEIPT));
    return new JPAQuery(entityManager).from(qPayment).where(express).count();
  }

  @Override
  public long count() {
    QPayment qPayment = QPayment.payment;
    return new JPAQuery(entityManager).from(qPayment)
            .where(qPayment.paymentContent.paymentType.eq(PaymentType.RECEIPT)).count();
  }

  @Override
  public long countByInActive() {
    QPayment qPayment = QPayment.payment;
    BooleanExpression express = qPayment.active.eq(false);
    express = express.and(qPayment.paymentContent.paymentType.eq(PaymentType.RECEIPT));
    return new JPAQuery(entityManager).from(qPayment).where(express).count();
  }

  @Override
  public List<PaymentDto> findByActive(int page, int size) {
    QPayment qPayment = QPayment.payment;
    BooleanExpression express = qPayment.active.eq(true);
    express = express.and(qPayment.paymentContent.paymentType.eq(PaymentType.RECEIPT));
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
    express = express.and(qPayment.paymentContent.paymentType.eq(PaymentType.RECEIPT));
    List<Payment> payments = new JPAQuery(entityManager).from(qPayment).where(express).limit(size)
            .offset(size * page)
            .list(qPayment);
    return StreamSupport.stream(payments.spliterator(), false).map(payment -> transformToDto(payment))
            .collect(Collectors.toList());
  }

  @Override
  public List<PaymentDto> findAll(int page, int size) {
    QPayment qPayment = QPayment.payment;
    BooleanExpression express = qPayment.paymentContent.paymentType.eq(PaymentType.RECEIPT);
    List<Payment> payments = new JPAQuery(entityManager).from(qPayment).where(express).limit(size)
            .offset(size * page)
            .list(qPayment);
    return StreamSupport.stream(payments.spliterator(), false).map(payment -> transformToDto(payment))
            .collect(Collectors.toList());
  }
}
