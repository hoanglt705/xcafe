package com.s3s.ssm.view.report;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.springframework.beans.BeanUtils;

import com.s3s.ssm.config.PosContextProvider;
import com.s3s.ssm.config.ReportContextProvider;
import com.s3s.ssm.dto.ProductDto;
import com.s3s.ssm.dto.ProductTypeDto;
import com.s3s.ssm.dto.export.ProductInStoreExportDto;
import com.s3s.ssm.dto.report.ProductInStoreDto;
import com.s3s.ssm.service.IProductService;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.export.ExportHandler;
import com.s3s.ssm.view.list.AListStatisticView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

import de.javasoft.swing.JYCheckBoxTree;

public class ListProductInStroreStatistic extends AListStatisticView<ProductInStoreDto> {
  private static final long serialVersionUID = 1L;
  private JYCheckBoxTree tree;
  private JButton btnSearch;

  public ListProductInStroreStatistic() {
    super();
    tree = new JYCheckBoxTree(createTreeNode());
    tree.setShowsRootHandles(true);
    tree.setCheckBoxSelectableByNodeClick(false);
    tree.setToggleClickCount(2);
    for (int i = 0; i < tree.getRowCount(); i++)
      tree.expandRow(i);
    contentPane.add(new JScrollPane(tree), BorderLayout.WEST);
  }

  @SuppressWarnings("unused")
  @Override
  protected List<ProductInStoreDto> loadData(int firstIndex, int maxResults) {
    List<String> selectedProduct = getSelectedProduct();
    if (!selectedProduct.isEmpty()) {
      return ReportContextProvider.getInstance().getReportService()
              .statisticProductInStore(selectedProduct);
    }
    return Collections.emptyList();
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("productName", ListRendererType.TEXT);
    listDataModel.addColumn("unit", ListRendererType.TEXT);
    listDataModel.addColumn("quantity", ListRendererType.NUMBER);
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
    btnExport.addActionListener(e -> {
      List<Object> exportData = prepareExportData();
      List<String> fields = Arrays.asList("productCode", "productName", "unit", "quantity");
      ExportHandler exportHandler = new ExportHandler(ProductInStoreExportDto.class, translateHeader(fields),
              exportData);
      exportHandler.performExportAction();
    });

    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(btnSearch);
    panel.add(btnExport);

    return panel;
  }

  private List<Object> prepareExportData() {
    List<Object> exportData = new ArrayList<Object>();
    loadData(0, Integer.MAX_VALUE).forEach(entity -> {
      ProductInStoreExportDto exportDto = new ProductInStoreExportDto();
      BeanUtils.copyProperties(entity, exportDto);
      exportDto.setQuantity(new BigDecimal(entity.getQuantity()));
      exportData.add(exportDto);
    });
    return exportData;
  }

  private List<String> translateHeader(List<String> fields) {
    List<String> translatedHeaders = new ArrayList<String>();
    for (String field : fields) {
      translatedHeaders.add(getMessage("label." + getGenericClass().getSimpleName() + "." + field));
    }
    return translatedHeaders;
  }

  @Override
  protected void clearCriteria() {
  }

  private TreeNode createTreeNode() {
    IProductService productService = PosContextProvider.getInstance().getProductService();
    Map<ProductTypeDto, List<ProductDto>> productTypeMap = productService.getAllMaterialTypes();
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
