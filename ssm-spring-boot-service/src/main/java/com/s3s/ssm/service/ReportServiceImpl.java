package com.s3s.ssm.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Projections;
import com.mysema.query.types.QBean;
import com.mysema.query.types.expr.BooleanExpression;
import com.s3s.ssm.dto.CompanyDto;
import com.s3s.ssm.dto.FinalPeriodSaleProcessDto;
import com.s3s.ssm.dto.FinalPeriodTableProcessDto;
import com.s3s.ssm.dto.FoodTableDto;
import com.s3s.ssm.dto.InvoiceStatus;
import com.s3s.ssm.dto.WarningProductDto;
import com.s3s.ssm.dto.report.EximportDto;
import com.s3s.ssm.dto.report.FinalPeriodFoodTableProcessDto;
import com.s3s.ssm.dto.report.FinalPeriodProductProcessDto;
import com.s3s.ssm.dto.report.FoodTableIncomeDto;
import com.s3s.ssm.dto.report.InvoiceIncomeDto;
import com.s3s.ssm.dto.report.ProductInStoreDto;
import com.s3s.ssm.dto.report.ProductIncomeDto;
import com.s3s.ssm.repo.FinalPeriodProductProcessRepository;
import com.s3s.ssm.repo.FinalPeriodSaleProcessRepository;
import com.s3s.ssm.repo.FinalPeriodTableProcessRepository;
import com.s3s.ssm.repo.FoodTableRepository;
import com.s3s.ssm.repo.InvoiceRepository;
import com.s3s.ssm.repo.MaterialRepository;
import com.s3s.ssm.util.InvoiceUtil;
import com.sunrise.xdoc.entity.catalog.Food;
import com.sunrise.xdoc.entity.catalog.FoodIngredient;
import com.sunrise.xdoc.entity.catalog.ImportPriceDetail;
import com.sunrise.xdoc.entity.catalog.Material;
import com.sunrise.xdoc.entity.catalog.Product;
import com.sunrise.xdoc.entity.catalog.QMaterial;
import com.sunrise.xdoc.entity.catalog.QProduct;
import com.sunrise.xdoc.entity.config.FoodTable;
import com.sunrise.xdoc.entity.config.QFoodTable;
import com.sunrise.xdoc.entity.sale.Invoice;
import com.sunrise.xdoc.entity.sale.QInvoice;
import com.sunrise.xdoc.entity.sale.QInvoiceDetail;
import com.sunrise.xdoc.entity.store.FinalPeriodProductProcess;
import com.sunrise.xdoc.entity.store.FinalPeriodSaleProcess;
import com.sunrise.xdoc.entity.store.FinalPeriodTableProcess;
import com.sunrise.xdoc.entity.store.QExportStoreDetail;
import com.sunrise.xdoc.entity.store.QFinalPeriodProductProcess;
import com.sunrise.xdoc.entity.store.QFinalPeriodSaleProcess;
import com.sunrise.xdoc.entity.store.QFinalPeriodTableProcess;
import com.sunrise.xdoc.entity.store.QImportStoreDetail;

@Component("reportService")
@Transactional
class ReportServiceImpl implements IReportService {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private IFinalPeriodProcessService finalPeriodProcessService;

  @Autowired
  private IFoodTableService foodTableService;
  @Autowired
  private ICompanyService companyService;

  @Autowired
  private FoodTableRepository foodTableRepository;

  @Autowired
  private MaterialRepository materialRepository;

  @Autowired
  private InvoiceRepository invoiceRepository;

  @Autowired
  private FinalPeriodTableProcessRepository finalPeriodTableProcessRepository;

  @Autowired
  private FinalPeriodSaleProcessRepository finalPeriodSaleProcessRepository;

  @Autowired
  private FinalPeriodProductProcessRepository finalPeriodProductProcessRepository;

  @Override
  public List<FoodTableIncomeDto> statisticFoodTableIncome(
          List<String> foodTables, Date fromDate, Date toDate) {
    List<FoodTableIncomeDto> result = new ArrayList<>();
    foodTables.stream().forEach(foodTable -> {
      result.add(getFoodTableIncome(foodTable, fromDate, toDate));
    });
    return result;
  }

  private FoodTableIncomeDto getFoodTableIncome(String foodTableCode,
          Date fromDate, Date toDate) {
    FoodTableDto foodTable = findFoodTable(foodTableCode);
    if (fromDate != null && fromDate.after(toDate)) {
      return new FoodTableIncomeDto(foodTable.getCode(),
              foodTable.getName(), 0l);
    }

    QInvoice qInvoice = QInvoice.invoice;

    BooleanExpression expression = qInvoice.active.eq(true);
    expression = expression.and(qInvoice.invoiceStatus.eq(InvoiceStatus.PAID));
    expression = expression.and(qInvoice.foodTable.active.eq(true));

    if (fromDate != null) {
      expression = expression.and(qInvoice.createdDate.gt(fromDate));
    }
    if (toDate != null) {
      expression = expression.and(qInvoice.createdDate.lt(toDate));
    }

    expression = expression.and(qInvoice.foodTable.code.eq(foodTableCode));

    return new FoodTableIncomeDto(foodTable.getCode(), foodTable.getName(),
            getTotalIncome(foodTableCode, fromDate, toDate));
  }

  public FoodTableDto findFoodTable(String foodTable) {
    QFoodTable qFoodTable = QFoodTable.foodTable;
    QBean<FoodTableDto> qBean = Projections.bean(FoodTableDto.class,
            qFoodTable.code, qFoodTable.name);
    return new JPAQuery(entityManager).from(qFoodTable)
            .where(qFoodTable.code.eq(foodTable)).singleResult(qBean);
  }

  @Override
  public List<ProductIncomeDto> statisticProductIncome(List<String> products,
          Date fromDate, Date toDate) {
    List<ProductIncomeDto> dtos = new ArrayList<>();
    Long interestAmtTotal = 0l;
    for (String productCode : products) {
      ProductIncomeDto productIncome = getProductIncome(productCode, fromDate, toDate);
      dtos.add(productIncome);
      interestAmtTotal = productIncome.getInterestAmt();
    }
    if (interestAmtTotal != 0) {
      for (ProductIncomeDto productIncomeDto : dtos) {
        float incomePercent = productIncomeDto.getInterestAmt().floatValue() / interestAmtTotal;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        productIncomeDto.setIncomePercent(Float.valueOf(decimalFormat.format(incomePercent)));
      }
    }
    return dtos;
  }

  @Override
  public List<EximportDto> statisticEximport(List<String> productTypess, Date fromDate, Date toDate) {
    return Collections.emptyList();
  }

  @Override
  public List<ProductInStoreDto> statisticProductInStore(List<String> products) {
    QMaterial qMaterial = QMaterial.material;
    List<ProductInStoreDto> dtos = new ArrayList<>();
    for (String productCode : products) {
      BooleanExpression expression = qMaterial.code.eq(productCode);
      Tuple tuple = new JPAQuery(entityManager)
              .from(qMaterial)
              .where(expression)
              .singleResult(qMaterial.code, qMaterial.name, qMaterial.uom,
                      qMaterial.quantityInStock);
      ProductInStoreDto dto = new ProductInStoreDto();
      dto.setProductCode(tuple.get(qMaterial.code));
      dto.setProductName(tuple.get(qMaterial.name));
      dto.setUnit(tuple.get(qMaterial.uom).getName());
      dto.setQuantity(tuple.get(qMaterial.quantityInStock));
      dtos.add(dto);
    }
    return dtos;
  }

  @Override
  public List<InvoiceIncomeDto> statisticInvoiceIncome(Date fromDate, Date toDate) {
    QInvoice qInvoice = QInvoice.invoice;
    BooleanExpression expression = qInvoice.invoiceStatus.eq(InvoiceStatus.PAID);

    if (fromDate != null) {
      expression = expression.and(qInvoice.createdDate.goe(fromDate));
    }

    if (toDate != null) {
      expression = expression.and(qInvoice.createdDate.loe(toDate));
    }
    QBean<InvoiceIncomeDto> qBean = Projections.bean(
            InvoiceIncomeDto.class, qInvoice.code,
            qInvoice.createdDate, qInvoice.foodTable.name, qInvoice.totalAmount, qInvoice.discount,
            qInvoice.income);
    return new JPAQuery(entityManager).from(qInvoice).where(expression)
            .list(qBean);
  }

  private ProductIncomeDto getProductIncome(String productCode,
          Date fromDate, Date toDate) {
    QInvoiceDetail qInvoiceDetail = QInvoiceDetail.invoiceDetail;

    BooleanExpression expression = qInvoiceDetail.invoice.active.eq(true);
    expression = expression.and(qInvoiceDetail.invoice.invoiceStatus
            .eq(InvoiceStatus.PAID));
    expression = expression.and(qInvoiceDetail.invoice.foodTable.active
            .eq(true));
    expression = expression
            .and(qInvoiceDetail.product.code.eq(productCode));

    if (fromDate != null) {
      expression = expression.and(qInvoiceDetail.invoice.createdDate
              .gt(fromDate));
    }
    if (toDate != null) {
      expression = expression.and(qInvoiceDetail.invoice.createdDate
              .lt(toDate));
    }

    Tuple tuple = new JPAQuery(entityManager)
            .from(qInvoiceDetail)
            .where(expression)
            .singleResult(qInvoiceDetail.quantity.sum().as("quantity"),
                    qInvoiceDetail.amount.sum().as("amount"));

    QProduct qProduct = QProduct.product;
    Product product = new JPAQuery(entityManager).from(qProduct)
            .where(qProduct.code.eq(productCode)).singleResult(qProduct);

    ProductIncomeDto dto = new ProductIncomeDto();
    dto.setProductCode(product.getCode());
    dto.setProductName(product.getName());
    dto.setUnit(product.getUom().getName());
    Long sellPrice = product.getSellPrice();
    dto.setSellPrice(sellPrice);

    Integer quantity = tuple.get(0, Integer.class) != null ? tuple.get(0, Integer.class) : 0;
    dto.setQuantity(quantity);
    long sellTotalAmt = quantity * sellPrice;
    dto.setSellTotalAmt(sellTotalAmt);
    long importPrice = getImportPrice(product);
    dto.setImportPrice(importPrice);
    long importTotalAmt = importPrice * quantity;
    dto.setImportTotalAmt(importTotalAmt);
    dto.setInterestAmt(sellTotalAmt - importTotalAmt);
    return dto;
  }

  private long getImportPrice(Product product) {
    if (product instanceof Material) {
      return getImportPrice((Material) product);
    }
    return getImportPrice((Food) product);
  }

  private long getImportPrice(Material material) {
    long importPriceUnit = 0;
    for (ImportPriceDetail importPrice : material.getImportPrices()) {
      if (importPrice.isMainSupplier()) {
        importPriceUnit += importPrice.getPrice();
      }
    }
    return importPriceUnit;
  }

  private long getImportPrice(Food food) {
    long importPriceUnit = 0;
    for (FoodIngredient ingredient : food.getFoodIngredients()) {
      Material material = ingredient.getMaterial();
      importPriceUnit += getImportPrice(material);
    }
    return importPriceUnit;
  }

  @Override
  public List<WarningProductDto> statisticWarningProduct(int firstIndex, int maxResults) {
    QMaterial qMaterial = QMaterial.material;
    BooleanExpression expression = qMaterial.quantityInStock.loe(qMaterial.minimumQuantity);
    List<Tuple> list = new JPAQuery(entityManager)
            .from(qMaterial)
            .where(expression)
            .offset(firstIndex)
            .limit(maxResults)
            .list(qMaterial.code, qMaterial.name, qMaterial.uom,
                    qMaterial.quantityInStock, qMaterial.minimumQuantity);

    List<WarningProductDto> dtos = new ArrayList<>();
    for (Tuple tuple : list) {
      WarningProductDto dto = new WarningProductDto();
      dto.setProductCode(tuple.get(qMaterial.code));
      dto.setProductName(tuple.get(qMaterial.name));
      dto.setUomName(tuple.get(qMaterial.uom).getName());
      dto.setMinimumQuantity(tuple.get(qMaterial.minimumQuantity));
      dto.setQuantity(tuple.get(qMaterial.quantityInStock));
      dtos.add(dto);
    }
    return dtos;
  }

  @Override
  public List<Date> findAllProcessFinalPeriod(Date fromDate, Date toDate) {
    QFinalPeriodSaleProcess qFinalPeriodSaleProcess = QFinalPeriodSaleProcess.finalPeriodSaleProcess;
    return new JPAQuery(entityManager)
            .from(qFinalPeriodSaleProcess)
            .where(qFinalPeriodSaleProcess.processingDate.between(fromDate,
                    toDate)).list(qFinalPeriodSaleProcess.processingDate);
  }

  @Override
  public Map<String, List<?>> processFinalPeriod() {
    Map<String, List<?>> resultMap = new HashMap<>();
    Date processTime = new Date();
    List<FinalPeriodProductProcessDto> finalMaterialPeriod = processFinalMaterialPeriod(processTime);
    List<FinalPeriodFoodTableProcessDto> finalFoodTablePeriod = processFinalFoodTablePeriod(processTime);
    FinalPeriodSaleProcessDto processFinalSalePeriod = processFinalSalePeriod(processTime);

    resultMap.put(FINAL_MATERIAL_PERIOD, finalMaterialPeriod);
    resultMap.put(FINAL_FOOD_TABLE_PERIOD, finalFoodTablePeriod);
    resultMap.put(PROCESS_FINAL_SALE_PERIOD,
            Arrays.asList(processFinalSalePeriod));
    return resultMap;
  }

  @Override
  public Map<String, List<?>> findProcessFinalPeriod(Date selectedDate) {
    Map<String, List<?>> resultMap = new HashMap<>();
    resultMap.put(FINAL_MATERIAL_PERIOD, DtoHelper
            .generateFinalPeriodProductProcessDto(findLatestFinalProductPeriodProcess(selectedDate)));
    resultMap
            .put(FINAL_FOOD_TABLE_PERIOD,
                    DtoHelper
                            .generateFinalPeriodFoodTableProcessDto(findLatestFinalFoodTablePeriodProcess(selectedDate)));
    resultMap.put(PROCESS_FINAL_SALE_PERIOD,
            Arrays.asList(findLatestFinalPeriodSaleProcess(selectedDate)));
    return resultMap;
  }

  private FinalPeriodSaleProcessDto processFinalSalePeriod(Date processTime) {
    FinalPeriodSaleProcess process = new FinalPeriodSaleProcess();
    process.setProcessingDate(processTime);
    process.setSaleTotal(getTotalSale());
    process.setInvoiceTotal(getTotalInvoice());
    finalPeriodSaleProcessRepository.save(process);
    return DtoHelper.generateFinalPeriodSaleProcessDto(process);
  }

  @Override
  public long getTotalInvoice() {
    FinalPeriodSaleProcessDto latestProcessing = finalPeriodProcessService
            .findLatestFinalPeriodSaleProcess();
    Long totalInvoice = 0l;
    if (latestProcessing != null) {
      totalInvoice += latestProcessing.getInvoiceTotal();
      totalInvoice += countInvoice(latestProcessing.getProcessingDate());
    } else {
      totalInvoice += countInvoice();
    }
    return totalInvoice;
  }

  @Override
  public Long getTotalSale() {
    FinalPeriodSaleProcessDto latestProcessing = finalPeriodProcessService
            .findLatestFinalPeriodSaleProcess();
    Long totalAmount = 0l;
    if (latestProcessing != null
            && latestProcessing.getTotalAmount() != null) {
      totalAmount += latestProcessing.getTotalAmount();
    } else {
      totalAmount += getTotalAmount();
    }
    return totalAmount;
  }

  @Override
  public Long getTotalIncome(Date fromDate) {
    return getTotalIncome(fromDate, null);
  }

  private List<FinalPeriodProductProcessDto> processFinalMaterialPeriod(
          Date processTime) {
    Iterable<Material> materials = materialRepository.findByActive(true);

    List<FinalPeriodProductProcessDto> result = new ArrayList<>();

    for (Material material : materials) {
      FinalPeriodProductProcess latestProcessing = findLatestFinalProductPeriodProcess(
              material.getCode(), processTime);
      result.add(generateFinalPeriodProductProcessDto(material,
              processTime, latestProcessing));
    }
    return result;
  }

  public FinalPeriodProductProcessDto generateFinalPeriodProductProcessDto(
          Material material, Date processTime,
          FinalPeriodProductProcess latestProcessing) {
    Date latestProcessingDate = (latestProcessing != null) ? latestProcessing
            .getProcessingDate() : null;
    double latestQuantity = (latestProcessing != null) ? latestProcessing
            .getQuantityInStock() : 0;

    int importQuantity = getImportedQuantity(material.getCode(),
            latestProcessingDate, processTime);
    int exportQuantity = getExportedQuantity(material.getCode(),
            latestProcessingDate, processTime);
    double soldQuantity = getSoldQuantity(material.getId(),
            latestProcessingDate, processTime);
    double quantityInStock = latestQuantity + importQuantity - soldQuantity
            - exportQuantity;

    long latestTotalAmount = (latestProcessing != null) ? latestProcessing
            .getTotalAmount() : 0;
    long totalAmount = latestTotalAmount;
    if (material.getRetailable()) {
      totalAmount += getSoldTotalAmount(material, latestProcessingDate);
    }

    FinalPeriodProductProcess process = new FinalPeriodProductProcess();
    process.setProcessingDate(processTime);
    process.setProduct(material);
    process.setUom(material.getUom());
    process.setQuantityInStock(quantityInStock);
    process.setImportQuantity(importQuantity);
    process.setExportQuantity(exportQuantity);
    process.setSoldQuantity(soldQuantity);
    process.setTotalAmount(totalAmount);
    finalPeriodProductProcessRepository.save(process);
    return DtoHelper.generateFinalPeriodProductProcessDto(process);
  }

  private long getSoldTotalAmount(Material material, Date latestProcessingDate) {
    QInvoiceDetail qInvoiceDetail = QInvoiceDetail.invoiceDetail;
    BooleanExpression expression = qInvoiceDetail.product.eq(material);

    if (latestProcessingDate != null) {
      expression = expression.and(qInvoiceDetail.invoice.createdDate
              .goe(latestProcessingDate));
    }

    Long result = new JPAQuery(entityManager).from(qInvoiceDetail)
            .where(expression).uniqueResult(qInvoiceDetail.amount.sum());
    return result != null ? result : 0;
  }

  @Override
  public double getSoldQuantity(Long materialId, Date fromDate, Date toDate) {
    QInvoice qInvoice = QInvoice.invoice;
    BooleanExpression expression = qInvoice.invoiceStatus.eq(
            InvoiceStatus.PAID).or(
            qInvoice.invoiceStatus.eq(InvoiceStatus.SERVING));

    if (fromDate != null) {
      expression = expression.and(qInvoice.createdDate.gt(fromDate));
    }

    if (toDate != null) {
      expression = expression.and(qInvoice.createdDate.loe(toDate));
    }

    List<Invoice> invoices = new JPAQuery(entityManager).from(qInvoice)
            .where(expression).list(qInvoice);

    double result = 0d;
    Material material = materialRepository.findOne(materialId);
    InvoiceUtil invoiceUtil = new InvoiceUtil();
    for (Invoice invoice : invoices) {
      result += invoiceUtil.findMaterialQuantityInInvoice(invoice,
              material);
    }
    return result;
  }

  private FinalPeriodProductProcess findLatestFinalProductPeriodProcess(
          String productCode, Date beforeDate) {
    QFinalPeriodProductProcess process = QFinalPeriodProductProcess.finalPeriodProductProcess;
    BooleanExpression booleanExpression = process.processingDate.before(beforeDate);
    booleanExpression = booleanExpression.and(process.product.code.eq(productCode));
    return new JPAQuery(entityManager).from(process).orderBy(
            new OrderSpecifier<>(Order.DESC, process.processingDate))
            .where(booleanExpression).singleResult(process);
  }

  private List<FinalPeriodProductProcess> findLatestFinalProductPeriodProcess(
          Date selectedDate) {
    QFinalPeriodProductProcess process = QFinalPeriodProductProcess.finalPeriodProductProcess;
    BooleanExpression booleanExpression = process.processingDate.eq(selectedDate);
    return new JPAQuery(entityManager).from(process).where(booleanExpression).list(process);
  }

  private List<FinalPeriodTableProcess> findLatestFinalFoodTablePeriodProcess(Date selectedDate) {
    QFinalPeriodTableProcess process = QFinalPeriodTableProcess.finalPeriodTableProcess;
    BooleanExpression booleanExpression = process.processingDate.eq(selectedDate);
    return new JPAQuery(entityManager).from(process).where(booleanExpression).list(process);
  }

  private FinalPeriodSaleProcessDto findLatestFinalPeriodSaleProcess(Date selectedDate) {
    QFinalPeriodSaleProcess process = QFinalPeriodSaleProcess.finalPeriodSaleProcess;
    BooleanExpression booleanExpression = process.processingDate.eq(selectedDate);

    QBean<FinalPeriodSaleProcessDto> qBean = Projections.bean(
            FinalPeriodSaleProcessDto.class, process.processingDate,
            process.totalAmount, process.saleTotal, process.invoiceTotal);
    return new JPAQuery(entityManager).from(process)
            .where(booleanExpression).singleResult(qBean);
  }

  private int getInStoreQuantity(String materialCode, Date toDate) {
    QExportStoreDetail qExportStoreDetail = QExportStoreDetail.exportStoreDetail;
    BooleanExpression expression = qExportStoreDetail.material.code
            .eq(materialCode);
    if (toDate != null) {
      expression = expression.and(qExportStoreDetail.exportStoreForm.createdDate.loe(toDate));
    }
    Integer quantitySum = new JPAQuery(entityManager)
            .from(qExportStoreDetail).where(expression)
            .uniqueResult(qExportStoreDetail.quantity.sum());
    return quantitySum != null ? quantitySum : 0;
  }

  @Override
  public int getExportedQuantity(String materialCode, Date fromDate, Date toDate) {
    QExportStoreDetail qExportStoreDetail = QExportStoreDetail.exportStoreDetail;
    BooleanExpression expression = qExportStoreDetail.material.code
            .eq(materialCode);
    if (fromDate != null) {
      expression = expression
              .and(qExportStoreDetail.exportStoreForm.createdDate
                      .gt(fromDate));
    }
    if (toDate != null) {
      expression = expression
              .and(qExportStoreDetail.exportStoreForm.createdDate
                      .loe(toDate));
    }

    Integer quantitySum = new JPAQuery(entityManager)
            .from(qExportStoreDetail).where(expression)
            .uniqueResult(qExportStoreDetail.quantity.sum());
    return quantitySum != null ? quantitySum : 0;
  }

  @Override
  public int getImportedQuantity(String materialCode, Date fromDate, Date toDate) {
    QImportStoreDetail qImportStoreDetail = QImportStoreDetail.importStoreDetail;
    BooleanExpression expression = qImportStoreDetail.material.code
            .eq(materialCode);
    if (fromDate != null) {
      expression = expression
              .and(qImportStoreDetail.importStoreForm.createdDate
                      .gt(fromDate));
    }
    if (toDate != null) {
      expression = expression
              .and(qImportStoreDetail.importStoreForm.createdDate
                      .loe(toDate));
    }

    Integer quantitySum = new JPAQuery(entityManager)
            .from(qImportStoreDetail).where(expression)
            .uniqueResult(qImportStoreDetail.quantity.sum());
    return quantitySum != null ? quantitySum : 0;
  }

  private List<FinalPeriodFoodTableProcessDto> processFinalFoodTablePeriod(Date processTime) {
    Iterable<FoodTable> foodTables = foodTableRepository.findByActive(true);
    List<FinalPeriodFoodTableProcessDto> result = new ArrayList<>();
    for (FoodTable table : foodTables) {
      FinalPeriodTableProcessDto latestProcessing = finalPeriodProcessService
              .findLatestFinalPeriodTableProcess(table.getCode());
      long totalAmount = 0l;
      if (latestProcessing != null) {
        totalAmount += latestProcessing.getTotalAmount();
        totalAmount += getTotalIncome(table.getCode(),
                latestProcessing.getProcessingDate());
      } else {
        totalAmount += getTotalIncome(table.getCode());
      }

      FinalPeriodTableProcess process = new FinalPeriodTableProcess();
      process.setProcessingDate(processTime);
      process.setFoodTable(table);
      process.setTotalAmount(totalAmount);

      finalPeriodTableProcessRepository.save(process);

      result.add(DtoHelper
              .generateFinalPeriodFoodTableProcessDto(process));
    }
    return result;
  }

  private long getTotalIncome(String foodTableCode) {
    return getTotalIncome(foodTableCode, null, null);
  }

  private long getTotalIncome(String foodTableCode, Date fromDate) {
    return getTotalIncome(foodTableCode, fromDate, null);
  }

  private long getTotalAmount() {
    Calendar calendar = Calendar.getInstance();
    calendar.clear();
    return getTotalIncome(calendar.getTime());
  }

  private long getTotalIncome(Date fromDate, Date toDate) {
    QInvoice qInvoice = QInvoice.invoice;
    BooleanExpression expression = qInvoice.invoiceStatus
            .eq(InvoiceStatus.PAID);

    if (fromDate != null) {
      expression = expression.and(qInvoice.createdDate.goe(fromDate));
    }

    if (toDate != null) {
      expression = expression.and(qInvoice.createdDate.loe(toDate));
    }

    Long totalAmount = new JPAQuery(entityManager).from(qInvoice).where(expression)
            .singleResult(qInvoice.income.sum());
    return totalAmount != null ? totalAmount : 0;
  }

  @Override
  public long getTotalIncome(String foodTableCode, Date fromDate, Date toDate) {
    QInvoice qInvoice = QInvoice.invoice;
    BooleanExpression expression = qInvoice.invoiceStatus.eq(InvoiceStatus.PAID);

    if (foodTableCode != null) {
      expression = expression.and(qInvoice.foodTable.code.eq(foodTableCode));
    }

    if (fromDate != null) {
      expression = expression.and(qInvoice.createdDate.goe(fromDate));
    }

    if (toDate != null) {
      expression = expression.and(qInvoice.createdDate.loe(toDate));
    }

    Long totalAmount = new JPAQuery(entityManager).from(qInvoice).where(expression)
            .singleResult(qInvoice.income.sum());
    return totalAmount != null ? totalAmount : 0;
  }

  private long countInvoice() {
    return countInvoice(null);
  }

  @Override
  public long countInvoice(Date fromDate) {
    return countInvoiceInBetween(fromDate, null);
  }

  @Override
  public long countInvoiceInBetween(Date fromDate, Date toDate) {
    QInvoice qInvoice = QInvoice.invoice;
    BooleanExpression expression = qInvoice.invoiceStatus
            .eq(InvoiceStatus.PAID);
    if (fromDate != null) {
      expression = expression.and(qInvoice.createdDate.goe(fromDate));
    }
    if (toDate != null) {
      expression = expression.and(qInvoice.createdDate.loe(toDate));
    }

    return new JPAQuery(entityManager).from(qInvoice).where(expression)
            .count();
  }

  @Override
  public long[] getTodaySaleOfWholeDay() {
    CompanyDto company = companyService.getCompany();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(company.getOpenTime());
    long openHour = calendar.get(Calendar.HOUR_OF_DAY);
    calendar.setTime(company.getCloseTime());
    long closeHour = calendar.get(Calendar.HOUR_OF_DAY);
    return getTodaySale(openHour, closeHour);
  }

  @Override
  public long[] getTodaySale(long openHour, long closeHour) {
    int period = (int) (closeHour - openHour + 1);
    long[] result = new long[period];
    for (int i = 0; i < period; i++) {
      Calendar fromCalendar = Calendar.getInstance();
      int startTime = (int) (i + openHour);
      fromCalendar.set(Calendar.HOUR_OF_DAY, startTime);
      Date fromDate = DateUtils.truncate(fromCalendar.getTime(), Calendar.HOUR);

      Calendar toCalendar = Calendar.getInstance();
      toCalendar.set(Calendar.HOUR_OF_DAY, startTime + 1);
      Date toDate = DateUtils.truncate(toCalendar.getTime(), Calendar.HOUR);

      result[i] = getTotalIncome(fromDate, toDate);
    }
    return result;
  }

  @Override
  public long[] getThisWeekSale() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    Date sunday = DateUtils.truncate(calendar.getTime(), Calendar.DATE);
    Date monday = DateUtils.addDays(sunday, 1);
    Date tuesday = DateUtils.addDays(sunday, 2);
    Date wednesday = DateUtils.addDays(sunday, 3);
    Date thursday = DateUtils.addDays(sunday, 4);
    Date friday = DateUtils.addDays(sunday, 5);
    Date saturtday = DateUtils.addDays(sunday, 6);
    Date nextSunday = DateUtils.addDays(sunday, 7);

    long[] result = new long[7];

    result[0] = getTotalIncome(sunday, monday);
    result[1] = getTotalIncome(monday, tuesday);
    result[2] = getTotalIncome(tuesday, wednesday);
    result[3] = getTotalIncome(wednesday, thursday);
    result[4] = getTotalIncome(thursday, friday);
    result[5] = getTotalIncome(friday, saturtday);
    result[6] = getTotalIncome(saturtday, nextSunday);

    return result;
  }

  @Override
  public long[] getThisMonthSale() {
    int numberOfDay = Calendar.getInstance().getActualMaximum(
            Calendar.DAY_OF_MONTH);
    long[] result = new long[numberOfDay];
    for (int i = 0; i < numberOfDay; i++) {
      Calendar fromCalendar = Calendar.getInstance();
      fromCalendar.set(Calendar.DAY_OF_MONTH, i + 1);
      Date fromDate = DateUtils.truncate(fromCalendar, Calendar.DATE).getTime();

      Calendar toCalendar = Calendar.getInstance();
      toCalendar.set(Calendar.DAY_OF_MONTH, i + 1);
      toCalendar.set(Calendar.HOUR_OF_DAY, 23);
      toCalendar.set(Calendar.MINUTE, 59);
      toCalendar.set(Calendar.SECOND, 59);

      Date toDate = toCalendar.getTime();
      result[i] = getTotalIncome(fromDate, toDate);
    }
    return result;
  }

  @Override
  public long[] getThisYearSale() {
    long[] result = new long[12];
    for (int i = 0; i < 12; i++) {
      Calendar fromCalendar = Calendar.getInstance();
      fromCalendar.set(Calendar.MONTH, i);

      fromCalendar = DateUtils.truncate(fromCalendar, Calendar.MONTH);

      Calendar toCalendar = Calendar.getInstance();
      toCalendar.set(Calendar.MONTH, i);
      toCalendar.set(Calendar.DAY_OF_MONTH,
              toCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
      toCalendar.set(Calendar.HOUR_OF_DAY, 23);
      toCalendar.set(Calendar.MINUTE, 59);
      toCalendar.set(Calendar.SECOND, 59);
      Date fromTime = fromCalendar.getTime();
      if (i == 1) {
        fromTime = DateUtils.setMonths(fromTime, 1);
      }

      result[i] = getTotalIncome(fromTime, toCalendar.getTime());
    }
    return result;
  }
}