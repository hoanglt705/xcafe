package com.s3s.ssm.view.controlpanel;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.s3s.ssm.dto.FoodTableDto;
import com.s3s.ssm.dto.InvoiceDto;
import com.s3s.ssm.dto.InvoiceStatus;

public class TableViewInfoHandler {
  private final Map<FoodTableDto, TableViewInformation> tableViewInforMap = new HashMap<>();

  public void add(InvoiceDto invoiceDto) {
    add(invoiceDto, null);
  }

  public void add(InvoiceDto invoiceDto, LocalTime time) {
    tableViewInforMap.put(invoiceDto.getFoodTable(), new TableViewInformation(invoiceDto, time));
  }

  public long servingFoodTableNumber() {
    return count(InvoiceStatus.SERVING);
  }

  public long servingFoodTableNumner(String areaCode) {
    return count(InvoiceStatus.SERVING, areaCode);
  }

  public List<FoodTableDto> getOperatingFoodTable() {
    return new ArrayList<FoodTableDto>(tableViewInforMap.keySet());
  }

  public long countBookingFoodTable() {
    return count(InvoiceStatus.BOOKING);
  }

  public long countBookingFoodTable(String areaCode) {
    return count(InvoiceStatus.BOOKING, areaCode);
  }

  private long count(InvoiceStatus status) {
    return tableViewInforMap.values().stream()
            .filter(info -> status.equals(info.getInvoiceDto().getInvoiceStatus())).count();
  }

  private long count(InvoiceStatus status, String areaCode) {
    int count = 0;
    for (FoodTableDto tableDto : tableViewInforMap.keySet()) {
      TableViewInformation tableViewInformation = tableViewInforMap.get(tableDto);
      if (tableDto.getArea().getCode().equals(areaCode)
              && status.equals(tableViewInformation.getInvoiceDto().getInvoiceStatus())) {
        count++;
      }
    }
    return count;
  }

  public InvoiceDto getInvoice(FoodTableDto foodTableDto) {
    if (tableViewInforMap.containsKey(foodTableDto)) {
      return getTableViewInformation(foodTableDto).getInvoiceDto();
    }
    return null;
  }

  public TableViewInformation getTableViewInformation(FoodTableDto foodTableDto) {
    return tableViewInforMap.get(foodTableDto);
  }

  public void setTime(FoodTableDto foodTable, LocalTime waitingTime) {
    tableViewInforMap.get(foodTable).setTime(waitingTime);

  }

  public void remove(FoodTableDto foodTable) {
    tableViewInforMap.remove(foodTable);
  }

  public class TableViewInformation {
    private InvoiceDto invoice;
    private LocalTime time;

    public TableViewInformation(InvoiceDto invoice, LocalTime time) {
      this.invoice = invoice;
      this.time = time;
    }

    public InvoiceDto getInvoiceDto() {
      return invoice;
    }

    public void setInvoiceDto(InvoiceDto invoiceDto) {
      this.invoice = invoiceDto;
    }

    public LocalTime getTime() {
      return time;
    }

    public void setTime(LocalTime time) {
      this.time = time;
    }

    public FoodTableDto getFoodTableDto() {
      return invoice.getFoodTable();
    }
  }
}
