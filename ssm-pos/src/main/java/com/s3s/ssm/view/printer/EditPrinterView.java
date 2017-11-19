/*
 * EditBanchView
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.s3s.ssm.view.printer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditPrinterView extends AEditServiceView<PrinterDto> {
  private static final long serialVersionUID = 728867266827208141L;
  private static List<String> SUPPORTED_PAPER_FORMAT = Arrays.asList("A4", "80 mm", "80 mmList");
  private static String invoicePrinterTab = ControlConfigUtils
          .getString("label.PrinterDto.invoicePrinterTab");
  private static String kitchenPrinterTab = ControlConfigUtils
          .getString("label.PrinterDto.kitchenPrinterTab");

  public EditPrinterView(Map<String, Object> request) {
    super(request);
  }

  @SuppressWarnings("unused")
  @Override
  protected boolean exists(String code) {
    return false;
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    List<String> printers = getPrinters();
    // detailDataModel.tab(invoicePrinterTab, invoicePrinterTab, null);
    detailDataModel.addAttribute("invoicePrinter", DetailFieldType.DROPDOWN).setDataList(printers);
    detailDataModel.addAttribute("invoicePaperSize", DetailFieldType.DROPDOWN).setDataList(
            SUPPORTED_PAPER_FORMAT);
    detailDataModel.addAttribute("invoicePrintNumber", DetailFieldType.POSITIVE_NUMBER_SPINNER);
    // detailDataModel.addAttribute("isPrintedAfterPayment", DetailFieldType.CHECKBOX);
    // detailDataModel.addAttribute("invoicePreview", DetailFieldType.CHECKBOX);

    // detailDataModel.tab(kitchenPrinterTab, kitchenPrinterTab, null);
    // detailDataModel.addAttribute("kitchenPrinter", DetailFieldType.DROPDOWN).setDataList(printers);
    // detailDataModel.addAttribute("kitchenPaperSize", DetailFieldType.DROPDOWN).setDataList(
    // SUPPORTED_PAPER_FORMAT);
    // detailDataModel.addAttribute("kitchenPreview", DetailFieldType.CHECKBOX);
  }

  private List<String> getPrinters() {
    List<String> result = new ArrayList<>();
    PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
    for (PrintService printService : printServices) {
      result.add(printService.getName());
    }
    return result;
  }

  @Override
  protected String getDefaultTitle(PrinterDto entity) {
    return entity.getCode();
  }

  @Override
  public IViewService<PrinterDto> getViewService() {
    return new PrinterServiceImpl();
  }
}
