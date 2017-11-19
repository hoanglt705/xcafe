package com.s3s.ssm.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.s3s.ssm.dto.WarningProductDto;
import com.s3s.ssm.dto.report.EximportDto;
import com.s3s.ssm.dto.report.FoodTableIncomeDto;
import com.s3s.ssm.dto.report.InvoiceIncomeDto;
import com.s3s.ssm.dto.report.ProductInStoreDto;
import com.s3s.ssm.dto.report.ProductIncomeDto;

public interface IReportService {
  public static final String PROCESS_FINAL_SALE_PERIOD = "processFinalSalePeriod";

  public static final String FINAL_FOOD_TABLE_PERIOD = "finalFoodTablePeriod";

  public static final String FINAL_MATERIAL_PERIOD = "finalMaterialPeriod";

  List<FoodTableIncomeDto> statisticFoodTableIncome(List<String> foodTables, Date fromDate, Date toDate);

  List<ProductIncomeDto> statisticProductIncome(List<String> products, Date fromDate, Date toDate);

  List<WarningProductDto> statisticWarningProduct(int firstIndex, int maxResults);

  Map<String, List<?>> processFinalPeriod();

  Long getTotalSale();

  Long getTotalIncome(Date fromDate);

  long getTotalInvoice();

  long[] getThisWeekSale();

  long[] getThisMonthSale();

  long[] getThisYearSale();

  List<Date> findAllProcessFinalPeriod(Date fromDate, Date toDate);

  Map<String, List<?>> findProcessFinalPeriod(Date selectedDate);

  long countInvoiceInBetween(Date fromDate, Date toDate);

  long countInvoice(Date fromDate);

  int getImportedQuantity(String materialCode, Date fromDate, Date toDate);

  int getExportedQuantity(String materialCode, Date fromDate, Date toDate);

  double getSoldQuantity(Long materialCode, Date fromDate, Date toDate);

  long getTotalIncome(String foodTableCode, Date fromDate, Date toDate);

  long[] getTodaySaleOfWholeDay();

  long[] getTodaySale(long openHour, long closeHour);

  List<InvoiceIncomeDto> statisticInvoiceIncome(Date fromDate, Date toDate);

  List<ProductInStoreDto> statisticProductInStore(List<String> products);

  List<EximportDto> statisticEximport(List<String> products, Date fromDate, Date toDate);

}
