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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Projections;
import com.mysema.query.types.QBean;
import com.s3s.ssm.dto.ProductDto;
import com.s3s.ssm.dto.ProductTypeDto;
import com.s3s.ssm.dto.UnitOfMeasureDto;
import com.sunrise.xdoc.entity.catalog.Food;
import com.sunrise.xdoc.entity.catalog.Material;
import com.sunrise.xdoc.entity.catalog.QFood;
import com.sunrise.xdoc.entity.catalog.QMaterial;
import com.sunrise.xdoc.entity.catalog.QProduct;
import com.sunrise.xdoc.entity.catalog.QProductType;

@Component("productService")
@Transactional
public class ProductServiceImpl implements IProductService {
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<ProductDto> findAll() {
    QProduct qProduct = QProduct.product;
    QBean<ProductDto> qBean = Projections.bean(ProductDto.class, qProduct.id, qProduct.code, qProduct.name);
    return new JPAQuery(entityManager).from(qProduct).where(qProduct.active.eq(true)).list(qBean);
  }

  @Override
  @Cacheable("sellableProductCache")
  public List<ProductDto> getSellableProduct() {
    QFood qFood = QFood.food;
    QMaterial qMaterial = QMaterial.material;
    QBean<ProductDto> qFoodBean = Projections.bean(ProductDto.class, qFood.id, qFood.code, qFood.name,
            qFood.sellPrice);
    List<ProductDto> foodList = new JPAQuery(entityManager).from(qFood).where(qFood.active.eq(true))
            .list(qFoodBean);
    QBean<ProductDto> qMaterialBean = Projections.bean(ProductDto.class, qMaterial.id, qMaterial.code,
            qMaterial.name, qMaterial.sellPrice);
    List<ProductDto> materialList = new JPAQuery(entityManager).from(qMaterial)
            .where(qMaterial.active.eq(true).and(qMaterial.retailable.eq(true)))
            .list(qMaterialBean);
    foodList.addAll(materialList);
    return foodList;
  }

  @Override
  public Map<ProductTypeDto, List<ProductDto>> getSellableProductTypes() {
    Map<ProductTypeDto, List<ProductDto>> result = new LinkedHashMap<>();
    getProductTypeDto()
            .stream()
            .filter(productTypeDto -> isSellable(productTypeDto.getCode()))
            .forEach(
                    productTypeDto -> {
                      List<ProductDto> productDtos = new ArrayList<>();
                      findFoods(productTypeDto).stream().forEach(food -> {
                        ProductDto dto = new ProductDto();
                        dto.setId(food.getId());
                        dto.setCode(food.getCode());
                        dto.setName(food.getName());
                        if (food.getImage() != null) {
                          dto.setImage(food.getImage());
                        }
                        UnitOfMeasureDto uom = new UnitOfMeasureDto();
                        uom.setName(food.getUom().getName());
                        dto.setUom(uom);
                        dto.setSellPrice(food.getSellPrice());
                        dto.setFood(true);
                        productDtos.add(dto);
                      });

                      findMaterials(productTypeDto).stream().forEach(material -> {
                        ProductDto dto = new ProductDto();
                        dto.setId(material.getId());
                        dto.setCode(material.getCode());
                        dto.setName(material.getName());
                        if (material.getImage() != null) {
                          dto.setImage(material.getImage());
                        }
                        UnitOfMeasureDto uom = new UnitOfMeasureDto();
                        uom.setName(material.getUom().getName());
                        dto.setUom(uom);
                        dto.setQuantityInStock(material.getQuantityInStock());
                        dto.setSellPrice(material.getSellPrice());
                        dto.setFood(false);
                        productDtos.add(dto);
                      });

                      result.put(productTypeDto, productDtos);
                    });
    return result;
  }

  @Override
  public Map<ProductTypeDto, List<ProductDto>> getAllProductTypes() {
    Map<ProductTypeDto, List<ProductDto>> result = new LinkedHashMap<>();
    getProductTypeDto()
            .stream()
            .forEach(
                    productTypeDto -> {
                      List<ProductDto> productDtos = new ArrayList<>();
                      findFoods(productTypeDto).stream().forEach(food -> {
                        ProductDto dto = new ProductDto();
                        dto.setId(food.getId());
                        dto.setCode(food.getCode());
                        dto.setName(food.getName());
                        if (food.getImage() != null) {
                          dto.setImage(food.getImage());
                        }
                        UnitOfMeasureDto uom = new UnitOfMeasureDto();
                        uom.setName(food.getUom().getName());
                        dto.setUom(uom);
                        dto.setSellPrice(food.getSellPrice());
                        dto.setFood(true);
                        productDtos.add(dto);
                      });

                      findMaterials(productTypeDto).stream().forEach(material -> {
                        ProductDto dto = new ProductDto();
                        dto.setId(material.getId());
                        dto.setCode(material.getCode());
                        dto.setName(material.getName());
                        if (material.getImage() != null) {
                          dto.setImage(material.getImage());
                        }
                        UnitOfMeasureDto uom = new UnitOfMeasureDto();
                        uom.setName(material.getUom().getName());
                        dto.setUom(uom);
                        dto.setQuantityInStock(material.getQuantityInStock());
                        dto.setSellPrice(material.getSellPrice());
                        dto.setFood(false);
                        productDtos.add(dto);
                      });

                      result.put(productTypeDto, productDtos);
                    });
    return result;
  }

  @Override
  public Map<ProductTypeDto, List<ProductDto>> getAllMaterialTypes() {
    Map<ProductTypeDto, List<ProductDto>> result = new LinkedHashMap<>();
    getProductTypeDto().stream().forEach(
            productTypeDto -> {
              List<ProductDto> productDtos = new ArrayList<>();
              findMaterials(productTypeDto).stream().forEach(material -> {
                ProductDto dto = new ProductDto();
                dto.setId(material.getId());
                dto.setCode(material.getCode());
                dto.setName(material.getName());
                if (material.getImage() != null) {
                  dto.setImage(material.getImage());
                }
                UnitOfMeasureDto uom = new UnitOfMeasureDto();
                uom.setName(material.getUom().getName());
                dto.setUom(uom);
                dto.setQuantityInStock(material.getQuantityInStock());
                dto.setSellPrice(material.getSellPrice());
                dto.setFood(false);
                productDtos.add(dto);
              });
              if (!productDtos.isEmpty()) {
                result.put(productTypeDto, productDtos);
              }
            });
    return result;
  }

  private List<Material> findMaterials(ProductTypeDto productTypeDto) {
    QMaterial qMaterial = QMaterial.material;
    return new JPAQuery(entityManager).from(qMaterial).where(qMaterial.active.eq(true).and(
            qMaterial.productType.code.eq(productTypeDto.getCode())))
            .list(qMaterial);
  }

  @Cacheable("foodCache")
  private List<Food> findFoods(ProductTypeDto productTypeDto) {
    QFood qFood = QFood.food;
    return new JPAQuery(entityManager).from(qFood).where(qFood.active.eq(true).and(
            qFood.productType.code.eq(productTypeDto.getCode())))
            .list(qFood);
  }

  @Override
  @Cacheable("productTypeDtoCache")
  public List<ProductTypeDto> getProductTypeDto() {
    QProductType qProductType = QProductType.productType;
    QBean<ProductTypeDto> qProductTypeDto = Projections
            .bean(ProductTypeDto.class, qProductType.id, qProductType.code, qProductType.name);
    List<ProductTypeDto> productTypeDtos = new JPAQuery(entityManager).from(qProductType)
            .where(qProductType.active.eq(true)).list(qProductTypeDto);
    return productTypeDtos;
  }

  @Cacheable("isSellableCache")
  public boolean isSellable(String productTypeCode) {
    QMaterial qMaterial = QMaterial.material;
    QFood qFood = QFood.food;

    boolean existFood = new JPAQuery(entityManager).from(qFood)
            .where(qFood.active.eq(true).and(qFood.productType.code.eq(productTypeCode))).exists();
    if (existFood) {
      return true;
    }
    boolean existMaterial = new JPAQuery(entityManager)
            .from(qMaterial)
            .where(qMaterial.active.eq(true).and(qMaterial.productType.code.eq(productTypeCode))
                    .and(qMaterial.retailable.eq(true))).exists();
    return existMaterial;
  }

  @Override
  public Map<String, Double> getQuantityInStock(List<ProductDto> productDtos) {
    Map<String, Double> result = new HashMap<>();
    QMaterial qMaterial = QMaterial.material;
    productDtos.forEach(productDto -> {
      Double quantity = new JPAQuery(entityManager).from(qMaterial)
              .where(qMaterial.code.eq(productDto.getCode()))
              .singleResult(qMaterial.quantityInStock);
      result.put(productDto.getCode(), quantity);
    });
    return result;
  }
}
