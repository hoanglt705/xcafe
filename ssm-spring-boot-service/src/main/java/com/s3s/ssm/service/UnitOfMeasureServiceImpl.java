package com.s3s.ssm.service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dto.UnitOfMeasureDto;
import com.s3s.ssm.dto.UomCategoryDto;
import com.s3s.ssm.repo.UnitOfMeasureRepository;
import com.s3s.ssm.repo.UomCategoryRepository;
import com.sunrise.xdoc.entity.config.UnitOfMeasure;
import com.sunrise.xdoc.entity.config.UomCategory;

@Component("unitOfMeasureService")
@Transactional
class UnitOfMeasureServiceImpl implements IUnitOfMeasureService {
  @Autowired
  private UnitOfMeasureRepository uomRepository;
  @Autowired
  private UomCategoryRepository uomCategoryRepository;

  public void setUomRepository(UnitOfMeasureRepository uomRepository) {
    this.uomRepository = uomRepository;
  }

  @Override
  public void saveOrUpdate(UnitOfMeasureDto dto) {
    UnitOfMeasure uom = null;
    if (dto.getId() != null) {
      uom = uomRepository.findOne(dto.getId());
    }
    if (uom == null) {
      uom = new UnitOfMeasure();
    }

    if (uom.isPersisted()) {
      if (uom.getUomCategory() != null && !uom.getUomCategory().getId().equals(dto.getUomCategory().getId())) {
        UomCategory newUomCategory = uomCategoryRepository.findOne(dto.getUomCategory().getId());
        uom.setUomCategory(newUomCategory);
      }
      if (!uom.getIsBaseMeasure() && dto.getIsBaseMeasure()) {
        Iterator<UnitOfMeasure> uoms = uomRepository.findByIsBaseMeasure(true).iterator();
        while (uoms.hasNext()) {
          UnitOfMeasure baseUom = uoms.next();
          baseUom.setIsBaseMeasure(false);
          uomRepository.save(baseUom);
          break;
        }
      }
    }
    uom = transformToEntity(uom, dto);
    uomRepository.save(uom);
  }

  @Override
  public long count() {
    return uomRepository.count();
  }

  @Override
  public UnitOfMeasureDto findOne(Long id) {
    if (uomRepository.exists(id)) {
      UnitOfMeasure area = uomRepository.findOne(id);
      return transformToDto(area);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return uomRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long areaId : ids) {
      if (uomRepository.exists(areaId)) {
        UnitOfMeasure area = uomRepository.findOne(areaId);
        area.setActive(false);
        uomRepository.save(area);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long areaId : ids) {
      UnitOfMeasure area = uomRepository.findOne(areaId);
      area.setActive(true);
      uomRepository.save(area);
    }
  }

  public static UnitOfMeasureDto transformToDto(UnitOfMeasure unitOfMeasure) {
    UnitOfMeasureDto dto = new UnitOfMeasureDto();
    dto.setId(unitOfMeasure.getId());
    dto.setCode(unitOfMeasure.getCode());
    dto.setName(unitOfMeasure.getName());
    dto.setShortName(unitOfMeasure.getShortName());
    dto.setIsBaseMeasure(unitOfMeasure.getIsBaseMeasure());
    dto.setExchangeRate(unitOfMeasure.getExchangeRate());
    dto.setActive(unitOfMeasure.isActive());

    UomCategory parent = unitOfMeasure.getUomCategory();
    UomCategoryDto parentDto = new UomCategoryDto();
    parentDto.setCode(parent.getCode());
    parentDto.setName(parent.getName());

    dto.setUomCategory(parentDto);
    return dto;
  }

  private UnitOfMeasure transformToEntity(UnitOfMeasure entity, UnitOfMeasureDto dto) {
    entity.setId(dto.getId());
    entity.setCode(dto.getCode());
    entity.setName(dto.getName());
    entity.setShortName(dto.getShortName());
    entity.setIsBaseMeasure(dto.getIsBaseMeasure());
    entity.setExchangeRate(dto.getExchangeRate());
    entity.setActive(dto.isActive());
    return entity;
  }

  @Override
  public long countByActive() {
    return uomRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return uomRepository.countByActive(false);
  }

  @Override
  public List<UnitOfMeasureDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(uomRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(uomCategory -> transformToDto(uomCategory)).collect(Collectors.toList());
  }

  @Override
  public List<UnitOfMeasureDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(uomRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(uomCategory -> transformToDto(uomCategory)).collect(Collectors.toList());
  }

  @Override
  public List<UnitOfMeasureDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(uomRepository.findAll(pageRequest).spliterator(), false)
            .map(uomCategory -> transformToDto(uomCategory)).collect(Collectors.toList());
  }

  @Override
  public UnitOfMeasureDto getBaseUnitOfMeasure(UomCategoryDto uomCategory) {
    Iterable<UnitOfMeasure> uoms = uomRepository.findByUomCategory(
            uomCategoryRepository.findOne(uomCategory.getId()));
    for (UnitOfMeasure unitOfMeasure : uoms) {
      if (unitOfMeasure.getIsBaseMeasure()) {
        return transformToDto(unitOfMeasure);
      }
    }
    return null;
  }

  @Override
  public List<UnitOfMeasureDto> findRelatingUom(UnitOfMeasureDto uom) {
    UomCategoryDto uomCategoryDto = uom.getUomCategory();
    if (uomCategoryDto == null) {
      return Arrays.asList(uom);
    }
    UomCategory uomCategory = uomCategoryRepository.findByCode(uomCategoryDto.getCode());
    return StreamSupport.stream(uomRepository.findByUomCategory(uomCategory).spliterator(), false)
            .map(unitOfMeasure -> transformToDto(unitOfMeasure)).collect(Collectors.toList());
  }
}
