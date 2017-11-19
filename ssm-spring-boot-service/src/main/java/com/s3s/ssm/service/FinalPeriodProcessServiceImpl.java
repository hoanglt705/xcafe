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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Projections;
import com.mysema.query.types.QBean;
import com.s3s.ssm.dto.FinalPeriodSaleProcessDto;
import com.s3s.ssm.dto.FinalPeriodTableProcessDto;
import com.s3s.ssm.repo.FoodTableRepository;
import com.sunrise.xdoc.entity.config.FoodTable;
import com.sunrise.xdoc.entity.store.QFinalPeriodSaleProcess;
import com.sunrise.xdoc.entity.store.QFinalPeriodTableProcess;

@Component("FinalPeriodService")
@Transactional
class FinalPeriodProcessServiceImpl implements IFinalPeriodProcessService {
  @PersistenceContext
  private EntityManager entityManager;
  @Autowired
  private FoodTableRepository foodTableRepository;
  @Autowired
  private IFoodTableService foodTableService;

  @Override
  public FinalPeriodSaleProcessDto findLatestFinalPeriodSaleProcess() {
    QFinalPeriodSaleProcess process = QFinalPeriodSaleProcess.finalPeriodSaleProcess;

    QBean<FinalPeriodSaleProcessDto> qBean = Projections.bean(FinalPeriodSaleProcessDto.class,
            process.processingDate,
            process.totalAmount,
            process.saleTotal,
            process.thisYearSaleTotal,
            process.invoiceTotal,
            process.thisYearInvoiceTotal
            );
    return new JPAQuery(entityManager).from(process)
            .orderBy(new OrderSpecifier<>(Order.DESC, process.processingDate))
            .singleResult(qBean);
  }

  @Override
  public FinalPeriodTableProcessDto findLatestFinalPeriodTableProcess(String foodTableCode) {
    FoodTable foodTable1 = foodTableRepository.findByCode(foodTableCode);

    if (foodTable1 == null) {
      return null;
    }

    QFinalPeriodTableProcess process = QFinalPeriodTableProcess.finalPeriodTableProcess;

    Tuple latestProcess = new JPAQuery(entityManager).from(process)
            .orderBy(new OrderSpecifier<>(Order.DESC, process.processingDate))
            .where(process.foodTable.eq(foodTable1))
            .singleResult(process.foodTable, process.processingDate, process.totalAmount);
    FinalPeriodTableProcessDto dto = new FinalPeriodTableProcessDto();

    if (latestProcess == null) {
      return null;
    }

    FoodTable foodTable = latestProcess.get(process.foodTable);
    if (foodTable != null) {
      dto.setFoodTableCode(foodTable.getCode());
    }
    dto.setProcessingDate(latestProcess.get(process.processingDate));
    dto.setTotalAmount(latestProcess.get(process.totalAmount));
    return dto;
  }
}
