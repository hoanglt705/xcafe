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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.query.NumberSubQuery;
import com.sunrise.xdoc.entity.crm.CrmSequenceNumber;
import com.sunrise.xdoc.entity.crm.QCrmSequenceNumber;

@Component("crmSequenceNumberService")
@Transactional
class CrmSequenceNumberServiceImpl implements ICrmSequenceNumberService {
  private static final int CODE_LENGTH = 20;
  private final String INVOICE_SEQ = "invoice_seq";
  @PersistenceContext
  private EntityManager entityManager;
  @Autowired
  private IConfigService configService;

  @Override
  public String getInvoiceNextSequence() {
    return getNextSequence(INVOICE_SEQ, configService.getConfig().getPrefixInvoiceCode());
  }

  @Override
  public String getNextSequence(String label, String codeRule) {
    if (label == null || StringUtils.isEmpty(label)) {
      throw new IllegalArgumentException("The parameter must be not null");
    }
    if (codeRule == null || StringUtils.isEmpty(codeRule)) {
      throw new IllegalArgumentException("The parameter must be not null");
    }
    QCrmSequenceNumber qSequenceNumber = QCrmSequenceNumber.crmSequenceNumber;
    BooleanExpression expression = qSequenceNumber.label.eq(label);

    NumberSubQuery<Long> subQuery = new JPASubQuery().from(qSequenceNumber).where(expression).unique(
            qSequenceNumber.seqNumber.max());

    Long maxSeqNumber = new JPAQuery(entityManager).from(qSequenceNumber)
            .where(qSequenceNumber.seqNumber.eq(subQuery)).singleResult(qSequenceNumber.seqNumber);
    Long seqNum = 1l;
    if (maxSeqNumber != null) {
      seqNum = maxSeqNumber + 1;
    }
    String seqNumString = seqNum.toString();
    seqNumString = codeRule
            + StringUtils.leftPad(seqNumString, CODE_LENGTH - codeRule.length(), '0');
    storeSeqNum(label, seqNum);
    return seqNumString;
  }

  private void storeSeqNum(String label, Long seqNum) {
    CrmSequenceNumber number = new CrmSequenceNumber(label, seqNum);
    entityManager.persist(number);
    entityManager.flush();
  }
}
