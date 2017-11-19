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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.query.NumberSubQuery;
import com.sunrise.xdoc.entity.system.QSequenceNumber;
import com.sunrise.xdoc.entity.system.SequenceNumber;

@Component("sequenceNumberService")
@Transactional
class SequenceNumberServiceImpl implements ISequenceNumberService {
  private final String INVOICE_SEQ = "invoice_seq";
  private final String EMPLOYEE_SEQ = "employee_seq";
  private final String EXPORT_STORE_FORM_SEQ = "exportStoreForm_seq";
  private final String IMPORT_STORE_FORM_SEQ = "importStoreForm_seq";
  private final String PAYMENT_SEQ = "payment_seq";
  @PersistenceContext
  private EntityManager entityManager;
  @Autowired
  private ICompanyService companyService;

  @Override
  public String getInvoiceNextSequence() {
    return getNextSequence(INVOICE_SEQ, companyService.getCompany().getInvoiceCodeRule());
  }

  @Override
  public String getEmployeeNextSequence() {
    return getNextSequence(EMPLOYEE_SEQ, companyService.getCompany().getEmployeeCodeRule());
  }

  @Override
  public String getExportStoreFormNextSequence() {
    return getNextSequence(EXPORT_STORE_FORM_SEQ, companyService.getCompany().getExportFormCodeRule());
  }

  @Override
  public String getImportStoreFormNextSequence() {
    return getNextSequence(IMPORT_STORE_FORM_SEQ, companyService.getCompany().getImportFormCodeRule());
  }

  @Override
  public String getPaymentNextSequence() {
    return getNextSequence(PAYMENT_SEQ, companyService.getCompany().getPaymentCodeRule());
  }

  @Override
  public String getNextSequence(String label, String codeRule) {
    if (label == null || StringUtils.isEmpty(label)) {
      throw new IllegalArgumentException("The parameter must be not null");
    }
    if (codeRule == null || StringUtils.isEmpty(codeRule)) {
      throw new IllegalArgumentException("The parameter must be not null");
    }

    QSequenceNumber qSequenceNumber = QSequenceNumber.sequenceNumber;
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
            + StringUtils.leftPad(seqNumString,
                    companyService.getCompany().getCodeLength() - codeRule.length(), '0');
    storeSeqNum(label, seqNum);
    return seqNumString;
  }

  private void storeSeqNum(String label, Long seqNum) {
    SequenceNumber number = new SequenceNumber(label, seqNum);
    entityManager.persist(number);
    entityManager.flush();
  }
}
