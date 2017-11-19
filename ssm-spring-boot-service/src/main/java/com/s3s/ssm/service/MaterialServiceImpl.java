package com.s3s.ssm.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dto.ImportPriceDetailDto;
import com.s3s.ssm.dto.MaterialDto;
import com.s3s.ssm.dto.SupplierDto;
import com.s3s.ssm.repo.ImportPriceDetailRepository;
import com.s3s.ssm.repo.MaterialRepository;
import com.s3s.ssm.repo.ProductTypeRepository;
import com.s3s.ssm.repo.SupplierRepository;
import com.s3s.ssm.repo.UnitOfMeasureRepository;
import com.sunrise.xdoc.entity.catalog.ImportPriceDetail;
import com.sunrise.xdoc.entity.catalog.Material;
import com.sunrise.xdoc.entity.catalog.ProductType;
import com.sunrise.xdoc.entity.config.UnitOfMeasure;
import com.sunrise.xdoc.entity.contact.Supplier;

@Component("materialService")
@Transactional
public class MaterialServiceImpl implements IMaterialService {
  @Autowired
  private MaterialRepository materialRepository;
  @Autowired
  private ProductTypeRepository productTypeRepository;
  @Autowired
  private UnitOfMeasureRepository unitOfMeasureRepository;
  @Autowired
  private SupplierRepository supplierRepository;
  @Autowired
  private ImportPriceDetailRepository importPriceDetailRepository;

  @Override
  public void saveOrUpdate(MaterialDto dto) {
    Material material = null;
    if (dto.getId() != null) {
      material = materialRepository.findOne(dto.getId());
    }
    if (material == null) {
      material = new Material();
    }
    transformToEntity(material, dto);
    materialRepository.save(material);
  }

  @Override
  public long count() {
    return materialRepository.count();
  }

  @Override
  public MaterialDto findOne(Long id) {
    if (materialRepository.exists(id)) {
      Material area = materialRepository.findOne(id);
      return transformToDto(area);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return materialRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long id : ids) {
      if (materialRepository.exists(id)) {
        Material area = materialRepository.findOne(id);
        area.setActive(false);
        materialRepository.save(area);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long id : ids) {
      Material area = materialRepository.findOne(id);
      area.setActive(true);
      materialRepository.save(area);
    }

  }

  public static MaterialDto transformToDto(Material material) {
    MaterialDto materialDto = new MaterialDto();
    BeanUtils.copyProperties(material, materialDto);
    BeanUtils.copyProperties(material.getProductType(), materialDto.getProductType());
    BeanUtils.copyProperties(material.getUom(), materialDto.getUom());
    for (ImportPriceDetail importPriceDetail : material.getImportPrices()) {
      ImportPriceDetailDto importPriceDetailDto = new ImportPriceDetailDto();
      BeanUtils.copyProperties(importPriceDetail, importPriceDetailDto);
      BeanUtils.copyProperties(importPriceDetail.getSupplier(), importPriceDetailDto.getSupplier());
      materialDto.getImportPrices().add(importPriceDetailDto);
    }
    return materialDto;
  }

  private void transformToEntity(Material material, MaterialDto materialDto) {
    BeanUtils.copyProperties(materialDto, material);
    if (material.isPersisted()
            && !materialDto.getProductType().getId().equals(material.getProductType().getId())) {
      ProductType newProductType = productTypeRepository.findOne(materialDto.getProductType().getId());
      material.setProductType(newProductType);
    }
    if (material.isPersisted() && !materialDto.getUom().getId().equals(material.getUom().getId())) {
      UnitOfMeasure newUom = unitOfMeasureRepository.findOne(materialDto.getUom().getId());
      material.setUom(newUom);
    }
    material.getImportPrices().clear();
    for (ImportPriceDetailDto importPriceDetailDto : materialDto.getImportPrices()) {
      ImportPriceDetail importPriceDetail = new ImportPriceDetail();
      BeanUtils.copyProperties(importPriceDetailDto, importPriceDetail);
      importPriceDetail.setMaterial(materialRepository.findByCode(importPriceDetailDto.getMaterial()
              .getCode()));
      importPriceDetail.setSupplier(supplierRepository.findByCode(importPriceDetailDto.getSupplier()
              .getCode()));
      material.getImportPrices().add(importPriceDetail);
    }
  }

  @Override
  public long countByActive() {
    return materialRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return materialRepository.countByActive(false);
  }

  @Override
  public List<MaterialDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(materialRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(area -> transformToDto(area)).collect(Collectors.toList());
  }

  @Override
  public List<MaterialDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(materialRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(area -> transformToDto(area)).collect(Collectors.toList());
  }

  @Override
  public List<MaterialDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(materialRepository.findAll(pageRequest).spliterator(), false)
            .map(food -> transformToDto(food)).collect(Collectors.toList());
  }

  @Override
  public List<MaterialDto> findBySupplier(SupplierDto supplierDto) {
    Supplier supplier = supplierRepository.findByCode(supplierDto.getCode());
    List<ImportPriceDetail> importPriceDetails = importPriceDetailRepository.findBySupplier(supplier);
    return StreamSupport.stream(importPriceDetails.spliterator(), false)
            .map(price -> transformToDto(price.getMaterial()))
            .collect(Collectors.toList());
  }
}
