package com.s3s.ssm.view.order;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.s3s.ssm.config.PosContextProvider;
import com.s3s.ssm.dto.InvoiceDto;
import com.s3s.ssm.dto.ProductDto;
import com.s3s.ssm.dto.ProductTypeDto;
import com.s3s.ssm.service.IProductService;

public class OrderPane extends JPanel {
  private static final int INVOICE_WIDTH = 340;
  private static final long serialVersionUID = 1L;
  private IProductService productService = PosContextProvider.getInstance().getProductService();
  private Map<ProductTypeDto, List<ProductDto>> productTypeMap = productService.getSellableProductTypes();
  private InvoicePanel invoicePanel;
  private ProductPanel productPanel;

  public OrderPane() {
    List<ProductDto> productDtos = new ArrayList<>();
    productTypeMap.values().forEach(list -> {
      productDtos.addAll(list);
    });
    setLayout(new BorderLayout());
    invoicePanel = new InvoicePanel(productDtos);
    JSplitPane splitPane = new JSplitPane();
    splitPane.setLeftComponent(invoicePanel);
    productPanel = new ProductPanel(productTypeMap);
    splitPane.setRightComponent(productPanel);
    splitPane.setDividerLocation(INVOICE_WIDTH);
    add(splitPane);
  }

  public void startInvoiceOrder(InvoiceDto newInvoice) {
    invoicePanel.initInvoiceScreen(newInvoice);
    productPanel.reload();
  }
}
