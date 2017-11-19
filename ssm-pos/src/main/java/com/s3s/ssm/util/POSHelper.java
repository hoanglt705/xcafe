package com.s3s.ssm.util;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;

import com.s3s.ssm.config.PosContextProvider;
import com.s3s.ssm.dto.InvoiceDetailDto;
import com.s3s.ssm.dto.InvoiceDto;
import com.s3s.ssm.service.ICompanyService;
import com.s3s.ssm.view.printer.PrinterDto;
import com.s3s.ssm.view.printer.PrinterServiceImpl;

public class POSHelper {
  private static final String COMPANY_NAME_PARAM = "COMPANY_NAME_PARAM";
  private static final String ADDRESS_PARAM = "ADDRESS_PARAM";
  private static final String FIX_PHONE_PARAM = "FIX_PHONE_PARAM";
  private static final String TOTAL_AMOUNT_PARAM = "TOTAL_AMOUNT_PARAM";
  private static final String TOTAL_QUANTITY_PARAM = "TOTAL_QUANTITY_PARAM";
  private static final String TABLE_PARAM = "TABLE_PARAM";
  private static final String TOTAL_PAYMENT_PARAM = "TOTAL_PAYMENT_PARAM";
  private static final String TOTAL_RETURN_PARAM = "TOTAL_RETURN_PARAM";
  private static final String TOTAL_DISCOUNT_PARAM = "TOTAL_DISCOUNT_PARAM";
  private static final String PERCENT_VAT_PARAM = "PERCENT_VAT_PARAM";
  private static final String AMOUNT_VAT_PARAM = "AMOUNT_VAT_PARAM";
  private ICompanyService companyService = PosContextProvider.getInstance().getCompanyService();
  private PrinterDto printerDto = new PrinterServiceImpl().findOne(1L);

  public void printInvoice(InvoiceDto invoice) {
    setPrinterDefault();
    JasperPrint jasperPrint = generateJasperSprint(invoice);
    JasperViewer.viewReport(jasperPrint, false);
    JRPrintServiceExporter exporter = new JRPrintServiceExporter();
    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
    exporter.setConfiguration(generatePrintConfiguration());
    try {
      exporter.exportReport();
    } catch (JRException e) {
      e.printStackTrace();
    }
  }

  private JasperPrint generateJasperSprint(InvoiceDto invoice) {
    Map<String, Object> reportParameters = new HashMap<>();
    reportParameters.put(COMPANY_NAME_PARAM, companyService.getCompany().getName());
    reportParameters.put(ADDRESS_PARAM, companyService.getCompany().getAddress());
    reportParameters.put(FIX_PHONE_PARAM, companyService.getCompany().getFixPhone());
    reportParameters.put(TOTAL_AMOUNT_PARAM, invoice.getTotalAmount());
    reportParameters.put(TABLE_PARAM, invoice.getFoodTable().getName());
    reportParameters.put(TOTAL_PAYMENT_PARAM, invoice.getTotalPaymentAmount());
    reportParameters.put(TOTAL_RETURN_PARAM, invoice.getTotalReturnAmount());
    reportParameters.put(TOTAL_DISCOUNT_PARAM, invoice.getDiscount());
    reportParameters.put(PERCENT_VAT_PARAM, invoice.getVatPercent());
    reportParameters.put(AMOUNT_VAT_PARAM, invoice.getVatTax());
    List<InvoiceDetailDto> invoiceDetails = invoice.getInvoiceDetails();
    List<InvoiceDetailPrinterDto> printerDtos = new ArrayList<>();
    int quantityTotal = 0;
    for (int i = 0; i < invoiceDetails.size(); i++) {
      printerDtos.add(transferToPrint(i + 1, invoiceDetails.get(i)));
      quantityTotal += invoiceDetails.get(i).getQuantity();
    }
    reportParameters.put(TOTAL_QUANTITY_PARAM, quantityTotal);
    JRDataSource dataSource = new JRBeanCollectionDataSource(printerDtos, false);
    try (InputStream resourceAsStream = POSHelper.class.getResourceAsStream("/reports/invoice.jasper");) {
      return JasperFillManager.fillReport(resourceAsStream, reportParameters, dataSource);
    } catch (JRException | IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private SimplePrintServiceExporterConfiguration generatePrintConfiguration() {
    SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
    configuration.setDisplayPageDialog(false);
    configuration.setDisplayPrintDialog(false);
    PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
    printRequestAttributeSet.add(new Copies(printerDto.getInvoicePrintNumber()));
    configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
    return configuration;
  }

  private void setPrinterDefault() {
    PrinterJob printerJob = PrinterJob.getPrinterJob();
    PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
    PrintService selectedService = null;
    if (services != null && services.length != 0) {
      for (PrintService service : services) {
        String existingPrinter = service.getName();
        if (existingPrinter.equals(printerDto.getInvoicePrinter())) {
          selectedService = service;
          break;
        }
      }
    }
    try {
      printerJob.setPrintService(selectedService);
    } catch (PrinterException e) {
      e.printStackTrace();
    }

  }

  public void printKitchen(InvoiceDto invoice) {
    Map<String, Object> reportParameters = new HashMap<>();
    reportParameters.put(COMPANY_NAME_PARAM, companyService.getCompany().getName());
    reportParameters.put(FIX_PHONE_PARAM, companyService.getCompany().getFixPhone());
    reportParameters.put(TOTAL_AMOUNT_PARAM, invoice.getTotalAmount());
    List<InvoiceDetailDto> invoiceDetails = invoice.getInvoiceDetails();
    List<InvoiceDetailPrinterDto> printerDtos = new ArrayList<>();
    for (int i = 0; i < invoiceDetails.size(); i++) {
      printerDtos.add(transferToPrint(i + 1, invoiceDetails.get(i)));
    }
    JRDataSource dataSource = new JRBeanCollectionDataSource(printerDtos, false);
    InputStream resourceAsStream = POSHelper.class.getResourceAsStream("/reports/invoice.jasper");
    // try {
    // // JasperPrint jasperPrint = generateJasperSprint(reportParameters, dataSource, resourceAsStream);
    // // JasperPrintManager.printReport(jasperPrint, true);
    // } catch (JRException e) {
    // e.printStackTrace();
    // }
  }

  private InvoiceDetailPrinterDto transferToPrint(int no, InvoiceDetailDto dto) {
    InvoiceDetailPrinterDto printerDto = new InvoiceDetailPrinterDto();
    printerDto.setNo(no);
    printerDto.setProductName(dto.getProduct().getName());
    printerDto.setQuantity(dto.getQuantity());
    printerDto.setUnitPrice(dto.getUnitPrice());
    printerDto.setAmount(dto.getAmount());
    return printerDto;
  }

  public class InvoiceDetailPrinterDto {
    private int no;
    private String productName;
    private int quantity;
    private long unitPrice;

    private long amount;

    public int getNo() {
      return no;
    }

    public void setNo(int no) {
      this.no = no;
    }

    public String getProductName() {
      return productName;
    }

    public void setProductName(String productName) {
      this.productName = productName;
    }

    public int getQuantity() {
      return quantity;
    }

    public void setQuantity(int quantity) {
      this.quantity = quantity;
    }

    public long getUnitPrice() {
      return unitPrice;
    }

    public void setUnitPrice(long unitPrice) {
      this.unitPrice = unitPrice;
    }

    public long getAmount() {
      return amount;
    }

    public void setAmount(long amount) {
      this.amount = amount;
    }
  }
}
