package com.s3s.ssm.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dto.ProductTypeDto;
import com.s3s.ssm.repo.ProductRepository;
import com.s3s.ssm.repo.ProductTypeRepository;
import com.sunrise.xdoc.entity.catalog.Product;
import com.sunrise.xdoc.entity.catalog.ProductType;

@Component("productTypeService")
@Transactional
class ProductTypeServiceImpl implements IProductTypeService {
  @Autowired
  private ProductTypeRepository productTypeRepository;
  @Autowired
  private ProductRepository productRepository;

  public static ProductTypeDto transformToDto(ProductType productType) {
    ProductTypeDto dto = new ProductTypeDto();
    BeanUtils.copyProperties(productType, dto, "image");
    dto.setImage(productType.getImage());
    return dto;
  }

  public static void transformToEntity(ProductType productType, ProductTypeDto productTypeDto) {
    productType.setCode(productTypeDto.getCode());
    productType.setName(productTypeDto.getName());
    productType.setImage(productTypeDto.getImage());
  }

  @Override
  public long countByActive() {
    return productTypeRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return productTypeRepository.countByActive(false);
  }

  @Override
  public void inactivate(long[] ids) {
    activate(ids, false);
  }

  @Override
  public void activate(long[] ids) {
    activate(ids, true);
  }

  private void activate(long[] ids, boolean active) {
    for (long id : ids) {
      if (productTypeRepository.exists(id)) {
        ProductType productType = productTypeRepository.findOne(id);
        productType.setActive(active);

        Iterable<Product> products = productRepository.findByProductType(productType);
        products.forEach(product -> {
          product.setActive(active);
          productRepository.save(product);
        });
        productTypeRepository.save(productType);
      }
    }
  }

  @Override
  public void saveOrUpdate(ProductTypeDto dto) {
    ProductType entity = null;
    if (dto.getId() != null) {
      entity = productTypeRepository.findOne(dto.getId());
    }
    if (entity == null) {
      entity = new ProductType();
    }
    transformToEntity(entity, dto);
    productTypeRepository.save(entity);
  }

  @Override
  public long count() {
    return productTypeRepository.count();
  }

  @Override
  public ProductTypeDto findOne(Long id) {
    return transformToDto(productTypeRepository.findOne(id));
  }

  @Override
  public boolean exists(String code) {
    return productTypeRepository.findByCode(code) != null;
  }

  @Override
  public List<ProductTypeDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(productTypeRepository.findByActive(true, pageRequest).spliterator(), true)
            .map(foodTable -> transformToDto(foodTable)).collect(Collectors.toList());
  }

  @Override
  public List<ProductTypeDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(productTypeRepository.findByActive(false, pageRequest).spliterator(), true)
            .map(foodTable -> transformToDto(foodTable)).collect(Collectors.toList());
  }

  @Override
  public List<ProductTypeDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(productTypeRepository.findAll(pageRequest).spliterator(), true)
            .map(foodTable -> transformToDto(foodTable)).collect(Collectors.toList());
  }
}
