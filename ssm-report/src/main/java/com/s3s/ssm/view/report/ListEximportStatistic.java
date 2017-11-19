package com.s3s.ssm.view.report;

import java.awt.BorderLayout;
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

import org.jdesktop.swingx.JXDatePicker;

import com.s3s.ssm.config.PosContextProvider;
import com.s3s.ssm.config.ReportContextProvider;
import com.s3s.ssm.dto.ProductDto;
import com.s3s.ssm.dto.ProductTypeDto;
import com.s3s.ssm.dto.report.EximportDto;
import com.s3s.ssm.service.IProductService;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.factory.SComponentFactory;
import com.s3s.ssm.view.list.AListStatisticView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

import de.javasoft.swing.JYCheckBoxTree;

public class ListEximportStatistic extends AListStatisticView<EximportDto> {
  private static final long serialVersionUID = 1L;
  private JXDatePicker dpkFromDate;
  private JXDatePicker dpkToDate;
  private JYCheckBoxTree tree;

  public ListEximportStatistic() {
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
  }

  @Override
  protected List<EximportDto> loadData(int firstIndex, int maxResults) {
    Date toDate = dpkToDate.getDate();
    Date fromDate = dpkFromDate.getDate();
    List<String> selectedProduct = getSelectedProduct();
    if (!selectedProduct.isEmpty()) {
      return ReportContextProvider.getInstance().getReportService()
              .statisticEximport(selectedProduct, fromDate, toDate);
    }
    return Collections.emptyList();
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("productName", ListRendererType.TEXT);
    listDataModel.addColumn("startQuantity", ListRendererType.NUMBER);
    listDataModel.addColumn("exportQuantity", ListRendererType.NUMBER);
    listDataModel.addColumn("importQuantity", ListRendererType.NUMBER);
    listDataModel.addColumn("instoreQuantity", ListRendererType.NUMBER);
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
  protected void addSearchPanel() {
    if (createSearchPanel() == null) {
      return;
    }
    contentPane.add(createSearchPanel(), BorderLayout.NORTH);
  }

  @Override
  protected JPanel createSearchPanel() {
    dpkFromDate = SComponentFactory.createDatePicker("dpkFromDate");
    dpkToDate = SComponentFactory.createDatePicker("dpkToDate");

    String fromDateLabel = ControlConfigUtils.getString("label.ProductIncomeDTO.searchPanel.fromDate");
    String toDateLabel = ControlConfigUtils.getString("label.ProductIncomeDTO.searchPanel.toDate");

    JButton btnSearch = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.SEARCH_ICON));
    btnSearch.setText(getMessage("button.text.search"));
    btnSearch.setName("btnSearch");
    btnSearch.addActionListener(e -> {
      refreshAndBackToFirstPage();
    });

    JPanel panel = new JPanel();
    panel.add(new JLabel(fromDateLabel));
    panel.add(dpkFromDate);
    panel.add(new JLabel(toDateLabel));
    panel.add(dpkToDate);
    panel.add(btnSearch);

    return panel;
  }

  @Override
  protected void clearCriteria() {
  }

  private TreeNode createTreeNode() {
    IProductService productService = PosContextProvider.getInstance().getProductService();
    Map<ProductTypeDto, List<ProductDto>> productTypeMap = productService.getAllMaterialTypes();
    DefaultMutableTreeNode allNode = new DefaultMutableTreeNode("All");
    for (ProductTypeDto productType : productTypeMap.keySet()) {
      DefaultMutableTreeNode productTypeNode = new DefaultMutableTreeNode(productType);
      List<ProductDto> products = productTypeMap.get(productType);
      for (ProductDto productDto : products) {
        productTypeNode.add(new DefaultMutableTreeNode(productDto));
      }
      allNode.add(productTypeNode);
    }
    return allNode;
  }
}
