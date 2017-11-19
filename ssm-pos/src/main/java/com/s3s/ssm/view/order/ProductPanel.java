package com.s3s.ssm.view.order;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.softsmithy.lib.swing.internal.TableLayoutConstants;

import com.s3s.ssm.config.PosContextProvider;
import com.s3s.ssm.dto.ProductDto;
import com.s3s.ssm.dto.ProductTypeDto;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.cashier.event.invoice.AddQuantityEvent;
import com.s3s.ssm.view.cashier.event.invoice.DeleteInvoiceDetailEvent;
import com.s3s.ssm.view.cashier.event.invoice.SubQuantityEvent;
import com.s3s.ssm.view.cashier.event.product.ProductEvent;
import com.s3s.ssm.widget.vbadge.Resources;
import com.s3s.ssm.widget.vbadge.VBadge;

public class ProductPanel extends JPanel {
  private static final int DEFAULT_ROW_NUM = 6;
  private static final int DEFAULT_COL_NUM = 8;
  private static final long serialVersionUID = 1L;
  private Map<ProductTypeDto, List<ProductDto>> fProductTypeMap;

  private int rowNum;
  private int columnNum;
  private final Map<ProductDto, VBadge> productButtonMap = new HashMap<>();

  public ProductPanel(Map<ProductTypeDto, List<ProductDto>> productTypeMap) {
    AnnotationProcessor.process(this);
    fProductTypeMap = productTypeMap;
    setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    setBackground(UIManager.getColor("TaskPaneContainer.background"));
    setLayout(generateLayout());
    addProductButtons();
    updateProductQuantity();
    JPopupMenu popupMenu = new JPopupMenu();
    setComponentPopupMenu(popupMenu);
    JCheckBoxMenuItem showNumberInStockItem = new JCheckBoxMenuItem(
            ControlConfigUtils.getString("label.ProductPanel.showNumberInStock"));
    showNumberInStockItem.setSelected(true);
    showNumberInStockItem.addItemListener(new ItemListener() {

      @Override
      public void itemStateChanged(ItemEvent e) {
        boolean showBadgeValue = ItemEvent.SELECTED == e.getStateChange();
        productButtonMap.forEach((productDto, badge) -> {
          if (!productDto.isFood()) {
            badge.setShowBadge(showBadgeValue);
          }
        });
      }
    });
    popupMenu.add(showNumberInStockItem);
  }

  private void updateProductQuantity() {
    productButtonMap.forEach((productDto, badge) -> {
      if (!productDto.isFood()) {
        Double quantityInStock = productDto.getQuantityInStock();
        badge.setBadgeValue(quantityInStock.intValue());
        badge.revalidate();
        badge.repaint();
        checkWarning(badge, productDto.getMinimumQuantity());
      }
    });
  }

  private void checkWarning(VBadge badge, double minQuantity) {
    if (badge.isBadgeShowing()) {
      if (badge.getBadgeValue().intValue() <= minQuantity) {
        badge.putClientProperty("Synthetica.background", Color.RED);
        badge.putClientProperty("Synthetica.background.alpha", 0.45f);
      } else {
        badge.putClientProperty("Synthetica.background.alpha", 0.0f);
      }
    }
  }

  private void addProductButtons() {
    int j = 0;
    for (ProductTypeDto productType : fProductTypeMap.keySet()) {
      JButton btnProductType = new JButton(productType.getName());
      btnProductType.setFont(btnProductType.getFont().deriveFont(18f));
      add(btnProductType, j + ",0");
      int i = 1;
      List<ProductDto> products = fProductTypeMap.get(productType);
      for (ProductDto productDto : products) {
        addProductButton(j, i, productDto);
        i++;
      }
      j++;
    }
  }

  private void addProductButton(int column, int row, final ProductDto productDto) {
    ImageIcon defaultIcon = new ImageIcon((BufferedImage) Resources.getResources("images.badge.24.red"));
    VBadge btnProduct = new VBadge(!productDto.isFood(), (int) productDto.getQuantityInStock(),
            defaultIcon);
    ImageIcon imgIcon = getImage(productDto);
    btnProduct.setIcon(imgIcon);
    btnProduct.setName(productDto.getCode());
    btnProduct.setToolTipText(productDto.getName());
    btnProduct.setEnabled(btnProduct.getBadgeValue().intValue() > 0 || productDto.isFood());

    btnProduct.addActionListener(e -> {
      if (btnProduct.getBadgeValue().intValue() > 0 || productDto.isFood()) {
        updateBadgeValue(btnProduct, -1);
        if (!productDto.isFood()) {
          checkWarning(btnProduct, productDto.getMinimumQuantity());
        }
        EventBus.publish(new ProductEvent(productDto));
      }
    });
    add(btnProduct, column + "," + row);
    productButtonMap.put(productDto, btnProduct);
  }

  private void updateBadgeValue(VBadge btnProduct, int quantity) {
    Number badgeValue = btnProduct.getBadgeValue();
    if (badgeValue instanceof Integer) {
      setBadgeValue(btnProduct, (int) badgeValue + quantity);
    }
  }

  private void setBadgeValue(VBadge btnProduct, int quantity) {
    btnProduct.setBadgeValue(quantity);
  }

  private ImageIcon getImage(final ProductDto productDto) {
    ImageIcon imgIcon = null;
    if (productDto.getImage() != null) {
      imgIcon = IziImageUtils.getBigIcon(productDto.getImage());
    } else {
      imgIcon = IziImageUtils.getBigIcon(IziImageConstants.NO_IMAGE_ICON);
    }
    return imgIcon;
  }

  private TableLayout generateLayout() {
    TableLayout layout = new TableLayout();
    columnNum = fProductTypeMap.keySet().size() >= DEFAULT_COL_NUM ? fProductTypeMap.keySet().size()
            : DEFAULT_COL_NUM;

    OptionalInt max = fProductTypeMap.values().stream().mapToInt(products -> products.size()).max();
    rowNum = max.isPresent() && max.getAsInt() > DEFAULT_ROW_NUM ? max.getAsInt() : DEFAULT_ROW_NUM;

    for (int i = 0; i < columnNum; i++) {
      layout.insertColumn(0, TableLayoutConstants.FILL);
    }

    for (int i = 0; i < rowNum; i++) {
      layout.insertRow(0, TableLayoutConstants.FILL);
    }
    layout.insertRow(0, 50);
    layout.setHGap(1);
    layout.setVGap(1);
    return layout;
  }

  @EventSubscriber(eventClass = AddQuantityEvent.class)
  public void subscribeAddQuantityEvent(Object evt) {
    AddQuantityEvent addQuantityEvent = (AddQuantityEvent) evt;
    ProductDto productDto = addQuantityEvent.getDto().getProduct();
    updateBadgeValue(productButtonMap.get(productDto), -1);
    if (!productDto.isFood()) {
      checkWarning(productButtonMap.get(productDto), productDto.getMinimumQuantity());
    }
  }

  @EventSubscriber(eventClass = SubQuantityEvent.class)
  public void subscribeSubQuantityEvent(Object evt) {
    SubQuantityEvent subQuantityEvent = (SubQuantityEvent) evt;
    ProductDto productDto = subQuantityEvent.getDto().getProduct();
    updateBadgeValue(productButtonMap.get(productDto), 1);
    checkWarning(productButtonMap.get(productDto), productDto.getMinimumQuantity());
  }

  @EventSubscriber(eventClass = DeleteInvoiceDetailEvent.class)
  public void subscribeDeleteInvoiceDetailEvent(Object evt) {
    DeleteInvoiceDetailEvent deleteInvoiceDetailEvent = (DeleteInvoiceDetailEvent) evt;
    ProductDto productDto = deleteInvoiceDetailEvent.getDto().getProduct();
    updateBadgeValue(productButtonMap.get(productDto), deleteInvoiceDetailEvent.getDto().getQuantity());
    if (!productDto.isFood()) {
      checkWarning(productButtonMap.get(productDto), productDto.getMinimumQuantity());
    }
  }

  public void reload() {
    List<ProductDto> materialDtos = new ArrayList<>();
    productButtonMap.keySet().stream().filter(productDto -> !productDto.isFood()).forEach(productDto -> {
      materialDtos.add(productDto);
    });
    Map<String, Double> inStock = PosContextProvider.getInstance().getProductService()
            .getQuantityInStock(materialDtos);
    productButtonMap.forEach((productDto, button) -> {
      if (!productDto.isFood() && inStock.containsKey(productDto.getCode())) {
        button.setBadgeValue(inStock.get(productDto.getCode()).intValue());
      }
    });

  }
}
