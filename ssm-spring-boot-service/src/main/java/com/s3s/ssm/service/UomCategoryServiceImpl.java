package com.s3s.ssm.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dto.UomCategoryDto;
import com.s3s.ssm.repo.UomCategoryRepository;
import com.sunrise.xdoc.entity.config.UomCategory;

@Component("uomCategoryService")
@Transactional
public class UomCategoryServiceImpl implements IUomCategoryService {
  @Autowired
  private UomCategoryRepository uomCategoryRepository;

  private List<UomCategoryDto> findAll() {
    return StreamSupport.stream(uomCategoryRepository.findAll().spliterator(), false)
            .map(area -> transformToDto(area)).collect(Collectors.toList());
  }

  @Override
  public void saveOrUpdate(UomCategoryDto dto) {
    UomCategory uomCategory = null;
    if (dto.getId() != null) {
      uomCategory = uomCategoryRepository.findOne(dto.getId());
    }
    if (uomCategory == null) {
      uomCategory = new UomCategory();
    }
    uomCategory.setCode(dto.getCode());
    uomCategory.setName(dto.getName());

    uomCategoryRepository.save(uomCategory);
  }

  @Override
  public long count() {
    return uomCategoryRepository.count();
  }

  @Override
  public UomCategoryDto findOne(Long id) {
    if (uomCategoryRepository.exists(id)) {
      UomCategory area = uomCategoryRepository.findOne(id);
      return transformToDto(area);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return uomCategoryRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long areaId : ids) {
      if (uomCategoryRepository.exists(areaId)) {
        UomCategory area = uomCategoryRepository.findOne(areaId);
        area.setActive(false);
        uomCategoryRepository.save(area);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long areaId : ids) {
      UomCategory area = uomCategoryRepository.findOne(areaId);
      area.setActive(true);
      uomCategoryRepository.save(area);
    }

  }

  protected UomCategoryDto transformToDto(UomCategory uomCategory) {
    UomCategoryDto areaDto = new UomCategoryDto();
    areaDto.setId(uomCategory.getId());
    areaDto.setCode(uomCategory.getCode());
    areaDto.setName(uomCategory.getName());
    areaDto.setActive(uomCategory.isActive());
    return areaDto;
  }

  @Override
  public long countByActive() {
    return uomCategoryRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return uomCategoryRepository.countByActive(false);
  }

  @Override
  public List<UomCategoryDto> getSuggestParent(UomCategoryDto uomCategoryDto) {
    List<UomCategoryDto> all = findAll();
    all.remove(uomCategoryDto);
    if (uomCategoryDto.getParent() != null) {
      all.remove(uomCategoryDto.getParent());
    }
    return all;
  }

  @Override
  public List<UomCategoryDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(uomCategoryRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(uomCategory -> transformToDto(uomCategory)).collect(Collectors.toList());
  }

  @Override
  public List<UomCategoryDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(uomCategoryRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(uomCategory -> transformToDto(uomCategory)).collect(Collectors.toList());
  }

  @Override
  public List<UomCategoryDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(uomCategoryRepository.findAll(pageRequest).spliterator(), false)
            .map(uomCategory -> transformToDto(uomCategory)).collect(Collectors.toList());
  }
}
