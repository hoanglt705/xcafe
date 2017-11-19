package com.s3s.ssm.view.report;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.s3s.ssm.config.PosContextProvider;
import com.s3s.ssm.config.ReportContextProvider;
import com.s3s.ssm.dto.ProductDto;
import com.s3s.ssm.dto.ProductTypeDto;
import com.s3s.ssm.dto.report.ProductIncomeDto;
import com.s3s.ssm.service.IProductService;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.view.list.AListStatisticView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;
import com.s3s.ssm.widget.JVCompoundDatePicker;

import de.javasoft.swing.JYCheckBoxTree;

public class ListProductIncomeStatistic extends AListStatisticView<ProductIncomeDto> {
  private static final long serialVersionUID = 1L;
  private JYCheckBoxTree tree;
  private JButton btnSearch;
  private JVCompoundDatePicker coumpoundDatePicker;
  private JLabel lblTotalIncome;
  private JLabel sellTotalAmt;
  private JLabel importTotalAmt;

  public ListProductIncomeStatistic() {
    super();
    tree = new JYCheckBoxTree(createTreeNode());
    tree.setShowsRootHandles(true);
    // disable check box selection on node click
    tree.setCheckBoxSelectableByNodeClick(false);
    // enable tree expansion by double click on node
    tree.setToggleClickCount(2);
    // expand tree
    for (int i = 0; i < tree.getRowCount(); i++)
      tree.expandRow(i);
    // select item
    contentPane.add(new JScrollPane(tree), BorderLayout.WEST);
    JPanel totalResultPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    sellTotalAmt = new JLabel("0");
    sellTotalAmt.setForeground(btnSearch.getForeground());
    sellTotalAmt.setFont(btnSearch.getFont().deriveFont(16f));
    sellTotalAmt.setPreferredSize(new Dimension(100, 20));

    lblTotalIncome = new JLabel("0");
    lblTotalIncome.setForeground(btnSearch.getForeground());
    lblTotalIncome.setFont(btnSearch.getFont().deriveFont(16f));
    lblTotalIncome.setPreferredSize(new Dimension(100, 20));

    importTotalAmt = new JLabel("0");
    importTotalAmt.setForeground(btnSearch.getForeground());
    importTotalAmt.setFont(btnSearch.getFont().deriveFont(16f));
    importTotalAmt.setPreferredSize(new Dimension(100, 20));

    JLabel lblSellTotalAmt = new JLabel("Tong tien ban");
    lblSellTotalAmt.setForeground(btnSearch.getForeground());
    lblSellTotalAmt.setFont(btnSearch.getFont().deriveFont(16f));

    JLabel lblImportTotalAmt = new JLabel("Tong tien nhap");
    lblImportTotalAmt.setForeground(btnSearch.getForeground());
    lblImportTotalAmt.setFont(btnSearch.getFont().deriveFont(16f));

    JLabel lblInterestTotalAmt = new JLabel("Tong lai");
    lblInterestTotalAmt.setForeground(btnSearch.getForeground());
    lblInterestTotalAmt.setFont(btnSearch.getFont().deriveFont(16f));

    totalResultPane.add(lblSellTotalAmt);
    totalResultPane.add(sellTotalAmt);
    totalResultPane.add(lblInterestTotalAmt);
    totalResultPane.add(lblTotalIncome);
    totalResultPane.add(lblImportTotalAmt);
    totalResultPane.add(importTotalAmt);
    tablePane.add(totalResultPane);
  }

  @Override
  protected List<ProductIncomeDto> loadData(int firstIndex, int maxResults) {
    Date toDate = coumpoundDatePicker.getToDate();
    Date fromDate = coumpoundDatePicker.getFromDate();
    List<String> selectedProduct = getSelectedProduct();
    if (!selectedProduct.isEmpty()) {
      List<ProductIncomeDto> result = ReportContextProvider.getInstance().getReportService()
              .statisticProductIncome(selectedProduct, fromDate, toDate);
      long totalIncome = result.stream().mapToLong(s -> s.getInterestAmt()).sum();
      long totalSell = result.stream().mapToLong(s -> s.getSellTotalAmt()).sum();
      long totalImport = result.stream().mapToLong(s -> s.getImportTotalAmt()).sum();
      lblTotalIncome.setText(totalIncome + "");
      sellTotalAmt.setText(totalSell + "");
      importTotalAmt.setText(totalImport + "");
      return result;
    }
    return Collections.emptyList();
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("productName", ListRendererType.TEXT);
    listDataModel.addColumn("unit", ListRendererType.TEXT);
    listDataModel.addColumn("quantity", ListRendererType.NUMBER);
    listDataModel.addColumn("sellPrice", ListRendererType.NUMBER);
    listDataModel.addColumn("sellTotalAmt", ListRendererType.NUMBER);
    listDataModel.addColumn("importPrice", ListRendererType.NUMBER);
    listDataModel.addColumn("importTotalAmt", ListRendererType.NUMBER);
    listDataModel.addColumn("interestAmt", ListRendererType.NUMBER);
    listDataModel.addColumn("incomePercent", ListRendererType.NUMBER);
  }

  private List<String> getSelectedProduct() {
    List<String> products = new ArrayList<>();
    for (TreePath p : tree.getCheckBoxSelectionModel().getSelectionPaths()) {
      if (p.getPathCount() == 3) {
        ProductDto productDto = (ProductDto) ((DefaultMutableTreeNode) p.getLastPathComponent())
                .getUserObject();
        products.add(productDto.getCode());
      }
    }
    return products;
  }

  @Override
  protected JPanel createSearchPanel() {
    btnSearch = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.SEARCH_ICON));
    btnSearch.setText(getMessage("button.text.search"));
    btnSearch.setName("btnSearch");
    btnSearch.addActionListener(e -> {
      refreshAndBackToFirstPage();
    });

    JButton btnExport = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.EXPORT_ICON));
    btnExport.setText(getMessage("default.button.export"));

    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    coumpoundDatePicker = new JVCompoundDatePicker();
    panel.add(coumpoundDatePicker);
    panel.add(btnSearch);
    panel.add(btnExport);

    return panel;
  }

  @Override
  protected void clearCriteria() {
  }

  private TreeNode createTreeNode() {
    IProductService productService = PosContextProvider.getInstance().getProductService();
    Map<ProductTypeDto, List<ProductDto>> productTypeMap = productService.getSellableProductTypes();
    DefaultMutableTreeNode allNode = new DefaultMutableTreeNode("All");
    for (ProductTypeDto productType : productTypeMap.keySet()) {
      DefaultMutableTreeNode productTypeNode = new DefaultMutableTreeNode(productType.getName());
      List<ProductDto> products = productTypeMap.get(productType);
      for (ProductDto productDto : products) {
        productTypeNode.add(new DefaultMutableTreeNode(productDto));
      }
      allNode.add(productTypeNode);
    }
    return allNode;
  }
}
