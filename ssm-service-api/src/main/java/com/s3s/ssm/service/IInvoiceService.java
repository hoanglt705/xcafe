package com.s3s.ssm.service;

import java.util.List;
import java.util.Map;

import com.s3s.ssm.dto.FoodTableDto;
import com.s3s.ssm.dto.InvoiceDto;
import com.s3s.ssm.dto.InvoiceStatus;
import com.s3s.ssm.dto.LatestInvoiceDto;
import com.s3s.ssm.dto.ProductDto;

public interface IInvoiceService extends IViewService<InvoiceDto> {
  List<LatestInvoiceDto> getLatestInvoice(int size);

  List<InvoiceDto> getOperatingInvoice();

  void updateStatus(String code, InvoiceStatus status);

  InvoiceDto combine2Invoices(InvoiceDto fromInvoice, InvoiceDto toInvoice);

  void updateInvoiceDetail(InvoiceDto dto);

  InvoiceDto moveInvoice(InvoiceDto fromInvoice, FoodTableDto toTableCode);

  void payInvoice(InvoiceDto invoice);

  void updateProductQuantityInStore(Map<ProductDto, Integer> transaction);

  InvoiceDto createNewInvoice(FoodTableDto foodTableDto);

  void cancelInvoice(InvoiceDto invoiceDto);

  long countInvoice(InvoiceStatus status);

  void updateQuantityOfProductInStore(InvoiceDto transferToDto);
}
