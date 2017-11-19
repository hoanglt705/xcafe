package com.s3s.ssm.view.controlpanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.Calendar;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.VerticalLayout;

import com.s3s.ssm.config.PosContextProvider;
import com.s3s.ssm.dto.CompanyDto;
import com.s3s.ssm.dto.FoodTableDto;
import com.s3s.ssm.dto.InvoiceDto;
import com.s3s.ssm.dto.InvoiceStatus;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.cashier.event.foodtable.TableViewStatusChangeEvent;
import com.s3s.ssm.view.controlpanel.TableViewInfoHandler.TableViewInformation;
import com.s3s.ssm.view.controlpanel.event.ShowTableEvent;
import com.s3s.ssm.view.event.DeleteInvoiceEvent;
import com.s3s.ssm.view.event.EmptyTableViewEvent;
import com.s3s.ssm.view.event.OrderEvent;
import com.s3s.ssm.view.event.PayInvoiceEvent;
import com.s3s.ssm.view.event.StopInvoiceEvent;
import com.s3s.ssm.view.event.UpdateFoodTableEvent;
import com.s3s.ssm.widget.clock.DigitalClock;

public class TableView extends JXTitledPanel {
  private final CompanyDto companyDto = PosContextProvider.getInstance().getCompanyService()
          .getCompany();

  private static final long serialVersionUID = 1L;

  private static final float TOTAL_AMOUNT_TITLE_SIZE = 18f;
  private static final PosContextProvider posContextProvider = PosContextProvider.getInstance();

  private FoodTableDto table;
  private InvoiceDto invoiceDto;

  private JButton btnShoppingCart;
  private JButton btnStop;
  private JButton btnDelete;
  private JPanel actionPane;

  private DigitalClock digitalClock;
  private DecimalFormat decimalFormat = new DecimalFormat();

  public TableView() {
    super();
    AnnotationProcessor.process(this);
  }

  public TableView(FoodTableDto table) {
    this();
    this.table = table;
    setTitle(table.getName());
    initActionPane();
    setName(table.getCode());
  }

  public TableView(InvoiceDto invoice) {
    this(invoice.getFoodTable());
    this.invoiceDto = invoice;
    initInvoice();
  }

  public enum TableViewStatus {
    BOOKING, SERVING, CLOSED
  }

  public TableViewStatus getStatus() {
    if (invoiceDto == null) {
      return TableViewStatus.CLOSED;
    }
    switch (invoiceDto.getInvoiceStatus()) {
      case BOOKING:
        return TableViewStatus.BOOKING;
      case SERVING:
        return TableViewStatus.SERVING;
      default:
        break;
    }
    return null;
  }

  private void updateTableAction(InvoiceStatus status) {
    if (status == null) {
      btnShoppingCart.setEnabled(true);
      btnStop.setEnabled(false);
      btnDelete.setEnabled(false);
      digitalClock.reset();
    } else {
      switch (status) {
        case SERVING:
          btnStop.setEnabled(false);
          btnDelete.setEnabled(true);
          break;
        case BOOKING:
          btnShoppingCart.setEnabled(true);
          btnStop.setEnabled(true);
          btnDelete.setEnabled(false);
          break;
        case DELETE:
          btnShoppingCart.setEnabled(true);
          btnStop.setEnabled(false);
          btnDelete.setEnabled(false);
          digitalClock.reset();
          break;
        default:
          break;
      }
    }
    actionPane.revalidate();
    actionPane.repaint();
  }

  private void addLeftIcon() {
    JButton leftIcon = new JButton();
    leftIcon.setName("btnStatus");
    switch (invoiceDto.getInvoiceStatus()) {
      case SERVING:
        leftIcon.setIcon(IziImageUtils.getIcon(IziImageConstants.ACTIVATE_ICON, 16));
        EventBus.publish(new TableViewStatusChangeEvent());
        break;
      case BOOKING:
        leftIcon.setIcon(IziImageUtils.getIcon(IziImageConstants.WAIT_ICON, 16));
        leftIcon.addActionListener(e -> {
          if (InvoiceStatus.BOOKING.equals(invoiceDto.getInvoiceStatus())) {
            digitalClock.setIgnore();
            leftIcon.setIcon(IziImageUtils.getIcon(IziImageConstants.ACTIVATE_ICON, 16));
            invoiceDto.setInvoiceStatus(InvoiceStatus.SERVING);
            updateTableAction(invoiceDto.getInvoiceStatus());
            Runnable task = () -> posContextProvider.getInvoiceService().updateStatus(invoiceDto.getCode(),
                    InvoiceStatus.SERVING);
            task.run();
            EventBus.publish(new TableViewStatusChangeEvent());
          }
        });
        EventBus.publish(new TableViewStatusChangeEvent());
        break;
      default:
        throw new IllegalStateException("the method addLeftIcon don't support the invoice status "
                + invoiceDto.getInvoiceStatus());
    }
    setLeftDecoration(leftIcon);
  }

  private void initActionPane() {
    actionPane = new JXPanel(new GridLayout(1, 3));
    btnShoppingCart = new JButton();
    btnShoppingCart.setName("btnShoppingCart");
    btnShoppingCart.setIcon(new ImageIcon(TableView.class.getResource("/images/shopping_cart.png")));
    btnShoppingCart.addActionListener(e -> {
      startNewInvoice();
    });
    btnStop = new JButton();
    btnStop.setName("btnStop");
    btnStop.setEnabled(false);
    btnStop.setIcon(new ImageIcon(TableView.class.getResource("/images/stopIcon.png")));
    btnStop.addActionListener(e -> {
      Runnable task = () -> {
        posContextProvider.getInvoiceService().cancelInvoice(invoiceDto);
      };
      task.run();
      emptyInvoicePanel();
      EventBus.publish(new StopInvoiceEvent(table));
      EventBus.publish(new TableViewStatusChangeEvent());
    });

    btnDelete = new JButton();
    btnDelete.setName("btnDelete");
    btnDelete.setEnabled(false);
    btnDelete.setIcon(new ImageIcon(TableView.class.getResource("/images/deleteIcon.png")));
    btnDelete.addActionListener(e -> {
      Runnable task = () -> {
        posContextProvider.getInvoiceService().updateStatus(invoiceDto.getCode(), InvoiceStatus.DELETE);
      };
      task.run();
      emptyInvoicePanel();
      EventBus.publish(new DeleteInvoiceEvent(table));
      EventBus.publish(new TableViewStatusChangeEvent());
    });

    actionPane.add(btnShoppingCart);
    actionPane.add(btnStop);
    actionPane.add(btnDelete);

    add(actionPane, BorderLayout.SOUTH);
  }

  private void startNewInvoice() {
    if (digitalClock != null) {
      EventBus.publish(new OrderEvent(table, digitalClock.getTime()));
    } else {
      EventBus.publish(new OrderEvent(table));
    }
  }

  private void initContentPane() {
    JPanel contentPane = new JPanel(new VerticalLayout(5));
    contentPane.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

    JLabel lblTitleTotalAmount = new JLabel(ControlConfigUtils.getString("label.TableView.totalAmount") + " "
            + decimalFormat.format(invoiceDto.getTotalAmount()));
    lblTitleTotalAmount.setName("lblTitleTotalAmount");
    lblTitleTotalAmount.setFont(lblTitleTotalAmount.getFont().deriveFont(TOTAL_AMOUNT_TITLE_SIZE));
    lblTitleTotalAmount.setForeground((new JButton()).getForeground());
    lblTitleTotalAmount.setHorizontalAlignment(SwingConstants.CENTER);

    JPanel totalAmountPane = new JPanel(new BorderLayout());
    totalAmountPane.add(lblTitleTotalAmount);
    totalAmountPane.add(createClockPanel(), BorderLayout.EAST);

    contentPane.add(totalAmountPane);
    contentPane.add(addDetailTable());
    setContentContainer(contentPane);
  }

  private JFXPanel createClockPanel() {
    final JFXPanel clockPanel = new JFXPanel();
    clockPanel.setPreferredSize(new Dimension(86, clockPanel.getHeight()));
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(companyDto.getTableViewAlertTime());
        int alertTimeHour = calendar.get(Calendar.HOUR);
        int alertTimeMinute = calendar.get(Calendar.MINUTE);
        digitalClock = new DigitalClock(Color.BLUE, null, LocalTime.of(alertTimeHour, alertTimeMinute));
        digitalClock.getTransforms().add(new Scale(0.18f, 0.18f, 0.18f, 0.18f));
        Scene scene = new Scene(digitalClock, 5, 5);
        digitalClock.refreshClocks();
        digitalClock.play();
        if (InvoiceStatus.SERVING.equals(invoiceDto.getInvoiceStatus())) {
          digitalClock.setIgnore();
        }
        clockPanel.setScene(scene);
      }
    }
            );
    Platform.setImplicitExit(false);
    return clockPanel;
  }

  private JScrollPane addDetailTable() {
    JScrollPane scrollTable = new JScrollPane();
    DetailTableView detailTableView = new DetailTableView(invoiceDto);
    detailTableView.setName("fDetailTableView");
    detailTableView.setColumnWidth(0, 30);
    detailTableView.setColumnWidth(1, 150);
    detailTableView.setColumnWidth(2, 36);
    detailTableView.setColumnWidth(3, 27);
    detailTableView.setColumnWidth(4, 73);
    scrollTable.setViewportView(detailTableView);
    scrollTable.setPreferredSize(new Dimension(-1, 182));
    scrollTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    return scrollTable;
  }

  private void setTotalAmount() {
    long totalAmount = invoiceDto.getInvoiceDetails().stream().mapToLong(detail -> detail.getAmount())
            .sum();
    invoiceDto.setTotalAmount(totalAmount);
  }

  private void initInvoice() {
    initContentPane();
    addLeftIcon();
    setTotalAmount();
    updateTableAction(invoiceDto.getInvoiceStatus());
  }

  private void emptyInvoicePanel() {
    invoiceDto = null;
    getContentContainer().removeAll();
    setLeftDecoration(null);
    setRightDecoration(null);
    updateTableAction(null);
    revalidate();
    repaint();
  }

  @EventSubscriber(eventClass = UpdateFoodTableEvent.class)
  public void subscribeUpdateFoodTableEvent(Object event) {
    TableViewInformation information = ((UpdateFoodTableEvent) event).getTableViewInformation();
    if (table.equals(information.getFoodTableDto())) {
      if (information.getInvoiceDto() == null
              || InvoiceStatus.COMBINED.equals(information.getInvoiceDto().getInvoiceStatus())) {
        emptyInvoicePanel();
      } else {
        invoiceDto = information.getInvoiceDto();
        initInvoice();
        if (information.getTime() != null && digitalClock != null) {
          digitalClock.setTime(information.getTime());
        }
      }
    }
  }

  @EventSubscriber(eventClass = EmptyTableViewEvent.class)
  public void subscribeEmptyTableViewEvent(Object event) {
    FoodTableDto foodTable = ((EmptyTableViewEvent) event).getFoodTable();
    if (table.equals(foodTable)) {
      emptyInvoicePanel();
    }
  }

  @EventSubscriber(eventClass = ShowTableEvent.class)
  public void subscribeShowTableEvent(Object event) {
    ShowTableEvent showTableEvent = (ShowTableEvent) event;
    switch (showTableEvent.getFilter()) {
      case SHOW_ALL:
        setVisible(true);
        break;
      case SHOW_EMPTY:
        switch (getStatus()) {
          case BOOKING:
          case SERVING:
            setVisible(false);
            break;
          case CLOSED:
            setVisible(true);
            break;
          default:
            break;
        }
        break;
      case SHOW_SERVING:
        switch (getStatus()) {
          case SERVING:
            setVisible(true);
            break;
          case BOOKING:
          case CLOSED:
            setVisible(false);
            break;
          default:
            break;
        }
        break;
      case SHOW_BOOKING:
        switch (getStatus()) {
          case BOOKING:
            setVisible(true);
            break;
          case SERVING:
          case CLOSED:
            setVisible(false);
            break;
          default:
            break;
        }
        break;
      case SHOW_SPECIFIC:
        setVisible(showTableEvent.getFoodTable().getCode().equals(table.getCode()));
        break;
      default:
        break;
    }
  }

  @EventSubscriber(eventClass = PayInvoiceEvent.class)
  public void subscribPayInvoiceEvent(Object event) {
    PayInvoiceEvent paymentEvent = (PayInvoiceEvent) event;
    if (table.equals(paymentEvent.getFoodTable())) {
      emptyInvoicePanel();
      EventBus.publish(new TableViewStatusChangeEvent());
    }
  }
}
