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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.Projections;
import com.mysema.query.types.QBean;
import com.mysema.query.types.expr.BooleanExpression;
import com.s3s.ssm.dto.AreaDto;
import com.s3s.ssm.dto.EmployeeDto;
import com.s3s.ssm.dto.FoodTableDto;
import com.s3s.ssm.dto.InvoiceDetailDto;
import com.s3s.ssm.dto.InvoiceDto;
import com.s3s.ssm.dto.InvoiceStatus;
import com.s3s.ssm.dto.LatestInvoiceDto;
import com.s3s.ssm.dto.ProductDto;
import com.s3s.ssm.dto.UnitOfMeasureDto;
import com.s3s.ssm.repo.FoodTableRepository;
import com.s3s.ssm.repo.InvoiceRepository;
import com.s3s.ssm.repo.MaterialRepository;
import com.s3s.ssm.repo.ProductRepository;
import com.s3s.ssm.repo.UnitOfMeasureRepository;
import com.sunrise.xdoc.entity.catalog.Material;
import com.sunrise.xdoc.entity.catalog.Product;
import com.sunrise.xdoc.entity.config.Area;
import com.sunrise.xdoc.entity.config.FoodTable;
import com.sunrise.xdoc.entity.employee.Employee;
import com.sunrise.xdoc.entity.sale.Invoice;
import com.sunrise.xdoc.entity.sale.InvoiceDetail;
import com.sunrise.xdoc.entity.sale.QInvoice;

@Component("invoiceService")
@Transactional
class InvoiceServiceImpl implements IInvoiceService {

  @PersistenceContext
  private EntityManager entityManager;
  @Autowired
  private InvoiceRepository invoiceRepository;
  @Autowired
  private ISequenceNumberService sequenceNumberService;
  @Autowired
  private FoodTableRepository foodTableRepo;
  @Autowired
  private ProductRepository productRepo;
  @Autowired
  private MaterialRepository materialRepo;
  @Autowired
  private UnitOfMeasureRepository uomRepo;

  @Override
  public List<LatestInvoiceDto> getLatestInvoice(int size) {
    QInvoice qInvoice = QInvoice.invoice;
    QBean<LatestInvoiceDto> qBean = Projections.bean(LatestInvoiceDto.class, qInvoice.code,
            qInvoice.createdDate, qInvoice.foodTable.name, qInvoice.totalAmount, qInvoice.income);
    return new JPAQuery(entityManager).from(qInvoice).where(qInvoice.active.eq(true)).limit(size)
            .list(qBean);
  }

  @Override
  public List<InvoiceDto> getOperatingInvoice() {
    QInvoice qInvoice = QInvoice.invoice;
    BooleanExpression expression = qInvoice.active.eq(true);
    expression = expression.and(qInvoice.invoiceStatus.in(InvoiceStatus.BOOKING, InvoiceStatus.SERVING));

    List<Invoice> invoices = new JPAQuery(entityManager).from(qInvoice)
            .where(expression).list(qInvoice);

    List<InvoiceDto> dtos = new ArrayList<>();
    invoices.stream().forEach(invoice -> {
      dtos.add(transformToDto(invoice));
    });
    return dtos;
  }

  private InvoiceDto transformToDto(Invoice invoice) {
    InvoiceDto dto = new InvoiceDto();
    dto.setId(invoice.getId());
    dto.setCode(invoice.getCode());
    FoodTable foodTable = invoice.getFoodTable();
    if (foodTable != null) {
      FoodTableDto foodTableDto = new FoodTableDto();
      foodTableDto.setCode(foodTable.getCode());
      foodTableDto.setName(foodTable.getName());
      Area area = foodTable.getArea();
      AreaDto areaDto = new AreaDto();
      areaDto.setCode(area.getCode());
      areaDto.setName(area.getName());
      foodTableDto.setArea(areaDto);
      dto.setFoodTable(foodTableDto);
    }
    Employee staff = invoice.getStaff();
    if (staff != null) {
      EmployeeDto staffDto = new EmployeeDto();
      staffDto.setId(staff.getId());
      staffDto.setCode(staff.getCode());
      staffDto.setName(staff.getName());
      dto.setStaff(staffDto);
    }
    dto.setCreatedDate(invoice.getCreatedDate());
    dto.setTotalAmount(invoice.getTotalAmount());
    dto.setTotalReturnAmount(invoice.getTotalReturnAmount());
    dto.setTotalPaymentAmount(invoice.getTotalPaymentAmount());
    dto.setDiscount(invoice.getDiscount());
    dto.setVatTax(invoice.getVatTax());
    dto.setInvoiceStatus(invoice.getInvoiceStatus());

    List<InvoiceDetailDto> invoiceDetails = new ArrayList<>();
    invoice.getInvoiceDetails().stream().forEach(detail -> {
      InvoiceDetailDto detailDto = new InvoiceDetailDto();
      detailDto.setInvoiceCode(dto.getCode());
      Product product = detail.getProduct();
      ProductDto productDto = new ProductDto();
      productDto.setId(product.getId());
      productDto.setCode(product.getCode());
      productDto.setName(product.getName());
      UnitOfMeasureDto uom = new UnitOfMeasureDto();
      uom.setName(product.getUom().getName());
      productDto.setUom(uom);
      productDto.setSellPrice(product.getSellPrice());
      detailDto.setProduct(productDto);
      detailDto.setQuantity(detail.getQuantity());
      detailDto.setUnitPrice(detail.getUnitPrice());
      detailDto.setAmount(detail.getAmount());
      invoiceDetails.add(detailDto);
    });
    dto.getInvoiceDetails().addAll(invoiceDetails);
    return dto;
  }

  @Override
  public long countInvoice(InvoiceStatus status) {
    QInvoice qInvoice = QInvoice.invoice;
    return new JPAQuery(entityManager).from(qInvoice).where(qInvoice.invoiceStatus.eq(status)).count();
  }

  @Override
  public InvoiceDto createNewInvoice(FoodTableDto foodTableDto) {
    String code = sequenceNumberService.getInvoiceNextSequence();
    InvoiceDto invoiceDto = new InvoiceDto();
    invoiceDto.setCode(code);
    invoiceDto.setFoodTable(foodTableDto);
    invoiceDto.setInvoiceStatus(InvoiceStatus.BOOKING);

    Invoice invoice = new Invoice();
    invoice.setCode(code);
    invoice.setInvoiceStatus(InvoiceStatus.BOOKING);
    FoodTable foodTable = foodTableRepo.findOne(foodTableDto.getId());
    invoice.setFoodTable(foodTable);

    invoiceDto.setId(invoiceRepository.save(invoice).getId());
    return invoiceDto;
  }

  @Override
  public void cancelInvoice(InvoiceDto invoiceDto) {
    Invoice invoice = invoiceRepository.findOne(invoiceDto.getId());
    invoice.setInvoiceStatus(InvoiceStatus.CANCEL);
    invoiceRepository.save(invoice);
    recoverInvoiceDetail(invoiceDto);
  }

  private void recoverInvoiceDetail(InvoiceDto invoiceDto) {
    invoiceDto.getInvoiceDetails().stream().filter(detail -> !detail.getProduct().isFood())
            .forEach(detail -> {
              Long productId = detail.getProduct().getId();
              Material material = materialRepo.findOne(productId);
              material.setQuantityInStock(material.getQuantityInStock() + detail.getQuantity());
              materialRepo.save(material);
            });
  }

  @Override
  public void updateStatus(String code, InvoiceStatus status) {
    QInvoice qInvoice = QInvoice.invoice;
    new JPAUpdateClause(entityManager, qInvoice).where(qInvoice.code.eq(code))
            .set(qInvoice.invoiceStatus, status).execute();
  }

  @Override
  public InvoiceDto combine2Invoices(InvoiceDto fromInvoiceDto, InvoiceDto toInvoiceDto) {
    Set<InvoiceDetailDto> newDetailsDto = new HashSet<>();
    for (InvoiceDetailDto toDetailDto : toInvoiceDto.getInvoiceDetails()) {
      for (InvoiceDetailDto fromDetail : fromInvoiceDto.getInvoiceDetails()) {
        if (toDetailDto.getProduct().equals(fromDetail.getProduct())) {
          toDetailDto.setQuantity(toDetailDto.getQuantity() + fromDetail.getQuantity());
          break;
        }
      }
    }

    for (InvoiceDetailDto fromDetailDto : fromInvoiceDto.getInvoiceDetails()) {
      boolean existed = false;
      for (InvoiceDetailDto toDetailDto : toInvoiceDto.getInvoiceDetails()) {
        if (toDetailDto.getProduct().equals(fromDetailDto.getProduct())) {
          existed = true;
          break;
        }
      }
      if (!existed) {
        newDetailsDto.add(fromDetailDto);
      }
    }
    toInvoiceDto.setTotalAmount(fromInvoiceDto.getTotalAmount() + toInvoiceDto.getTotalAmount());
    toInvoiceDto.getInvoiceDetails().addAll(newDetailsDto);
    updateStatus(fromInvoiceDto.getCode(), InvoiceStatus.COMBINED);
    return toInvoiceDto;
  }

  @Override
  public void updateInvoiceDetail(InvoiceDto invoiceDto) {
    Invoice invoice = invoiceRepository.findOne(invoiceDto.getId());
    List<InvoiceDetail> newInvoiceDetails = new ArrayList<>();
    for (InvoiceDetailDto detailDto : invoiceDto.getInvoiceDetails()) {
      boolean existing = false;
      for (InvoiceDetail detail : invoice.getInvoiceDetails()) {
        if (detailDto.getProduct().getCode().equals(detail.getProduct().getCode())) {
          existing = true;
          detail.setQuantity(detailDto.getQuantity());
        }
      }
      if (!existing) {
        newInvoiceDetails.add(createNewInvoiceDetail(invoice, detailDto));
      }
    }
    invoice.getInvoiceDetails().addAll(newInvoiceDetails);
    invoiceRepository.save(invoice);
  }

  private InvoiceDetail createNewInvoiceDetail(Invoice invoice, InvoiceDetailDto detailDto) {
    InvoiceDetail newDetail = new InvoiceDetail();
    Long productId = detailDto.getProduct().getId();
    Product product = productRepo.findOne(productId);
    newDetail.setProduct(product);
    newDetail.setQuantity(detailDto.getQuantity());
    newDetail.setAmount(detailDto.getAmount());
    newDetail.setUnitPrice(detailDto.getUnitPrice());
    newDetail.setInvoice(invoice);
    return newDetail;
  }

  @Override
  public InvoiceDto moveInvoice(InvoiceDto fromInvoiceDto, FoodTableDto toTableDto) {
    fromInvoiceDto.setFoodTable(toTableDto);
    FoodTable table = foodTableRepo.findOne(toTableDto.getId());
    Invoice invoice = invoiceRepository.findOne(fromInvoiceDto.getId());
    invoice.setFoodTable(table);
    invoiceRepository.save(invoice);
    return fromInvoiceDto;
  }

  @Override
  public void payInvoice(InvoiceDto invoiceDto) {
    long income = 0l;
    if (invoiceDto.getTotalReturnAmount() < 0) {
      income = invoiceDto.getTotalReturnAmount();
    } else {
      income = invoiceDto.getTotalPaymentAmount() - invoiceDto.getTotalReturnAmount();
    }

    Invoice invoice = invoiceRepository.findOne(invoiceDto.getId());
    invoice.setTotalAmount(invoiceDto.getTotalAmount());
    invoice.setTotalPaymentAmount(invoiceDto.getTotalPaymentAmount());
    invoice.setDiscount(invoiceDto.getDiscount());
    invoice.setVatTax(invoiceDto.getVatTax());
    invoice.setTotalReturnAmount(invoiceDto.getTotalReturnAmount());
    invoice.setEndedDate(new Date());
    invoice.setInvoiceStatus(InvoiceStatus.PAID);
    invoice.setIncome(income);

    invoiceRepository.save(invoice);
  }

  @Override
  public void updateProductQuantityInStore(Map<ProductDto, Integer> transaction) {
    transaction.forEach((productDto, quantity) -> {
      if (!productDto.isFood() && quantity > 0) {
        Material material = materialRepo.findOne(productDto.getId());
        material.setQuantityInStock(material.getQuantityInStock() - quantity);
        materialRepo.save(material);
      }
    });
  }

  @Override
  public void updateQuantityOfProductInStore(InvoiceDto transferToDto) {

  }

  @Override
  public void inactivate(long[] ids) {
    for (long id : ids) {
      if (invoiceRepository.exists(id)) {
        Invoice invoice = invoiceRepository.findOne(id);
        invoice.setActive(false);
        invoiceRepository.save(invoice);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long id : ids) {
      if (invoiceRepository.exists(id)) {
        Invoice invoice = invoiceRepository.findOne(id);
        invoice.setActive(true);
        invoiceRepository.save(invoice);
      }
    }
  }

  @Override
  public void saveOrUpdate(InvoiceDto dto) {
    Invoice invoice = null;
    if (dto.getId() != null) {
      invoice = invoiceRepository.findOne(dto.getId());
    }
    if (invoice == null) {
      invoice = new Invoice();
    }
    invoice.setCode(dto.getCode());
    if (dto.getFoodTable() != null) {
      FoodTable foodTable = foodTableRepo.findByCode(dto.getFoodTable().getCode());
      invoice.setFoodTable(foodTable);
    }
    invoice.setCreatedDate(dto.getCreatedDate());
    invoice.setTotalAmount(dto.getTotalAmount());
    invoice.setTotalReturnAmount(dto.getTotalReturnAmount());
    invoice.setTotalPaymentAmount(dto.getTotalPaymentAmount());
    invoice.setDiscount(dto.getDiscount());
    invoice.setVatTax(dto.getVatTax());
    invoice.setInvoiceStatus(dto.getInvoiceStatus());

    invoice.getInvoiceDetails().clear();
    for (InvoiceDetailDto detailDto : dto.getInvoiceDetails()) {
      InvoiceDetail detail = new InvoiceDetail();
      detail.setInvoice(invoice);
      detail.setProduct(productRepo.findByCode(detailDto.getProduct().getCode()));
      detail.setQuantity(detailDto.getQuantity());
      detail.setUnitPrice(detailDto.getUnitPrice());
      detail.setAmount(detailDto.getAmount());
      UnitOfMeasureDto uom = detailDto.getUom();
      if (uom != null) {
        detail.setUom(uomRepo.findByCode(uom.getCode()));
      }
      invoice.getInvoiceDetails().add(detail);
    }
    invoiceRepository.save(invoice);
  }

  @Override
  public long count() {
    return invoiceRepository.count();
  }

  @Override
  public long countByActive() {
    return invoiceRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return invoiceRepository.countByActive(false);
  }

  @Override
  public InvoiceDto findOne(Long id) {
    if (invoiceRepository.exists(id)) {
      Invoice invoice = invoiceRepository.findOne(id);
      return transformToDto(invoice);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return invoiceRepository.findByCode(code) != null;
  }

  @Override
  public List<InvoiceDto> findByActive(int page, int size) {
    QInvoice qInvoice = QInvoice.invoice;
    BooleanExpression expression = qInvoice.active.eq(true);
    expression = expression.and(qInvoice.foodTable.isNotNull());
    expression = expression.and(qInvoice.invoiceStatus.eq(InvoiceStatus.PAID));
    List<Invoice> invoices = new JPAQuery(entityManager).from(qInvoice)
            .where(expression).limit(size).offset(page * size).list(qInvoice);
    return StreamSupport.stream(invoices.spliterator(), false)
            .map(invoice -> transformToDto(invoice)).collect(Collectors.toList());
  }

  @Override
  public List<InvoiceDto> findByInactive(int page, int size) {
    QInvoice qInvoice = QInvoice.invoice;
    BooleanExpression expression = qInvoice.active.eq(false);
    expression = expression.and(qInvoice.foodTable.isNotNull());
    expression = expression.and(qInvoice.invoiceStatus.eq(InvoiceStatus.PAID));
    List<Invoice> invoices = new JPAQuery(entityManager).from(qInvoice)
            .where(expression).limit(size).offset(page * size).list(qInvoice);
    return StreamSupport.stream(invoices.spliterator(), false)
            .map(invoice -> transformToDto(invoice)).collect(Collectors.toList());
  }

  @Override
  public List<InvoiceDto> findAll(int page, int size) {
    QInvoice qInvoice = QInvoice.invoice;
    BooleanExpression expression = qInvoice.foodTable.isNotNull();
    expression = expression.and(qInvoice.invoiceStatus.eq(InvoiceStatus.PAID));
    List<Invoice> invoices = new JPAQuery(entityManager).from(qInvoice)
            .where(expression).limit(size).offset(page * size).list(qInvoice);
    return StreamSupport.stream(invoices.spliterator(), false)
            .map(invoice -> transformToDto(invoice)).collect(Collectors.toList());
  }
}
