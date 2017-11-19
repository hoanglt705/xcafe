package com.s3s.ssm.view;

import java.awt.Component;
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

import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.list.AListView;
import com.s3s.ssm.view.list.catalog.ListFoodView;
import com.s3s.ssm.view.list.catalog.ListMaterialView;
import com.s3s.ssm.view.list.catalog.ListProductTypeView;
import com.s3s.ssm.view.list.config.ListAreaView;
import com.s3s.ssm.view.list.config.ListFoodTableView;
import com.s3s.ssm.view.list.config.ListUnitOfMeasureView;
import com.s3s.ssm.view.list.config.ListUomCategoryView;
import com.s3s.ssm.view.list.contact.ListSupplierView;
import com.s3s.ssm.view.list.employee.ListEmployeeView;
import com.s3s.ssm.view.list.employee.ListRoleView;
import com.s3s.ssm.view.list.employee.ListShiftView;
import com.s3s.ssm.view.list.finance.ListPaymentContentView;
import com.s3s.ssm.view.list.finance.ListPaymentView;
import com.s3s.ssm.view.list.finance.ListReceiptView;
import com.s3s.ssm.view.list.sale.ListTableInvoiceView;
import com.s3s.ssm.view.list.security.ListSecurityRoleView;
import com.s3s.ssm.view.list.security.ListSecurityUserView;
import com.s3s.ssm.view.list.store.ListImportStoreFormView;

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
    container.add(generateCatalogTaskPane());
    container.add(generateContactTaskPane());
    container.add(generateEmployeeTaskPane());
    container.add(generateSecurityTaskPane());
    container.add(generateSaleTaskPane());
    container.add(generateFinanceTaskPane());
    container.add(generateStoreTaskPane());
    return container;
  }

  private Component generateSecurityTaskPane() {
    JXTaskPane manageTaskPane = new JXTaskPane();
    manageTaskPane.setTitle(ControlConfigUtils.getString("JTree.SecurityManagement"));

    JToggleButton btnUser = new JToggleButton(ControlConfigUtils.getString("JTree.Config.User"));
    btnUser.setHorizontalAlignment(SwingConstants.LEFT);
    btnUser.addActionListener(this);
    btnUser.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.USER_ICON));
    btnUser.setName("btnUser");

    JToggleButton btnRole = new JToggleButton(ControlConfigUtils.getString("JTree.Config.Role"));
    btnRole.setHorizontalAlignment(SwingConstants.LEFT);
    btnRole.addActionListener(this);
    btnRole.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.ROLE_ICON));
    btnRole.setName("btnRole");

    manageTaskPane.add(btnUser);
    manageTaskPane.add(btnRole);

    buttonGroup.add(btnUser);
    buttonGroup.add(btnRole);

    manageTaskPane.setCollapsed(true);
    return manageTaskPane;
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
      if ("btnArea".equals(name)) {
        view = new ListAreaView();
      } else if ("btnFoodTable".equals(name)) {
        view = new ListFoodTableView();
      } else if ("btnUomCate".equals(name)) {
        view = new ListUomCategoryView();
      } else if ("btnUom".equals(name)) {
        view = new ListUnitOfMeasureView();
      } else if ("btnProductType".equals(name)) {
        view = new ListProductTypeView();
      } else if ("btnFoodNode".equals(name)) {
        view = new ListFoodView();
      } else if ("btnMaterial".equals(name)) {
        view = new ListMaterialView();
      } else if ("btnEmployee".equals(name)) {
        view = new ListEmployeeView();
      } else if ("btnRoleNode".equals(name)) {
        view = new ListRoleView();
      } else if ("btnShift".equals(name)) {
        view = new ListShiftView();
      } else if ("btnImportStoreForm".equals(name)) {
        view = new ListImportStoreFormView();
      } else if ("bntPaymentContent".equals(name)) {
        view = new ListPaymentContentView();
      } else if ("bntPayment".equals(name)) {
        view = new ListPaymentView();
      } else if ("bntReceipt".equals(name)) {
        view = new ListReceiptView();
      } else if ("bntSupplier".equals(name)) {
        view = new ListSupplierView();
      } else if ("bntInvoice".equals(name)) {
        view = new ListTableInvoiceView();
      } else if ("btnUser".equals(name)) {
        view = new ListSecurityUserView();
      } else if ("btnRole".equals(name)) {
        view = new ListSecurityRoleView();
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
    manageTaskPane.setTitle(ControlConfigUtils.getString("JTree.SystemManagement"));
    manageTaskPane.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.SYSTEM_ICON));

    JToggleButton btnArea = new JToggleButton(ControlConfigUtils.getString("JTree.Config.Area"));
    btnArea.setHorizontalAlignment(SwingConstants.LEFT);
    btnArea.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.AREA_ICON));
    btnArea.addActionListener(this);
    btnArea.setName("btnArea");

    JToggleButton btnFoodTable = new JToggleButton(ControlConfigUtils.getString("JTree.Config.FoodTable"));
    btnFoodTable.setHorizontalAlignment(SwingConstants.LEFT);
    btnFoodTable.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.TABLE_ICON));
    btnFoodTable.addActionListener(this);
    btnFoodTable.setName("btnFoodTable");

    JToggleButton btnUomCate = new JToggleButton(ControlConfigUtils.getString("JTree.Config.UOMCategory"));
    btnUomCate.setHorizontalAlignment(SwingConstants.LEFT);
    btnUomCate.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.UOM_CATE_ICON));
    btnUomCate.addActionListener(this);
    btnUomCate.setName("btnUomCate");

    JToggleButton btnUom = new JToggleButton(ControlConfigUtils.getString("JTree.Config.UOM"));
    btnUom.setHorizontalAlignment(SwingConstants.LEFT);
    btnUom.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.UOM_ICON));
    btnUom.addActionListener(this);
    btnUom.setName("btnUom");

    manageTaskPane.add(btnArea);
    manageTaskPane.add(btnFoodTable);
    manageTaskPane.add(btnUomCate);
    manageTaskPane.add(btnUom);

    buttonGroup.add(btnArea);
    buttonGroup.add(btnFoodTable);
    buttonGroup.add(btnUomCate);
    buttonGroup.add(btnUom);

    manageTaskPane.setCollapsed(true);
    return manageTaskPane;
  }

  private JXTaskPane generateCatalogTaskPane() {
    JXTaskPane manageTaskPane = new JXTaskPane();
    manageTaskPane.setTitle(ControlConfigUtils.getString("JTree.CatalogManagement"));
    manageTaskPane.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.FOOD_ICON));

    JToggleButton btnProductType = new JToggleButton(
            ControlConfigUtils.getString("JTree.Catalog.ProductType"));
    btnProductType.setHorizontalAlignment(SwingConstants.LEFT);
    btnProductType.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.PRODUCT_TYPE_ICON));
    btnProductType.addActionListener(this);
    btnProductType.setName("btnProductType");

    JToggleButton btnFoodNode = new JToggleButton(ControlConfigUtils.getString("JTree.Catalog.Food"));
    btnFoodNode.setHorizontalAlignment(SwingConstants.LEFT);
    btnFoodNode.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.FOOD_ICON));
    btnFoodNode.addActionListener(this);
    btnFoodNode.setName("btnFoodNode");

    JToggleButton btnMaterial = new JToggleButton(ControlConfigUtils.getString("JTree.Catalog.Material"));
    btnMaterial.setHorizontalAlignment(SwingConstants.LEFT);
    btnMaterial.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.MATERIAL_ICON));
    btnMaterial.addActionListener(this);
    btnMaterial.setName("btnMaterial");

    manageTaskPane.add(btnProductType);
    manageTaskPane.add(btnFoodNode);
    manageTaskPane.add(btnMaterial);

    buttonGroup.add(btnProductType);
    buttonGroup.add(btnFoodNode);
    buttonGroup.add(btnMaterial);

    manageTaskPane.setCollapsed(true);
    return manageTaskPane;
  }

  private JXTaskPane generateEmployeeTaskPane() {
    JXTaskPane manageTaskPane = new JXTaskPane();
    manageTaskPane.setTitle(ControlConfigUtils.getString("JTree.EmployeeManagement"));
    manageTaskPane.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.ROLE_ICON));

    JToggleButton btnEmployee = new JToggleButton(ControlConfigUtils.getString("JTree.Employee.staff"));
    btnEmployee.setHorizontalAlignment(SwingConstants.LEFT);
    btnEmployee.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.USER_ICON));
    btnEmployee.addActionListener(this);
    btnEmployee.setName("btnEmployee");

    JToggleButton btnRoleNode = new JToggleButton(ControlConfigUtils.getString("JTree.Employee.role"));
    btnRoleNode.setHorizontalAlignment(SwingConstants.LEFT);
    btnRoleNode.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.ROLE_ICON));
    btnRoleNode.addActionListener(this);
    btnRoleNode.setName("btnRoleNode");

    JToggleButton btnShift = new JToggleButton(ControlConfigUtils.getString("JTree.Employee.shift"));
    btnShift.setHorizontalAlignment(SwingConstants.LEFT);
    btnShift.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.SHIFT_ICON));
    btnShift.addActionListener(this);
    btnShift.setName("btnShift");

    manageTaskPane.add(btnEmployee);
    manageTaskPane.add(btnRoleNode);
    manageTaskPane.add(btnShift);

    buttonGroup.add(btnEmployee);
    buttonGroup.add(btnRoleNode);
    buttonGroup.add(btnShift);

    manageTaskPane.setCollapsed(true);
    return manageTaskPane;
  }

  private JXTaskPane generateStoreTaskPane() {
    JXTaskPane manageTaskPane = new JXTaskPane();
    manageTaskPane.setTitle(ControlConfigUtils.getString("JTree.StoreManagement"));
    manageTaskPane.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.STORE_ICON));

    JToggleButton btnImportStoreForm = new JToggleButton(
            ControlConfigUtils.getString("JTree.Store.StoreImportForm"));
    btnImportStoreForm.setHorizontalAlignment(SwingConstants.LEFT);
    btnImportStoreForm.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.WAREHOUSE_ICON));
    btnImportStoreForm.addActionListener(this);
    btnImportStoreForm.setName("btnImportStoreForm");

    manageTaskPane.add(btnImportStoreForm);
    buttonGroup.add(btnImportStoreForm);
    manageTaskPane.setCollapsed(true);
    return manageTaskPane;
  }

  private JXTaskPane generateFinanceTaskPane() {
    JXTaskPane manageTaskPane = new JXTaskPane();
    manageTaskPane.setTitle(ControlConfigUtils.getString("JTree.FinanceManagement"));
    manageTaskPane.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.FINANCE_ICON));

    JToggleButton bntPaymentContent = new JToggleButton(
            ControlConfigUtils.getString("JTree.Finance.PaymentContent"));
    bntPaymentContent.setHorizontalAlignment(SwingConstants.LEFT);
    bntPaymentContent.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.PAYMENT_CONTENT_ICON));
    bntPaymentContent.addActionListener(this);
    bntPaymentContent.setName("bntPaymentContent");

    JToggleButton bntPayment = new JToggleButton(ControlConfigUtils.getString("JTree.Finance.Payment"));
    bntPayment.setHorizontalAlignment(SwingConstants.LEFT);
    bntPayment.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.PAYMENT_ICON));
    bntPayment.addActionListener(this);
    bntPayment.setName("bntPayment");

    JToggleButton bntReceipt = new JToggleButton(ControlConfigUtils.getString("JTree.Finance.Receipt"));
    bntReceipt.setHorizontalAlignment(SwingConstants.LEFT);
    bntReceipt.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.RECEIPT_ICON));
    bntReceipt.addActionListener(this);
    bntReceipt.setName("bntReceipt");

    manageTaskPane.add(bntPaymentContent);
    manageTaskPane.add(bntPayment);
    manageTaskPane.add(bntReceipt);

    buttonGroup.add(bntPaymentContent);
    buttonGroup.add(bntPayment);
    buttonGroup.add(bntReceipt);

    manageTaskPane.setCollapsed(true);
    return manageTaskPane;
  }

  private JXTaskPane generateContactTaskPane() {
    JXTaskPane manageTaskPane = new JXTaskPane();
    manageTaskPane.setTitle(ControlConfigUtils.getString("JTree.ContactManagement"));
    manageTaskPane.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.CONTACT_ICON));
    JToggleButton bntSupplier = new JToggleButton(ControlConfigUtils.getString("JTree.Contact.supplier"));
    bntSupplier.setHorizontalAlignment(SwingConstants.LEFT);
    bntSupplier.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.SUPPLIER_ICON));
    bntSupplier.addActionListener(this);
    bntSupplier.setName("bntSupplier");

    manageTaskPane.add(bntSupplier);
    manageTaskPane.setCollapsed(true);

    buttonGroup.add(bntSupplier);
    return manageTaskPane;
  }

  private JXTaskPane generateSaleTaskPane() {
    JXTaskPane manageTaskPane = new JXTaskPane();
    manageTaskPane.setTitle(ControlConfigUtils.getString("JTree.SaleManagement"));
    manageTaskPane.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.SALE_ICON));

    JToggleButton bntInvoice = new JToggleButton(ControlConfigUtils.getString("JTree.Sale.Invoice"));
    bntInvoice.setHorizontalAlignment(SwingConstants.LEFT);
    bntInvoice.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.INVOICE_ICON));
    bntInvoice.addActionListener(this);
    bntInvoice.setName("bntInvoice");

    manageTaskPane.add(bntInvoice);
    manageTaskPane.setCollapsed(true);

    buttonGroup.add(bntInvoice);
    return manageTaskPane;
  }
}
