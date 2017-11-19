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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dto.ExportStoreFormDto;
import com.s3s.ssm.dto.ImportStoreFormDto;
import com.s3s.ssm.repo.AreaRepository;
import com.s3s.ssm.repo.FoodTableRepository;

@Component("storeService")
@Transactional
class StoreServiceImpl implements IStoreService {
  @PersistenceContext
  private EntityManager entityManager;
  @Autowired
  private AreaRepository areaRepository;
  @Autowired
  private FoodTableRepository foodTableRepository;

  @Override
  public void updateQuantityOfProductInStore(ImportStoreFormDto form) {
    // TODO Auto-generated method stub

  }

  @Override
  public void updateQuantityOfProductInStore(ExportStoreFormDto form) {
    // TODO Auto-generated method stub

  }

  @Override
  public void recoverProductInStore(ImportStoreFormDto form) {
    // TODO Auto-generated method stub

  }

  @Override
  public void recoverProductInStore(List<ImportStoreFormDto> forms) {
    // TODO Auto-generated method stub

  }

}
