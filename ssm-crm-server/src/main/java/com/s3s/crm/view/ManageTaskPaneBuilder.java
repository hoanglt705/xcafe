package com.s3s.crm.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.plaf.BorderUIResource;

import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;

import com.s3s.crm.view.customer.ListCustomerView;
import com.s3s.crm.view.customerCard.ListCustomerCardView;
import com.s3s.crm.view.customerType.ListCustomerTypeView;
import com.s3s.crm.view.internalMaterial.ListInternalMaterialView;
import com.s3s.crm.view.invoice.ListCrmInvoiceView;
import com.s3s.crm.view.materialType.ListMaterialTypeView;
import com.s3s.crm.view.product.ListCrmProductView;
import com.s3s.crm.view.promotion.ListPromotionView;
import com.s3s.crm.view.shape.ListShapeView;
import com.s3s.crm.view.size.ListSizeView;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.list.AListView;

public class ManageTaskPaneBuilder implements ActionListener {

  private final JScrollPane contentViewScrollPane;
  private final ButtonGroup buttonGroup = new ButtonGroup();
  private Map<String, JPanel> paneMap = new HashMap<>();

  public ManageTaskPaneBuilder(JScrollPane contentViewScrollPane) {
    this.contentViewScrollPane = contentViewScrollPane;
  }

  public JComponent buildApplicationContext() {
    JXTaskPaneContainer container = new JXTaskPaneContainer();
    container.setBorder(new BorderUIResource(BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    container.add(generateManageTaskPane());
    container.add(generateProductTaskPane());
    container.add(generatePromotionTaskPane());
    container.add(generateSaleTaskPane());
    return container;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void actionPerformed(ActionEvent e) {
    JToggleButton button = (JToggleButton) e.getSource();
    String name = button.getName();
    if (paneMap.containsKey(name)) {
      contentViewScrollPane.setViewportView(paneMap.get(name));
    } else {
      JPanel view = null;
      switch (name) {
        case "btnCustomer":
          view = new ListCustomerView();
          break;
        case "btnCustomerType":
          view = new ListCustomerTypeView();
          break;
        case "btnCustomerCard":
          view = new ListCustomerCardView();
          break;
        case "btnShape":
          view = new ListShapeView();
          break;
        case "btnSize":
          view = new ListSizeView();
          break;
        case "btnMaterialType":
          view = new ListMaterialTypeView();
          break;
        case "btnProduct":
          view = new ListCrmProductView();
          break;
        case "btnPromotion":
          view = new ListPromotionView();
          break;
        case "btnInvoice":
          view = new ListCrmInvoiceView();
          break;
        case "btnInternalMaterial":
          view = new ListInternalMaterialView();
          break;
        default:
          break;
      }
      if (view instanceof AListView) {
        ((AListView) view).loadView();
      }
      contentViewScrollPane.setViewportView(view);
      paneMap.put(name, view);
    }
  }

  private JXTaskPane generateManageTaskPane() {
    JXTaskPane manageTaskPane = new JXTaskPane();
    manageTaskPane.setTitle(ControlConfigUtils.getString("JTree.Management.Customer"));

    JToggleButton btnCustomer = new JToggleButton(ControlConfigUtils.getString("JTree.Crm.Customer"));
    btnCustomer.setHorizontalAlignment(SwingConstants.LEFT);
    btnCustomer.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.SUPPLIER_ICON));
    btnCustomer.addActionListener(this);
    btnCustomer.setName("btnCustomer");

    JToggleButton btnCustomerType = new JToggleButton(ControlConfigUtils.getString("JTree.Crm.CustomerType"));
    btnCustomerType.setHorizontalAlignment(SwingConstants.LEFT);
    btnCustomerType.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.CONTACT_ICON));
    btnCustomerType.addActionListener(this);
    btnCustomerType.setName("btnCustomerType");

    JToggleButton btnCustomerCard = new JToggleButton(ControlConfigUtils.getString("JTree.Crm.CustomerCard"));
    btnCustomerCard.setHorizontalAlignment(SwingConstants.LEFT);
    btnCustomerCard.setIcon(IziImageUtils.getSmallIcon("/images/card.png"));
    btnCustomerCard.addActionListener(this);
    btnCustomerCard.setName("btnCustomerCard");

    buttonGroup.add(btnCustomerType);
    buttonGroup.add(btnCustomer);
    buttonGroup.add(btnCustomerCard);

    manageTaskPane.add(btnCustomer);
    manageTaskPane.add(btnCustomerType);
    manageTaskPane.add(btnCustomerCard);
    return manageTaskPane;
  }

  private JXTaskPane generateProductTaskPane() {
    JXTaskPane productTaskPane = new JXTaskPane();
    productTaskPane.setTitle(ControlConfigUtils.getString("JTree.Management.Product"));

    JToggleButton btnProduct = new JToggleButton(ControlConfigUtils.getString("JTree.Crm.Product"));
    btnProduct.setHorizontalAlignment(SwingConstants.LEFT);
    btnProduct.setIcon(IziImageUtils.getSmallIcon("/images/cake.png"));
    btnProduct.addActionListener(this);
    btnProduct.setName("btnProduct");

    JToggleButton btnShape = new JToggleButton(ControlConfigUtils.getString("JTree.Crm.Shape"));
    btnShape.setHorizontalAlignment(SwingConstants.LEFT);
    btnShape.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.CONTACT_ICON));
    btnShape.addActionListener(this);
    btnShape.setName("btnShape");

    JToggleButton btnSize = new JToggleButton(ControlConfigUtils.getString("JTree.Crm.Size"));
    btnSize.setHorizontalAlignment(SwingConstants.LEFT);
    btnSize.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.CONTACT_ICON));
    btnSize.addActionListener(this);
    btnSize.setName("btnSize");

    JToggleButton btnMaterialType = new JToggleButton(ControlConfigUtils.getString("JTree.Crm.MaterialType"));
    btnMaterialType.setHorizontalAlignment(SwingConstants.LEFT);
    btnMaterialType.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.CONTACT_ICON));
    btnMaterialType.addActionListener(this);
    btnMaterialType.setName("btnMaterialType");

    JToggleButton btnInternalMaterial = new JToggleButton(
            ControlConfigUtils.getString("JTree.Crm.InternalMaterial"));
    btnInternalMaterial.setHorizontalAlignment(SwingConstants.LEFT);
    btnInternalMaterial.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.CONTACT_ICON));
    btnInternalMaterial.addActionListener(this);
    btnInternalMaterial.setName("btnInternalMaterial");

    buttonGroup.add(btnShape);
    buttonGroup.add(btnSize);
    buttonGroup.add(btnMaterialType);
    buttonGroup.add(btnProduct);
    buttonGroup.add(btnInternalMaterial);

    productTaskPane.add(btnProduct);
    productTaskPane.add(btnShape);
    productTaskPane.add(btnSize);
    productTaskPane.add(btnMaterialType);
    productTaskPane.add(btnInternalMaterial);
    return productTaskPane;
  }

  private JXTaskPane generatePromotionTaskPane() {
    JXTaskPane promotionTaskPane = new JXTaskPane();
    promotionTaskPane.setTitle(ControlConfigUtils.getString("JTree.Management.Promotion"));

    JToggleButton btnPromotion = new JToggleButton(ControlConfigUtils.getString("JTree.Crm.Promotion"));
    btnPromotion.setHorizontalAlignment(SwingConstants.LEFT);
    btnPromotion.setIcon(IziImageUtils.getSmallIcon("/images/promotion.jpg"));
    btnPromotion.addActionListener(this);
    btnPromotion.setName("btnPromotion");

    buttonGroup.add(btnPromotion);
    promotionTaskPane.add(btnPromotion);

    return promotionTaskPane;
  }

  private JXTaskPane generateSaleTaskPane() {
    JXTaskPane invoiceTaskPane = new JXTaskPane();
    invoiceTaskPane.setTitle(ControlConfigUtils.getString("JTree.Management.Invoice"));

    JToggleButton btnInvoice = new JToggleButton(ControlConfigUtils.getString("JTree.Crm.Invoice"));
    btnInvoice.setHorizontalAlignment(SwingConstants.LEFT);
    btnInvoice.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.SALE_ICON));
    btnInvoice.addActionListener(this);
    btnInvoice.setName("btnInvoice");

    buttonGroup.add(btnInvoice);
    invoiceTaskPane.add(btnInvoice);

    return invoiceTaskPane;
  }

}
