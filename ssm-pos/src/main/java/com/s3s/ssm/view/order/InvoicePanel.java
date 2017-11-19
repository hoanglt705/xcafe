package com.s3s.ssm.view.order;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.lang.math.NumberUtils;
import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.jdesktop.swingx.VerticalLayout;
import org.softsmithy.lib.swing.JLongField;

import com.s3s.ssm.config.PosContextProvider;
import com.s3s.ssm.dto.FoodTableDto;
import com.s3s.ssm.dto.InvoiceDetailDto;
import com.s3s.ssm.dto.InvoiceDto;
import com.s3s.ssm.dto.InvoiceStatus;
import com.s3s.ssm.dto.ProductDto;
import com.s3s.ssm.service.IInvoiceService;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.POSHelper;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.cashier.event.foodtable.OpenFoodTableEvent;
import com.s3s.ssm.view.cashier.event.invoice.AddQuantityEvent;
import com.s3s.ssm.view.cashier.event.invoice.DeleteInvoiceDetailEvent;
import com.s3s.ssm.view.cashier.event.invoice.InputTotalPaymentAmountEvent;
import com.s3s.ssm.view.cashier.event.invoice.InvoiceDetailSelectionEvent;
import com.s3s.ssm.view.cashier.event.invoice.SubQuantityEvent;
import com.s3s.ssm.view.cashier.event.product.ProductEvent;
import com.s3s.ssm.view.controlpanel.DetailTableView;
import com.s3s.ssm.view.event.BackToControlPanelEvent;
import com.s3s.ssm.view.event.PayInvoiceEvent;
import com.s3s.ssm.view.util.InvoiceUtil;

public class InvoicePanel extends JPanel {
  private static final long serialVersionUID = 1L;
  private static final int DEFAULT_VAT_PERCENT = 0;
  private static final int DETAIL_TABLE_HEIGHT = 360;
  private InvoiceDto activeInvoice;

  private JLongField tfdTotalAmount;
  private JLabel lblFoodTable;
  private DetailTableView fInvoiceDetailTable;

  private JLongField tfdTotalPaymentAmount;
  private JLongField tfdTotalReturnAmount;
  private JSpinner discountSpinner;
  private JLongField tfdDiscountAmount;
  private JSpinner vatSpinner;
  private JLongField tfdVatAmount;

  private DecimalFormat decimalFormat = new DecimalFormat();

  private TransactionHandler transactionHandler = new TransactionHandler();
  private InvoiceUtil invoiceUtil = new InvoiceUtil();
  private List<ProductDto> fProductDtos;
  private IInvoiceService invoiceService = PosContextProvider.getInstance().getInvoiceService();
  private JTextField tfdNote;
  private JButton kitchenPrintButton;

  public InvoicePanel(List<ProductDto> productDtos) {
    fProductDtos = productDtos;
    AnnotationProcessor.process(this);
    setBorder(BorderFactory.createEmptyBorder(3, 5, 2, 2));
    VerticalLayout layout = new VerticalLayout();
    layout.setGap(2);
    setLayout(layout);
    add(generateInvoiceBar());
    add(generateMoneyPanel());
    InvoiceDetailActionBar actionBar = new InvoiceDetailActionBar(transactionHandler, fProductDtos);
    add(actionBar);
    add(initDetailTable());
    actionBar.setDetailTable(fInvoiceDetailTable);
    updateVisibilityOfPrintButton();
  }

  private JToolBar generateInvoiceBar() {
    JToolBar invoiceBar = new JToolBar();
    invoiceBar.add(genearateBackButton());
    invoiceBar.add(generatePaymentButton());
    invoiceBar.add(generatePrintButton());
    invoiceBar.setPreferredSize(new Dimension(1, 40));
    return invoiceBar;
  }

  private JPanel generateMoneyPanel() {
    JButton button = new JButton();
    lblFoodTable = new JLabel("");
    lblFoodTable.setForeground(button.getForeground());
    lblFoodTable.setHorizontalAlignment(SwingConstants.CENTER);
    lblFoodTable.setFont(lblFoodTable.getFont().deriveFont(25f));

    JLabel lblTotalAmount = new JLabel(ControlConfigUtils.getString("label.Invoice.totalAmount"));
    lblTotalAmount.setForeground(button.getForeground());
    lblTotalAmount.setFont(lblFoodTable.getFont().deriveFont(16f));

    tfdTotalAmount = new JLongField();
    tfdTotalAmount.setName("lblTotalAmount");
    tfdTotalAmount.setForeground(Color.RED);
    tfdTotalAmount.setEditable(false);
    tfdTotalAmount.setFont(lblFoodTable.getFont().deriveFont(20f));

    tfdTotalPaymentAmount = new JLongField(0l);
    tfdTotalPaymentAmount.setName("tfdTotalPaymentAmount");
    tfdTotalPaymentAmount.setFont(lblFoodTable.getFont().deriveFont(16f));
    tfdTotalPaymentAmount.addFocusListener(new FocusAdapter() {

      @Override
      public void focusLost(FocusEvent e) {
        try {
          tfdTotalPaymentAmount.commitEdit();
        } catch (ParseException e1) {
          e1.printStackTrace();
        }
        updateTotalReturnAmount();
      }

      @SuppressWarnings("unused")
      @Override
      public void focusGained(FocusEvent e) {
        tfdTotalPaymentAmount.selectAll();
      }

    });

    JLabel lblTotalPaymentAmount = new JLabel(
            ControlConfigUtils.getString("label.Invoice.totalPaymentAmount"));
    lblTotalPaymentAmount.setForeground(button.getForeground());
    lblTotalPaymentAmount.setFont(lblFoodTable.getFont().deriveFont(16f));

    JLabel lblDiscount = new JLabel(ControlConfigUtils.getString("label.Invoice.discount"));
    lblDiscount.setForeground(button.getForeground());
    lblDiscount.setFont(lblFoodTable.getFont().deriveFont(16f));

    tfdTotalReturnAmount = new JLongField(0l);
    tfdTotalReturnAmount.setName("tfdTotalReturnAmount");
    tfdTotalReturnAmount.setFont(lblFoodTable.getFont().deriveFont(16f));

    JLabel lblTotalReturnAmount = new JLabel(ControlConfigUtils.getString("label.Invoice.totalReturnAmount"));
    lblTotalReturnAmount.setForeground(button.getForeground());
    lblTotalReturnAmount.setFont(lblFoodTable.getFont().deriveFont(16f));

    JLabel lblVatTax = new JLabel(ControlConfigUtils.getString("label.Invoice.vatTax"));
    lblVatTax.setForeground(button.getForeground());
    lblVatTax.setFont(lblFoodTable.getFont().deriveFont(16f));

    JLabel lblNote = new JLabel(ControlConfigUtils.getString("label.Invoice.note"));
    lblNote.setForeground(button.getForeground());
    lblNote.setFont(lblFoodTable.getFont().deriveFont(16f));

    tfdNote = new JTextField();
    tfdNote.setName("tfdNote");
    tfdNote.setFont(lblFoodTable.getFont().deriveFont(16f));

    TableLayout tableLayout = new TableLayout(new double[][] { {100, -1}, {0, 30, 30, 30, 30, 30, 30, 30}});
    tableLayout.setHGap(2);
    tableLayout.setVGap(3);

    JPanel moneyPanel = new JPanel(tableLayout);

    JLabel lblTotalAmountTitle = new JLabel(ControlConfigUtils.getString("label.Invoice.totalAmount"));
    lblTotalAmountTitle.setFont(lblFoodTable.getFont().deriveFont(20f));
    lblTotalAmountTitle.setForeground(button.getForeground());

    lblFoodTable = new JLabel("");
    lblFoodTable.setForeground(button.getForeground());
    lblFoodTable.setHorizontalTextPosition(SwingConstants.CENTER);
    lblFoodTable.setFont(lblFoodTable.getFont().deriveFont(25f));

    moneyPanel.add(lblFoodTable, "0,1,1,1");

    moneyPanel.add(lblTotalAmount, "0,2");
    moneyPanel.add(tfdTotalAmount, "1,2");

    moneyPanel.add(lblTotalPaymentAmount, "0,3");
    moneyPanel.add(tfdTotalPaymentAmount, "1,3");

    moneyPanel.add(lblVatTax, "0,4");

    vatSpinner = new JSpinner();
    vatSpinner.setName("vatSpinner");
    vatSpinner.setPreferredSize(new Dimension(50, 20));
    vatSpinner.setFont(lblFoodTable.getFont().deriveFont(16f));
    SpinnerListModel vatModel = new SpinnerListModel(new Integer[] {0, 5, 10, 15});
    vatSpinner.setModel(vatModel);
    ((DefaultEditor) vatSpinner.getEditor()).getTextField().setHorizontalAlignment(SwingConstants.RIGHT);

    JPanel vatPanel = new JPanel(new BorderLayout(2, 0));
    vatPanel.add(vatSpinner, BorderLayout.WEST);
    tfdVatAmount = new JLongField(0l);
    tfdVatAmount.setEditable(false);
    tfdVatAmount.setFont(lblFoodTable.getFont().deriveFont(16f));
    vatPanel.add(tfdVatAmount);

    moneyPanel.add(vatPanel, "1,4");

    discountSpinner = new JSpinner();
    discountSpinner.setName("discountSpinner");
    discountSpinner.setPreferredSize(new Dimension(50, 20));
    discountSpinner.setFont(lblFoodTable.getFont().deriveFont(16f));

    SpinnerNumberModel discountModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
    discountSpinner.setModel(discountModel);
    JPanel discountPanel = new JPanel(new BorderLayout(2, 0));
    discountPanel.add(discountSpinner, BorderLayout.WEST);
    tfdDiscountAmount = new JLongField(0l);
    tfdDiscountAmount.setName("tfdDiscountAmount");
    tfdDiscountAmount.setEditable(false);
    tfdDiscountAmount.setFont(lblFoodTable.getFont().deriveFont(16f));
    discountPanel.add(tfdDiscountAmount);

    moneyPanel.add(lblDiscount, "0,5");
    moneyPanel.add(discountPanel, "1,5");

    moneyPanel.add(lblTotalReturnAmount, "0,6");
    moneyPanel.add(tfdTotalReturnAmount, "1,6");

    moneyPanel.add(lblNote, "0,7");
    moneyPanel.add(new JTextField(), "1,7");

    discountSpinner.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        updateTotalReturnAmount();
      }
    });

    vatSpinner.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        updateTotalReturnAmount();
      }
    });
    tfdTotalReturnAmount.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        updateTotalReturnAmount();
      }
    });
    return moneyPanel;
  }

  private JButton generatePrintButton() {
    kitchenPrintButton = new JButton(ControlConfigUtils.getString("label.InvoicePanel.print"));
    kitchenPrintButton.setIcon(IziImageUtils.getMediumIcon(IziImageConstants.PRINT_ICON));
    kitchenPrintButton.setEnabled(false);
    kitchenPrintButton.addActionListener(e -> {
      ChickenPrinterDialog printerDialog = new ChickenPrinterDialog(activeInvoice);
      printerDialog.setPreferredSize(new Dimension(500, 380));
      printerDialog.setVisible(true);
    });
    return kitchenPrintButton;
  }

  private void updateTotalReturnAmount() {
    long totalAmount = tfdTotalAmount.getLongValue();
    long totalPaymentAmount = tfdTotalPaymentAmount.getLongValue();
    int discount = (int) discountSpinner.getValue();
    int vatTax = (int) vatSpinner.getValue();
    long discountAmount = invoiceUtil.calculateVatTaxAmount(totalAmount, discount);
    long vatTaxAmount = invoiceUtil.calculateVatTaxAmount(totalAmount - discountAmount, vatTax);
    long totalReturnAmount = invoiceUtil.calculateTotalReturnAmount(totalAmount, totalPaymentAmount,
            discountAmount, vatTaxAmount);
    tfdTotalReturnAmount.setLongValue(totalReturnAmount);
    tfdVatAmount.setLongValue(vatTaxAmount);
    tfdDiscountAmount.setLongValue(discountAmount);
  }

  private JButton generatePaymentButton() {
    JButton btnPayment = new JButton(ControlConfigUtils.getString("label.InvoicePanel.payment"));
    btnPayment.setPreferredSize(new Dimension(20, 80));
    btnPayment.setIcon(IziImageUtils.getMediumIcon(IziImageConstants.PAYMENT_ICON));
    btnPayment.addActionListener(e -> {
      payInvoice();
      POSHelper helper = new POSHelper();
      helper.printInvoice(activeInvoice);
      EventBus.publish(new BackToControlPanelEvent(activeInvoice));
      EventBus.publish(new PayInvoiceEvent(activeInvoice.getFoodTable()));
    });
    return btnPayment;
  }

  private JButton genearateBackButton() {
    JButton btnBack = new JButton(ControlConfigUtils.getString("label.InvoicePanel.back"));
    btnBack.setName("btnBack");
    btnBack.setIcon(IziImageUtils.getMediumIcon(IziImageConstants.BACK_ICON));
    btnBack.setPreferredSize(new Dimension(50, 50));
    btnBack.addActionListener(e -> {
      EventBus.publish(new BackToControlPanelEvent(activeInvoice));
      Runnable task = () -> {
        invoiceService.updateInvoiceDetail(activeInvoice);
        if (!transactionHandler.isEmpty()) {
          invoiceService.updateProductQuantityInStore(transactionHandler.getTransaction());
          transactionHandler.clear();
        }
      };
      task.run();
    });
    return btnBack;
  }

  private void payInvoice() {
    long totalAmount = 0;
    try {
      totalAmount = decimalFormat.parse(tfdTotalAmount.getText()).longValue();
    } catch (ParseException e) {
      e.printStackTrace();
    }

    long totalPaymentAmount = tfdTotalPaymentAmount.getLongValue();
    int discount = (int) discountSpinner.getValue();
    int vatPercent = (int) vatSpinner.getValue();
    long vatTaxAmount = invoiceUtil.calculateVatTaxAmount(totalAmount, vatPercent);
    long discountAmount = invoiceUtil.calculateVatTaxAmount(totalAmount, discount);
    long totalReturnAmount = invoiceUtil.calculateTotalReturnAmount(totalAmount, totalPaymentAmount,
            discountAmount, vatTaxAmount);

    activeInvoice.setTotalAmount(totalAmount);
    activeInvoice.setTotalPaymentAmount(totalPaymentAmount);
    activeInvoice.setDiscount(discountAmount);
    activeInvoice.setVatTax(vatTaxAmount);
    activeInvoice.setVatPercent(vatPercent);
    activeInvoice.setVatTax(vatTaxAmount);
    activeInvoice.setTotalReturnAmount(totalReturnAmount);
    activeInvoice.setEndedDate(new Date());
    Runnable task = () -> {
      invoiceService.payInvoice(activeInvoice);
    };
    task.run();
    cleanInvoice();
  }

  private void cleanInvoice() {
    tfdTotalAmount.setLongValue(0l);
    tfdTotalPaymentAmount.setLongValue(0l);
    discountSpinner.setValue(0);
    vatSpinner.setValue(DEFAULT_VAT_PERCENT);
    tfdTotalReturnAmount.setLongValue(0);
    tfdDiscountAmount.setLongValue(0l);
    tfdVatAmount.setLongValue(0l);
  }

  private JScrollPane initDetailTable() {
    JScrollPane scrollTable = new JScrollPane();

    fInvoiceDetailTable = new DetailTableView(activeInvoice);
    fInvoiceDetailTable.setName("fInvoiceDetailTable");
    fInvoiceDetailTable.setColumnWidth(0, 27);
    fInvoiceDetailTable.setColumnWidth(1, 170);
    fInvoiceDetailTable.setColumnWidth(2, 36);
    fInvoiceDetailTable.setColumnWidth(3, 27);
    fInvoiceDetailTable.setColumnWidth(4, 66);
    fInvoiceDetailTable.getSelectionModel().addListSelectionListener(e -> {
      EventBus.publish(new InvoiceDetailSelectionEvent());
    });

    scrollTable.setViewportView(fInvoiceDetailTable);
    scrollTable.setPreferredSize(new Dimension(20, DETAIL_TABLE_HEIGHT));
    scrollTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    return scrollTable;
  }

  public void initInvoiceScreen(InvoiceDto invoice) {
    cleanInvoice();
    activeInvoice = invoice;
    fInvoiceDetailTable.setInvoice(invoice);
    updateInvoice();
    transactionHandler.clear();
  }

  private void updateInvoice() {
    long totalAmount = activeInvoice.getInvoiceDetails().stream().mapToLong(detail -> detail.getAmount())
            .sum();
    activeInvoice.setTotalAmount(totalAmount);
    tfdTotalAmount.setLongValue(totalAmount);
    FoodTableDto foodTableDto = activeInvoice.getFoodTable();
    lblFoodTable.setText(foodTableDto.getArea().getName() + " - " + foodTableDto.getName());
    fInvoiceDetailTable.reloadData(fInvoiceDetailTable.getSelectedRow());
  }

  @EventSubscriber(eventClass = ProductEvent.class)
  public void subscribeProductEvent(Object event) {
    ProductDto productDto = ((ProductEvent) event).getProduct();
    addProduct(productDto, 1);
    updateTotalReturnAmount();
    updateVisibilityOfPrintButton();
  }

  private void updateVisibilityOfPrintButton() {
    if (fInvoiceDetailTable.getRowCount() > 0) {
      kitchenPrintButton.setEnabled(true);
    } else {
      kitchenPrintButton.setEnabled(false);
    }
  }

  private void addProduct(ProductDto productDto, int quantity) {
    fInvoiceDetailTable.addProduct(productDto, quantity);
    updateInvoice();
    transactionHandler.updateTransaction(productDto, quantity);
  }

  @EventSubscriber(eventClass = OpenFoodTableEvent.class)
  public void subscribeOpeningFoodTableEvent(Object evt) {
    invoiceService.updateStatus(activeInvoice.getCode(), InvoiceStatus.SERVING);
  }

  @EventSubscriber(eventClass = InputTotalPaymentAmountEvent.class)
  public void subscribeInputTotalPaymentAmountEvent(Object evt) {
    InputTotalPaymentAmountEvent combineEvent = (InputTotalPaymentAmountEvent) evt;
    String strInputTotalPaymentAmount = combineEvent.getStrInputTotalPaymentAmount();
    Long inputTotalPaymentAmount = NumberUtils.toLong(strInputTotalPaymentAmount);
    Long totalReturnAmount = inputTotalPaymentAmount - activeInvoice.getTotalAmount();
    activeInvoice.setTotalPaymentAmount(inputTotalPaymentAmount);
    activeInvoice.setTotalReturnAmount(totalReturnAmount);
  }

  @EventSubscriber(eventClass = AddQuantityEvent.class)
  public void subscribeAddQuantityEvent(Object evt) {
    AddQuantityEvent addQuantityEvent = (AddQuantityEvent) evt;
    addProduct(addQuantityEvent.getDto().getProduct(), 1);
    updateTotalReturnAmount();
  }

  @EventSubscriber(eventClass = SubQuantityEvent.class)
  public void subscribeSubQuantityEvent(Object evt) {
    SubQuantityEvent subQuantityEvent = (SubQuantityEvent) evt;
    addProduct(subQuantityEvent.getDto().getProduct(), -1);
    updateTotalReturnAmount();
  }

  @EventSubscriber(eventClass = DeleteInvoiceDetailEvent.class)
  public void subscribeDeleteInvoiceDetailEvent(Object evt) {
    DeleteInvoiceDetailEvent deleteInvoiceDetailEvent = (DeleteInvoiceDetailEvent) evt;
    InvoiceDetailDto invoiceDetail = deleteInvoiceDetailEvent.getDto();
    if (!invoiceDetail.getProduct().isFood()) {
      transactionHandler.updateTransaction(invoiceDetail.getProduct(), (-1) * invoiceDetail.getQuantity());
    }
    activeInvoice.getInvoiceDetails().remove(invoiceDetail);
    int selectedRow = fInvoiceDetailTable.getSelectedRow();
    long totalAmount = activeInvoice.getInvoiceDetails().stream().mapToLong(detail -> detail.getAmount())
            .sum();
    activeInvoice.setTotalAmount(totalAmount);
    tfdTotalAmount.setLongValue(totalAmount);
    lblFoodTable.setText(activeInvoice.getFoodTable().getName());

    if (selectedRow == activeInvoice.getInvoiceDetails().size()) {
      fInvoiceDetailTable.reloadData(selectedRow - 1);
    } else {
      fInvoiceDetailTable.reloadData(selectedRow);
    }
    updateTotalReturnAmount();
    updateVisibilityOfPrintButton();
  }
}
