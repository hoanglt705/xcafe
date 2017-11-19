package com.s3s.ssm.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dto.FoodDto;
import com.s3s.ssm.dto.FoodIngredientDto;
import com.s3s.ssm.repo.FoodIngredientRepository;
import com.s3s.ssm.repo.FoodRepository;
import com.s3s.ssm.repo.MaterialRepository;
import com.s3s.ssm.repo.ProductTypeRepository;
import com.s3s.ssm.repo.UnitOfMeasureRepository;
import com.sunrise.xdoc.entity.catalog.Food;
import com.sunrise.xdoc.entity.catalog.FoodIngredient;
import com.sunrise.xdoc.entity.catalog.Material;
import com.sunrise.xdoc.entity.catalog.ProductType;
import com.sunrise.xdoc.entity.config.UnitOfMeasure;

@Component("foodService")
@Transactional
public class FoodServiceImpl implements IFoodService {
  @Autowired
  private FoodRepository foodRepository;
  @Autowired
  private ProductTypeRepository productTypeRepository;
  @Autowired
  private UnitOfMeasureRepository unitOfMeasureRepository;
  @Autowired
  private MaterialRepository materialRepository;
  @Autowired
  private FoodIngredientRepository foodIngredientRepository;

  @Override
  public void saveOrUpdate(FoodDto dto) {
    Food area = null;
    if (dto.getId() != null) {
      area = foodRepository.findOne(dto.getId());
    }
    if (area == null) {
      area = new Food();
    }
    transformToEntity(area, dto);
    foodRepository.save(area);
  }

  @Override
  public long count() {
    return foodRepository.count();
  }

  @Override
  public FoodDto findOne(Long id) {
    if (foodRepository.exists(id)) {
      Food area = foodRepository.findOne(id);
      return transformToDto(area);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return foodRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long id : ids) {
      if (foodRepository.exists(id)) {
        Food area = foodRepository.findOne(id);
        area.setActive(false);
        foodRepository.save(area);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long id : ids) {
      Food area = foodRepository.findOne(id);
      area.setActive(true);
      foodRepository.save(area);
    }

  }

  protected FoodDto transformToDto(Food food) {
    FoodDto foodDto = new FoodDto();
    BeanUtils.copyProperties(food, foodDto);

    BeanUtils.copyProperties(food.getProductType(), foodDto.getProductType());
    BeanUtils.copyProperties(food.getUom(), foodDto.getUom());

    for (FoodIngredient foodIngredient : food.getFoodIngredients()) {
      FoodIngredientDto foodIngredientDto = new FoodIngredientDto();
      BeanUtils.copyProperties(foodIngredient, foodIngredientDto);
      BeanUtils.copyProperties(foodIngredient.getMaterial(), foodIngredientDto.getMaterial());
      BeanUtils.copyProperties(foodIngredient.getUom(), foodIngredientDto.getUom());
      foodDto.getFoodIngredients().add(foodIngredientDto);
    }
    return foodDto;
  }

  private void transformToEntity(Food food, FoodDto foodDto) {
    BeanUtils.copyProperties(foodDto, food);
    food.setImage(foodDto.getImage());
    if (foodDto.getProductType() != null) {
      ProductType newProductType = productTypeRepository.findOne(foodDto.getProductType().getId());
      food.setProductType(newProductType);
    }

    if (foodDto.getUom() != null) {
      UnitOfMeasure newUom = unitOfMeasureRepository.findOne(foodDto.getUom().getId());
      food.setUom(newUom);
    }
    food.getFoodIngredients().clear();
    for (FoodIngredientDto foodIngredientDto : foodDto.getFoodIngredients()) {
      FoodIngredient foodIngredient = new FoodIngredient();
      Material material = materialRepository.findByCode(foodIngredientDto.getMaterial().getCode());
      foodIngredient.setMaterial(material);
      Food ingreFood = foodRepository.findByCode(foodIngredientDto.getFood().getCode());
      foodIngredient.setFood(ingreFood);
      UnitOfMeasure uom = unitOfMeasureRepository.findByCode(foodIngredientDto.getUom().getCode());
      foodIngredient.setUom(uom);
      BeanUtils.copyProperties(foodIngredientDto, foodIngredient);
      food.getFoodIngredients().add(foodIngredient);
    }
  }

  @Override
  public long countByActive() {
    return foodRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return foodRepository.countByActive(false);
  }

  @Override
  public List<FoodDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(foodRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(food -> transformToDto(food)).collect(Collectors.toList());
  }

  @Override
  public List<FoodDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(foodRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(area -> transformToDto(area)).collect(Collectors.toList());
  }

  @Override
  public List<FoodDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(foodRepository.findAll(pageRequest).spliterator(), false)
            .map(food -> transformToDto(food)).collect(Collectors.toList());
  }
}
