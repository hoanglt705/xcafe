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

import com.s3s.ssm.dto.SupplierDto;
import com.s3s.ssm.repo.SupplierRepository;
import com.sunrise.xdoc.entity.contact.Supplier;

@Component("supplierService")
@Transactional
public class SupplierServiceImpl implements ISupplierService {
  @Autowired
  private SupplierRepository supplierRepository;

  @Override
  public void saveOrUpdate(SupplierDto dto) {
    Supplier supplier = null;
    if (dto.getId() != null) {
      supplier = supplierRepository.findOne(dto.getId());
    }
    if (supplier == null) {
      supplier = new Supplier();
    }
    transformToEntity(dto, supplier);

    supplierRepository.save(supplier);
  }

  public static void transformToEntity(SupplierDto dto, Supplier supplier) {
    supplier.setCode(dto.getCode());
    supplier.setName(dto.getName());
    supplier.setActive(dto.isActive());
    supplier.setImage(dto.getImage());
    supplier.setRepresenter(dto.getRepresenter());
    supplier.setPosition(dto.getPosition());
    supplier.setPhone(dto.getPhone());
    supplier.setContactPerson1(dto.getContactPerson1());
    supplier.setPhone1(dto.getPhone1());
    supplier.setContactPerson2(dto.getContactPerson2());
    supplier.setPhone2(dto.getPhone2());
    supplier.setWebsite(dto.getWebsite());
  }

  @Override
  public long count() {
    return supplierRepository.count();
  }

  @Override
  public SupplierDto findOne(Long id) {
    if (supplierRepository.exists(id)) {
      Supplier supplier = supplierRepository.findOne(id);
      return transformToDto(supplier);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return supplierRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long supplierId : ids) {
      if (supplierRepository.exists(supplierId)) {
        Supplier supplier = supplierRepository.findOne(supplierId);
        supplier.setActive(false);
        supplierRepository.save(supplier);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long supplierId : ids) {
      Supplier supplier = supplierRepository.findOne(supplierId);
      supplier.setActive(true);
      supplierRepository.save(supplier);
    }

  }

  public static SupplierDto transformToDto(Supplier supplier) {
    SupplierDto supplierDto = new SupplierDto();
    supplierDto.setId(supplier.getId());
    supplierDto.setCode(supplier.getCode());
    supplierDto.setName(supplier.getName());
    supplierDto.setActive(supplier.isActive());
    supplierDto.setImage(supplier.getImage());
    supplierDto.setRepresenter(supplier.getRepresenter());
    supplierDto.setPosition(supplier.getPosition());
    supplierDto.setPhone(supplier.getPhone());
    supplierDto.setContactPerson1(supplier.getContactPerson1());
    supplierDto.setPhone1(supplier.getPhone1());
    supplierDto.setContactPerson2(supplier.getContactPerson2());
    supplierDto.setPhone2(supplier.getPhone2());
    supplierDto.setWebsite(supplier.getWebsite());
    return supplierDto;
  }

  @Override
  public long countByActive() {
    return supplierRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return supplierRepository.countByActive(false);
  }

  @Override
  public List<SupplierDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(supplierRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(supplier -> transformToDto(supplier)).collect(Collectors.toList());
  }

  @Override
  public List<SupplierDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(supplierRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(supplier -> transformToDto(supplier)).collect(Collectors.toList());
  }

  @Override
  public List<SupplierDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(supplierRepository.findAll(pageRequest).spliterator(), false)
            .map(supplier -> transformToDto(supplier)).collect(Collectors.toList());
  }
}
