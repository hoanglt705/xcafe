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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dto.ImportStoreDetailDto;
import com.s3s.ssm.dto.ImportStoreFormDto;
import com.s3s.ssm.dto.MaterialDto;
import com.s3s.ssm.dto.UnitOfMeasureDto;
import com.s3s.ssm.repo.EmployeeRepository;
import com.s3s.ssm.repo.ImportStoreFormRepository;
import com.s3s.ssm.repo.MaterialRepository;
import com.s3s.ssm.repo.SupplierRepository;
import com.s3s.ssm.repo.UnitOfMeasureRepository;
import com.sunrise.xdoc.entity.catalog.Material;
import com.sunrise.xdoc.entity.contact.Supplier;
import com.sunrise.xdoc.entity.employee.Employee;
import com.sunrise.xdoc.entity.store.ImportStoreDetail;
import com.sunrise.xdoc.entity.store.ImportStoreForm;

@Component("importStoreFormService")
@Transactional
public class ImportStoreFormServiceImpl implements IImportStoreFormService {
  @Autowired
  private ImportStoreFormRepository importStoreFormRepository;
  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private SupplierRepository supplierRepository;
  @Autowired
  private MaterialRepository materialRepository;
  @Autowired
  private UnitOfMeasureRepository uomRepository;

  @Override
  public void saveOrUpdate(ImportStoreFormDto dto) {
    ImportStoreForm importStoreForm = null;
    if (dto.getId() != null) {
      importStoreForm = importStoreFormRepository.findOne(dto.getId());
    }
    if (importStoreForm == null) {
      importStoreForm = new ImportStoreForm();
    }
    transformToEntity(dto, importStoreForm);
    importStoreFormRepository.save(importStoreForm);
  }

  private void transformToEntity(ImportStoreFormDto dto, ImportStoreForm importStoreForm) {
    BeanUtils.copyProperties(dto, importStoreForm);
    if (importStoreForm.getStaff() != null) {
      Employee staff = employeeRepository.findByCode(importStoreForm.getStaff().getCode());
      importStoreForm.setStaff(staff);
    } else {
      importStoreForm.setStaff(null);
    }
    if (importStoreForm.getStaff() != null) {
      Supplier supplier = supplierRepository.findByCode(importStoreForm.getStaff().getCode());
      importStoreForm.setSupplier(supplier);
    } else {
      importStoreForm.setStaff(null);
    }
    importStoreForm.getImportDetails().clear();

    List<ImportStoreDetailDto> importDetails = dto.getImportDetails();
    for (ImportStoreDetailDto detailDto : importDetails) {
      ImportStoreDetail detail = new ImportStoreDetail();
      detail.setImportStoreForm(importStoreForm);

      MaterialDto materialDto = detailDto.getMaterial();
      if (materialDto != null) {
        detail.setMaterial(materialRepository.findByCode(materialDto.getCode()));
      } else {
        detail.setMaterial(null);
      }

      UnitOfMeasureDto uomDto = detailDto.getUom();
      if (uomDto != null) {
        detail.setUom(uomRepository.findByCode(uomDto.getCode()));
      } else {
        detail.setUom(null);
      }
      BeanUtils.copyProperties(detailDto, detail);
      importStoreForm.getImportDetails().add(detail);
    }
  }

  @Override
  public long count() {
    return importStoreFormRepository.count();
  }

  @Override
  public ImportStoreFormDto findOne(Long id) {
    if (importStoreFormRepository.exists(id)) {
      ImportStoreForm importStoreForm = importStoreFormRepository.findOne(id);
      return transformToDto(importStoreForm);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return importStoreFormRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long id : ids) {
      if (importStoreFormRepository.exists(id)) {
        ImportStoreForm importStoreForm = importStoreFormRepository.findOne(id);
        importStoreForm.setActive(false);
        importStoreFormRepository.save(importStoreForm);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long id : ids) {
      ImportStoreForm importStoreForm = importStoreFormRepository.findOne(id);
      importStoreForm.setActive(true);
      importStoreFormRepository.save(importStoreForm);
    }

  }

  protected ImportStoreFormDto transformToDto(ImportStoreForm importStoreForm) {
    ImportStoreFormDto importStoreFormDto = new ImportStoreFormDto();
    BeanUtils.copyProperties(importStoreForm, importStoreFormDto);
    importStoreFormDto.setStaff(EmployeeServiceImpl.transformToDto(importStoreForm.getStaff()));
    importStoreFormDto.setSupplier(SupplierServiceImpl.transformToDto(importStoreForm.getSupplier()));
    Set<ImportStoreDetail> importDetails = importStoreForm.getImportDetails();
    for (ImportStoreDetail detail : importDetails) {
      ImportStoreDetailDto detailDto = new ImportStoreDetailDto();
      detailDto.setImportStoreForm(importStoreFormDto);
      BeanUtils.copyProperties(detail, detailDto);

      Material material = detail.getMaterial();
      if (material != null) {
        MaterialDto materialDto = new MaterialDto();
        BeanUtils.copyProperties(material, materialDto);
        detailDto.setMaterial(materialDto);
      }
      UnitOfMeasureDto uom = detailDto.getUom();
      if (uom != null) {
        UnitOfMeasureDto uomDto = new UnitOfMeasureDto();
        BeanUtils.copyProperties(uom, uomDto);
        detailDto.setUom(uomDto);
      }
      importStoreFormDto.getImportDetails().add(detailDto);
    }
    return importStoreFormDto;
  }

  @Override
  public long countByActive() {
    return importStoreFormRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return importStoreFormRepository.countByActive(false);
  }

  @Override
  public List<ImportStoreFormDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport
            .stream(importStoreFormRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(importStoreForm -> transformToDto(importStoreForm)).collect(Collectors.toList());
  }

  @Override
  public List<ImportStoreFormDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport
            .stream(importStoreFormRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(importStoreForm -> transformToDto(importStoreForm)).collect(Collectors.toList());
  }

  @Override
  public List<ImportStoreFormDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(importStoreFormRepository.findAll(pageRequest).spliterator(), false)
            .map(importStoreForm -> transformToDto(importStoreForm)).collect(Collectors.toList());
  }
}
