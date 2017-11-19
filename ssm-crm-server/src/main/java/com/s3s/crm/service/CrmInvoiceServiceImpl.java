package com.s3s.crm.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.crm.dto.CrmInvoiceDetailDto;
import com.s3s.crm.dto.CrmInvoiceDto;
import com.s3s.crm.dto.CrmProductDto;
import com.s3s.crm.dto.CustomerDto;
import com.s3s.crm.dto.InternalMaterialDto;
import com.s3s.crm.dto.MaterialTypeDto;
import com.s3s.crm.dto.ShapeDto;
import com.s3s.crm.dto.SizeDto;
import com.s3s.crm.repo.CrmInvoiceRepository;
import com.s3s.crm.repo.CrmProductRepository;
import com.s3s.crm.repo.CustomerRepository;
import com.s3s.crm.repo.CustomerTypeRepository;
import com.s3s.crm.repo.PromotionRepository;
import com.sunrise.xdoc.entity.crm.CrmInvoice;
import com.sunrise.xdoc.entity.crm.CrmInvoiceDetail;
import com.sunrise.xdoc.entity.crm.Customer;
import com.sunrise.xdoc.entity.crm.CustomerType;
import com.sunrise.xdoc.entity.crm.InternalMaterial;
import com.sunrise.xdoc.entity.crm.MaterialType;
import com.sunrise.xdoc.entity.crm.Promotion;
import com.sunrise.xdoc.entity.crm.Shape;
import com.sunrise.xdoc.entity.crm.Size;

@Component("crmInvoiceService")
@Transactional
public class CrmInvoiceServiceImpl implements ICrmInvoiceService {
  @Autowired
  private CrmInvoiceRepository crmInvoiceRepository;
  @Autowired
  private CrmProductRepository crmProductRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private CustomerTypeRepository customerTypeRepository;
  @Autowired
  private PromotionRepository promotionRepository;
  @Autowired
  private IConfigService configService;

  @Override
  public void saveOrUpdate(CrmInvoiceDto dto) {
    CrmInvoice invoice = null;
    if (dto.getId() != null) {
      invoice = crmInvoiceRepository.findOne(dto.getId());
    }
    if (invoice == null) {
      invoice = new CrmInvoice();
    }
    BeanUtils.copyProperties(dto, invoice, "customer", "invoiceDetails", "promotion");
    if (dto.getCustomer() != null) {
      Customer customer = customerRepository.findByCode(dto.getCustomer().getCode());
      invoice.setCustomer(customer);
    }
    if (dto.getPromotion() != null) {
      Promotion promotion = promotionRepository.findByCode(dto.getPromotion().getCode());
      invoice.setPromotion(promotion);
    }
    invoice.getInvoiceDetails().clear();
    for (CrmInvoiceDetailDto invDetailDto : dto.getInvoiceDetails()) {
      CrmInvoiceDetail invDetail = new CrmInvoiceDetail();
      BeanUtils.copyProperties(invDetailDto, invDetail, "invoice", "product");
      invDetail.setInvoice(invoice);
      invDetail.setProduct(crmProductRepository.findByCode(invDetailDto.getProduct().getCode()));
      invoice.getInvoiceDetails().add(invDetail);
    }
    crmInvoiceRepository.save(invoice);
    updatePointForCustomer(invoice);
  }

  @Override
  public long count() {
    return crmInvoiceRepository.count();
  }

  @Override
  public CrmInvoiceDto findOne(Long id) {
    if (crmInvoiceRepository.exists(id)) {
      CrmInvoice invoice = crmInvoiceRepository.findOne(id);
      return transformToDto(invoice);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return crmInvoiceRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long areaId : ids) {
      if (crmInvoiceRepository.exists(areaId)) {
        CrmInvoice invoice = crmInvoiceRepository.findOne(areaId);
        invoice.setActive(false);
        crmInvoiceRepository.save(invoice);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long areaId : ids) {
      CrmInvoice invoice = crmInvoiceRepository.findOne(areaId);
      invoice.setActive(true);
      crmInvoiceRepository.save(invoice);
    }

  }

  protected CrmInvoiceDto transformToDto(CrmInvoice invoice) {
    CrmInvoiceDto invoiceDto = new CrmInvoiceDto();
    BeanUtils.copyProperties(invoice, invoiceDto, "customer", "invoiceDetails");
    Customer customer = invoice.getCustomer();
    Promotion promotion = invoice.getPromotion();
    if (customer != null) {
      CustomerDto customerDto = invoiceDto.getCustomer();
      BeanUtils.copyProperties(customer, customerDto);
      CustomerType customerType = customer.getCustomerType();
      if (customerType != null) {
        BeanUtils.copyProperties(customerType, customerDto.getCustomerType());
      }
    }
    if (promotion != null) {
      BeanUtils.copyProperties(promotion, invoiceDto.getPromotion());
    }

    for (CrmInvoiceDetail invDetail : invoice.getInvoiceDetails()) {
      CrmInvoiceDetailDto invDetailDto = new CrmInvoiceDetailDto();
      BeanUtils.copyProperties(invDetail, invDetailDto, "invoice", "product");
      invDetailDto.setInvoice(invoiceDto);
      CrmProductDto productDto = new CrmProductDto();
      BeanUtils.copyProperties(invDetail.getProduct(), productDto, "shape", "size", "materialType",
              "internalMaterial");
      Shape shape = invDetail.getProduct().getShape();
      if (shape != null) {
        ShapeDto shapeDto = new ShapeDto();
        BeanUtils.copyProperties(shape, shapeDto);
        productDto.setShape(shapeDto);
        invDetailDto.setShape(shapeDto);
      }
      Size size = invDetail.getProduct().getSize();
      if (size != null) {
        SizeDto sizeDto = new SizeDto();
        BeanUtils.copyProperties(size, sizeDto);
        productDto.setSize(sizeDto);
        invDetailDto.setSize(sizeDto);
      }
      MaterialType materialType = invDetail.getProduct().getMaterialType();
      if (materialType != null) {
        MaterialTypeDto materialTypeDto = new MaterialTypeDto();
        BeanUtils.copyProperties(materialType, materialTypeDto);
        productDto.setMaterialType(materialTypeDto);
        invDetailDto.setMaterialType(materialTypeDto);
      }
      InternalMaterial internalMaterial = invDetail.getProduct().getInternalMaterial();
      if (internalMaterial != null) {
        InternalMaterialDto internalMaterialDto = new InternalMaterialDto();
        BeanUtils.copyProperties(internalMaterial, internalMaterialDto);
        productDto.setInternalMaterial(internalMaterialDto);
        invDetailDto.setInternalMaterial(internalMaterialDto);
      }
      invDetailDto.setProduct(productDto);
      invoiceDto.getInvoiceDetails().add(invDetailDto);
    }
    return invoiceDto;
  }

  @Override
  public long countByActive() {
    return crmInvoiceRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return crmInvoiceRepository.countByActive(false);
  }

  @Override
  public List<CrmInvoiceDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(crmInvoiceRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(invoice -> transformToDto(invoice)).collect(Collectors.toList());
  }

  @Override
  public List<CrmInvoiceDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(crmInvoiceRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(invoice -> transformToDto(invoice)).collect(Collectors.toList());
  }

  @Override
  public List<CrmInvoiceDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(crmInvoiceRepository.findAll(pageRequest).spliterator(), false)
            .map(invoice -> transformToDto(invoice)).collect(Collectors.toList());
  }

  public void updatePointForCustomer(CrmInvoice invoice) {
    long amountToCountPoint = getAmountToCountPoint(invoice);
    Integer plusPoint = countPoint(amountToCountPoint);
    int countPoint = countPoint(amountToCountPoint);
    if (!invoice.isBorrowCard()) {
      plusPoint = countPoint;
    } else {
      Integer rewardIntroducePercent = configService.getConfig().getRewardIntroducePercent();
      plusPoint = countPoint * rewardIntroducePercent / 100;
    }
    Customer customer = invoice.getCustomer();
    int newPoint = customer.getPoint() + plusPoint;
    customer.setPoint(newPoint);

    CustomerType nextCustomerType = customer.getCustomerType().getNextCustomerType();
    if (nextCustomerType != null && newPoint >= nextCustomerType.getMinPoint()) {
      customer.setCustomerType(nextCustomerType);
    }
    customerRepository.save(customer);
  }

  private int countPoint(long amount) {
    Long pointValue = configService.getConfig().getPointValue();
    return (int) (amount / pointValue);
  }

  private long getAmountToCountPoint(CrmInvoice invoice) {
    return invoice.getInvoiceDetails().stream().mapToLong(invDetail -> invDetail.getAmount()).sum();
  }
}
