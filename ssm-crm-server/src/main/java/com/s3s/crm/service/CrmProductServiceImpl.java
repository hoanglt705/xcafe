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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.crm.dto.CrmProductDto;
import com.s3s.crm.dto.InternalMaterialDto;
import com.s3s.crm.dto.MaterialTypeDto;
import com.s3s.crm.dto.ShapeDto;
import com.s3s.crm.dto.SizeDto;
import com.s3s.crm.repo.CrmProductRepository;
import com.s3s.crm.repo.InternalMaterialRepository;
import com.s3s.crm.repo.MaterialTypeRepository;
import com.s3s.crm.repo.ShapeRepository;
import com.s3s.crm.repo.SizeRepository;
import com.sunrise.xdoc.entity.crm.CrmProduct;

@Component("crmProductService")
@Transactional
public class CrmProductServiceImpl implements ICrmProductService {
  @Autowired
  private CrmProductRepository crmProductdRepository;
  @Autowired
  private SizeRepository sizeRepository;
  @Autowired
  private ShapeRepository shapeRepository;
  @Autowired
  private MaterialTypeRepository materialTypeRepository;
  @Autowired
  private InternalMaterialRepository internalMaterialRepository;

  @Override
  public void saveOrUpdate(CrmProductDto productDto) {
    CrmProduct crmProduct = null;
    if (productDto.getId() != null) {
      crmProduct = crmProductdRepository.findOne(productDto.getId());
    }
    if (crmProduct == null) {
      crmProduct = new CrmProduct();
    }
    BeanUtils.copyProperties(productDto, crmProduct);
    SizeDto sizeDto = productDto.getSize();
    if (sizeDto != null) {
      crmProduct.setSize(sizeRepository.findByCode(sizeDto.getCode()));
    }
    ShapeDto shapeDto = productDto.getShape();
    if (shapeDto != null) {
      crmProduct.setShape(shapeRepository.findByCode(shapeDto.getCode()));
    }
    MaterialTypeDto materialTypeDto = productDto.getMaterialType();
    if (materialTypeDto != null) {
      crmProduct.setMaterialType(materialTypeRepository.findByCode(materialTypeDto.getCode()));
    }
    InternalMaterialDto internalMaterialDto = productDto.getInternalMaterial();
    if (internalMaterialDto != null) {
      crmProduct.setInternalMaterial(internalMaterialRepository.findByCode(internalMaterialDto.getCode()));
    }
    crmProductdRepository.save(crmProduct);
  }

  @Override
  public long count() {
    return crmProductdRepository.count();
  }

  @Override
  public CrmProductDto findOne(Long id) {
    if (crmProductdRepository.exists(id)) {
      CrmProduct CrmProduct = crmProductdRepository.findOne(id);
      return transformToDto(CrmProduct);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return crmProductdRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long customerId : ids) {
      if (crmProductdRepository.exists(customerId)) {
        CrmProduct CrmProduct = crmProductdRepository.findOne(customerId);
        CrmProduct.setActive(false);
        crmProductdRepository.save(CrmProduct);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long areaId : ids) {
      CrmProduct CrmProduct = crmProductdRepository.findOne(areaId);
      CrmProduct.setActive(true);
      crmProductdRepository.save(CrmProduct);
    }

  }

  protected CrmProductDto transformToDto(CrmProduct crmProduct) {
    CrmProductDto productDto = new CrmProductDto();
    BeanUtils.copyProperties(crmProduct, productDto);
    BeanUtils.copyProperties(crmProduct.getSize(), productDto.getSize());
    BeanUtils.copyProperties(crmProduct.getShape(), productDto.getShape());
    BeanUtils.copyProperties(crmProduct.getMaterialType(), productDto.getMaterialType());
    BeanUtils.copyProperties(crmProduct.getInternalMaterial(), productDto.getInternalMaterial());
    return productDto;
  }

  @Override
  public long countByActive() {
    return crmProductdRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return crmProductdRepository.countByActive(false);
  }

  @Override
  public List<CrmProductDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(crmProductdRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(CrmProduct -> transformToDto(CrmProduct)).collect(Collectors.toList());
  }

  @Override
  public List<CrmProductDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(crmProductdRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(CrmProduct -> transformToDto(CrmProduct)).collect(Collectors.toList());
  }

  @Override
  public List<CrmProductDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(crmProductdRepository.findAll(pageRequest).spliterator(), false)
            .map(CrmProduct -> transformToDto(CrmProduct)).collect(Collectors.toList());
  }
}
