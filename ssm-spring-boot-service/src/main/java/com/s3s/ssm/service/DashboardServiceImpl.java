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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.s3s.ssm.dto.InvoiceStatus;
import com.s3s.ssm.dto.LatestInvoiceDto;
import com.s3s.ssm.dto.PaymentContentDto;
import com.s3s.ssm.dto.PaymentDto;
import com.sunrise.xdoc.entity.finance.QPayment;
import com.sunrise.xdoc.entity.finance.QPaymentContent;
import com.sunrise.xdoc.entity.sale.QInvoice;

@Component("dashboardService")
@Transactional
class DashboardServiceImpl implements IDashboardService {
  @PersistenceContext
  private EntityManager entityManager;
  @Autowired
  private IInvoiceService invoiceService;
  @Autowired
  private IFoodTableService foodTableService;

  @Override
  public List<PaymentDto> getLatestPayment(int size) {
    QPayment qPayment = QPayment.payment;
    QPaymentContent qPaymentContent = QPaymentContent.paymentContent;

    List<Tuple> list = new JPAQuery(entityManager).from(qPayment)
            .leftJoin(qPayment.paymentContent, qPaymentContent)
            .where(qPayment.active.eq(true)).limit(size)
            .orderBy(new OrderSpecifier<>(Order.DESC, qPayment.paymentDate))
            .list(qPayment.code, qPayment.paymentDate, qPaymentContent.name, qPayment.amount);

    List<PaymentDto> result = new ArrayList<>();
    for (Tuple tuple : list) {
      PaymentDto dto = new PaymentDto();
      dto.setCode(tuple.get(qPayment.code));
      PaymentContentDto paymentContentDto = new PaymentContentDto();
      paymentContentDto.setName(tuple.get(qPaymentContent.name));
      dto.setPaymentContent(paymentContentDto);
      dto.setPaymentDate(tuple.get(qPayment.paymentDate));
      dto.setAmount(tuple.get(qPayment.amount));
      result.add(dto);
    }

    return result;
  }

  @Override
  public long[] getOperatingTable() {
    long[] tableCount = new long[3];
    long waitingCount = invoiceService.countInvoice(InvoiceStatus.BOOKING);
    long servingCount = invoiceService.countInvoice(InvoiceStatus.SERVING);
    long existingCount = foodTableService.countByActive();

    tableCount[0] = waitingCount;
    tableCount[1] = servingCount;
    tableCount[2] = existingCount - (waitingCount + servingCount);
    return tableCount;
  }

  @Override
  public List<LatestInvoiceDto> getLatestInvoice(int size) {
    if (size < 1) {
      return Collections.emptyList();
    }
    QInvoice qInvoice = QInvoice.invoice;

    List<Tuple> list = new JPAQuery(entityManager).from(qInvoice).limit(size)
            .orderBy(new OrderSpecifier<>(Order.DESC, qInvoice.createdDate))
            .where(qInvoice.invoiceStatus.eq(InvoiceStatus.PAID)).list(qInvoice.code,
                    qInvoice.foodTable, qInvoice.createdDate, qInvoice.totalAmount, qInvoice.income);

    List<LatestInvoiceDto> result = new ArrayList<>();
    for (Tuple tuple : list) {
      LatestInvoiceDto dto = new LatestInvoiceDto();
      dto.setCode(tuple.get(qInvoice.code));
      dto.setCreatedDate(tuple.get(qInvoice.createdDate));
      dto.setFoodTable(tuple.get(qInvoice.foodTable).getName());
      dto.setAmount(tuple.get(qInvoice.totalAmount));
      dto.setIncome(tuple.get(qInvoice.income));
      result.add(dto);
    }
    return result;
  }
}
