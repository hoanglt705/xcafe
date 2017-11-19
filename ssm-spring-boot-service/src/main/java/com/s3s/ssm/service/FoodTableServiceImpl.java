package com.s3s.ssm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dto.AreaDto;
import com.s3s.ssm.dto.FoodTableDto;
import com.s3s.ssm.repo.AreaRepository;
import com.s3s.ssm.repo.FoodTableRepository;
import com.sunrise.xdoc.entity.config.Area;
import com.sunrise.xdoc.entity.config.FoodTable;

@Component("foodTableService")
@Transactional
class FoodTableServiceImpl implements IFoodTableService {
  @Autowired
  private FoodTableRepository foodTableRepository;
  @Autowired
  private AreaRepository areaRepository;

  private FoodTableDto transformToDto(FoodTable foodTable) {
    FoodTableDto dto = new FoodTableDto(foodTable.getId(),
            foodTable.getCode(), foodTable.isActive());
    dto.setName(foodTable.getName());
    dto.setSeatNum(foodTable.getSeatNum());

    AreaDto areaDto = new AreaDto();
    Area area = foodTable.getArea();
    areaDto.setCode(area.getCode());
    areaDto.setName(area.getName());
    dto.setArea(areaDto);
    return dto;
  }

  @Override
  @Cacheable("countFoodTableByActiveCache")
  public long countByActive() {
    return foodTableRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return foodTableRepository.countByActive(false);
  }

  @Override
  @Cacheable("foodTableSameAreaCache")
  public List<FoodTableDto> findAllSameArea(String areaCode) {
    List<FoodTableDto> result = new ArrayList<>();
    Iterable<FoodTable> foodTables = foodTableRepository.findByActive(true);
    for (FoodTable foodTable : foodTables) {
      Area area = foodTable.getArea();
      if (area.getCode().equals(areaCode)) {
        FoodTableDto dto = new FoodTableDto(foodTable.getId(), foodTable.getCode(), foodTable.isActive());
        dto.setName(foodTable.getName());
        AreaDto areaDto = new AreaDto();
        areaDto.setCode(area.getCode());
        areaDto.setName(area.getName());
        dto.setArea(areaDto);
        dto.setSeatNum(foodTable.getSeatNum());
        result.add(dto);
      }
    }
    return result;

  }

  @Override
  @Cacheable("findFoodTableCache")
  public FoodTableDto findFoodTable(String foodTableCode) {
    FoodTable foodTable = foodTableRepository.findByCode(foodTableCode);
    return transformToDto(foodTable);
  }

  @Override
  public void inactivate(long[] ids) {
    for (long foodTableId : ids) {
      if (foodTableRepository.exists(foodTableId)) {
        FoodTable foodTable = foodTableRepository.findOne(foodTableId);
        foodTable.setActive(false);
        foodTableRepository.save(foodTable);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long foodTableId : ids) {
      if (foodTableRepository.exists(foodTableId)) {
        FoodTable foodTable = foodTableRepository.findOne(foodTableId);
        foodTable.setActive(true);
        foodTableRepository.save(foodTable);
      }
    }
  }

  @Override
  public void saveOrUpdate(FoodTableDto dto) {
    FoodTable foodTable = null;
    if (dto.getId() != null) {
      foodTable = foodTableRepository.findOne(dto.getId());
    }
    if (foodTable == null) {
      foodTable = new FoodTable();
    }
    foodTable.setCode(dto.getCode());
    foodTable.setName(dto.getName());
    foodTable.setSeatNum(dto.getSeatNum());

    Area area = areaRepository.findOne(dto.getArea().getId());
    if (foodTable.isPersisted()) {
      if (!area.getId().equals(foodTable.getArea().getId())) {
        foodTable.setArea(area);
      }
    } else {
      foodTable.setArea(area);
    }
    foodTableRepository.save(foodTable);
  }

  @Override
  public long count() {
    return foodTableRepository.count();
  }

  @Override
  public FoodTableDto findOne(Long id) {
    return transformToDto(foodTableRepository.findOne(id));
  }

  @Override
  public boolean exists(String code) {
    return foodTableRepository.findByCode(code) != null;
  }

  @Override
  public List<FoodTableDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(foodTableRepository.findByActive(true, pageRequest).spliterator(), true)
            .map(foodTable -> transformToDto(foodTable)).collect(Collectors.toList());
  }

  @Override
  public List<FoodTableDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(foodTableRepository.findByActive(false, pageRequest).spliterator(), true)
            .map(foodTable -> transformToDto(foodTable)).collect(Collectors.toList());
  }

  @Override
  public List<FoodTableDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(foodTableRepository.findAll(pageRequest).spliterator(), true)
            .map(foodTable -> transformToDto(foodTable)).collect(Collectors.toList());
  }
}
