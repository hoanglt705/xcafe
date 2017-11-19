package com.s3s.ssm.view.controlpanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.jdesktop.application.ApplicationContext;

import com.s3s.ssm.config.PosContextProvider;
import com.s3s.ssm.dto.AreaDto;
import com.s3s.ssm.dto.FoodTableDto;
import com.s3s.ssm.dto.InvoiceDto;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.view.cashier.event.foodtable.ChangeFoodTable;
import com.s3s.ssm.view.cashier.event.foodtable.CombineFoodTable;
import com.s3s.ssm.view.cashier.event.foodtable.TableViewStatusChangeEvent;
import com.s3s.ssm.view.controlpanel.TableViewInfoHandler.TableViewInformation;
import com.s3s.ssm.view.controlpanel.event.ShowTableEvent;
import com.s3s.ssm.view.event.BackToControlPanelEvent;
import com.s3s.ssm.view.event.DeleteInvoiceEvent;
import com.s3s.ssm.view.event.EmptyTableViewEvent;
import com.s3s.ssm.view.event.OrderEvent;
import com.s3s.ssm.view.event.PayInvoiceEvent;
import com.s3s.ssm.view.event.StopInvoiceEvent;
import com.s3s.ssm.view.event.UpdateFoodTableEvent;
import com.s3s.ssm.view.order.OrderPane;

import de.javasoft.swing.JYTabbedPane;

public class ControlPanel extends JPanel {
  public static final String AREA_TABBED_PANE_NAME = "areaTabbedPaneName";

  private static final PosContextProvider posContextProvider = PosContextProvider.getInstance();

  private static final long serialVersionUID = 1L;

  private static final String AREA_LIST_STORED_FILE = "area_list";
  private static final String ORDER_PANE = "OrderPane";
  private static final String AREA_PANE = "AreaPane";
  private final JYTabbedPane fAreaTabbedPane;
  private final Map<String, Integer> fTabMap = new HashMap<>();

  private final OrderPane fOrderPane;
  private List<AreaDto> fAreaList;

  private final TableViewInfoHandler fTableViewInfoHandler = new TableViewInfoHandler();

  private final SearchAndInformationToolBar fToolBar;
  private final ApplicationContext fAppContext;

  private boolean fNeedToStoreData;

  public ControlPanel(ApplicationContext appContext) {
    fAppContext = appContext;
    AnnotationProcessor.process(this);
    initData();
    setLayout(new CardLayout());
    fOrderPane = new OrderPane();
    fAreaTabbedPane = new JYTabbedPane();
    fAreaTabbedPane.setName(AREA_TABBED_PANE_NAME);
    fAreaTabbedPane.setTabOverviewEnabled(true);
    fAreaTabbedPane.setTabRotationEnabled(false);

    List<InvoiceDto> invoices = posContextProvider.getInvoiceService().getOperatingInvoice();

    for (InvoiceDto invoice : invoices) {
      fTableViewInfoHandler.add(invoice);
    }
    for (int j = 0; j < fAreaList.size(); j++) {
      AreaDto areaDto = fAreaList.get(j);
      if (areaDto.isActive()) {
        addAreaTab(areaDto);
      }
    }
    JPanel areaPane = new JPanel(new BorderLayout());

    fToolBar = new SearchAndInformationToolBar(fTableViewInfoHandler);
    areaPane.add(fToolBar, BorderLayout.NORTH);
    areaPane.add(fAreaTabbedPane, BorderLayout.CENTER);
    add(areaPane, AREA_PANE);
    add(fOrderPane, ORDER_PANE);
  }

  private void addAreaTab(AreaDto areaDto) {
    List<FoodTableDto> footTables = posContextProvider.getFoodTableService().findAllSameArea(
            areaDto.getCode());
    JPanel areaTab = new JPanel(new BorderLayout());
    List<JComponent> tableViews = new ArrayList<JComponent>();
    for (int i = 0; i < footTables.size(); i++) {
      InvoiceDto invoice = fTableViewInfoHandler.getInvoice(footTables.get(i));
      TableView tableView = null;
      if (invoice != null) {
        tableView = new TableView(invoice);
      } else {
        tableView = new TableView(footTables.get(i));
      }
      tableViews.add(tableView);
    }
    NavigationPanel navPanel = new NavigationPanel(tableViews, 4, 2);
    areaTab.add(navPanel);
    String areaTitle = generateTabTitle(areaDto, areaTab.getComponentCount());

    fAreaTabbedPane.addTab(areaDto.getName(), areaTab);
    int tabPosition = fAreaTabbedPane.getTabCount() - 1;
    fAreaTabbedPane.setToolTipTextAt(tabPosition, areaTitle);
    fAreaTabbedPane.setIconAt(tabPosition, IziImageUtils.getSize24Icon(IziImageConstants.AREA_ICON));
    fTabMap.put(areaDto.getCode(), tabPosition);
  }

  private String generateTabTitle(AreaDto areaDto, int tableNum) {
    return "<html><b>" + areaDto.getName() + "</b><br/>So ban:" + tableNum
            + "<br/>Phuc vu:" + fTableViewInfoHandler.servingFoodTableNumner(areaDto.getCode())
            + "<br/>Dang cho:" + fTableViewInfoHandler.countBookingFoodTable(areaDto.getCode()) + "</html>";
  }

  private void initData() {
    try {
      loadData();
      if (fAreaList == null) {
        fAreaList = posContextProvider.getAreaService().findAll(0, Integer.MAX_VALUE);
      }
    } catch (IOException e) {
      fAreaList = posContextProvider.getAreaService().findAll(0, Integer.MAX_VALUE);
      fNeedToStoreData = true;
    }
  }

  @EventSubscriber(eventClass = OrderEvent.class)
  public void subscribeOrderEvent(Object event) {
    CardLayout cl = (CardLayout) (getLayout());
    cl.show(this, ORDER_PANE);
    OrderEvent orderEvent = (OrderEvent) event;
    FoodTableDto foodTable = orderEvent.getFoodTable();
    InvoiceDto invoice = fTableViewInfoHandler.getInvoice(foodTable);
    if (invoice != null) {
      fTableViewInfoHandler.setTime(foodTable, orderEvent.getWaitingTime());
      fOrderPane.startInvoiceOrder(invoice);
    } else {
      InvoiceDto newInvoice = PosContextProvider.getInstance().getInvoiceService()
              .createNewInvoice(foodTable);
      fTableViewInfoHandler.add(newInvoice, orderEvent.getWaitingTime());
      fOrderPane.startInvoiceOrder(newInvoice);
    }
  }

  @EventSubscriber(eventClass = BackToControlPanelEvent.class)
  public void subscribBackToControlPanelEvent(Object event) {
    InvoiceDto invoice = ((BackToControlPanelEvent) event).getInvoice();
    CardLayout cl = (CardLayout) (getLayout());
    cl.show(this, AREA_PANE);
    if (invoice != null) {
      TableViewInformation information = fTableViewInfoHandler
              .getTableViewInformation(invoice.getFoodTable());
      EventBus.publish(new UpdateFoodTableEvent(information));
    }
  }

  @EventSubscriber(eventClass = StopInvoiceEvent.class)
  public void subscribStopInvoiceEvent(Object event) {
    FoodTableDto foodTable = ((StopInvoiceEvent) event).getFoodTable();
    fTableViewInfoHandler.remove(foodTable);
  }

  @EventSubscriber(eventClass = DeleteInvoiceEvent.class)
  public void subscribDeleteInvoiceEvent(Object event) {
    FoodTableDto foodTable = ((DeleteInvoiceEvent) event).getFoodTable();
    fTableViewInfoHandler.remove(foodTable);
  }

  @EventSubscriber(eventClass = PayInvoiceEvent.class)
  public void subscribPayInvoiceEvent(Object event) {
    FoodTableDto foodTable = ((PayInvoiceEvent) event).getFoodTable();
    fTableViewInfoHandler.remove(foodTable);
  }

  @EventSubscriber(eventClass = TableViewStatusChangeEvent.class)
  public void subscribTableViewStatusChangeEvent(Object event) {
    updateTitleForAreaTabbedPane();
    if (fToolBar != null) {
      fToolBar.updateInformation();
    }
  }

  private void updateTitleForAreaTabbedPane() {
    for (int i = 0; i < fAreaTabbedPane.getTabCount(); i++) {
      JPanel areaPane = (JPanel) fAreaTabbedPane.getComponentAt(i);
      int tableNum = areaPane.getComponentCount();
      fAreaTabbedPane.setToolTipTextAt(i, generateTabTitle(fAreaList.get(i), tableNum));
    }
  }

  @EventSubscriber(eventClass = CombineFoodTable.class)
  public void subscribCombineFoodTableEvent(Object event) {
    CombineFoodTable combineEvent = (CombineFoodTable) event;
    FoodTableDto fromTable = combineEvent.getFromTable();
    FoodTableDto toTable = combineEvent.getToTable();

    InvoiceDto fromInvoice = fTableViewInfoHandler.getInvoice(fromTable);
    InvoiceDto toInvoice = fTableViewInfoHandler.getInvoice(toTable);

    InvoiceDto combinedInvoice = posContextProvider.getInvoiceService().combine2Invoices(fromInvoice,
            toInvoice);

    EventBus.publish(new UpdateFoodTableEvent(fTableViewInfoHandler.getTableViewInformation(fromTable)));
    EventBus.publish(new UpdateFoodTableEvent(fTableViewInfoHandler.getTableViewInformation(toTable)));

    fTableViewInfoHandler.add(combinedInvoice);
    FoodTableDto foodtableDto = new FoodTableDto();
    foodtableDto.setCode(fromInvoice.getFoodTable().getCode());
    foodtableDto.setName(fromInvoice.getFoodTable().getName());

    fTableViewInfoHandler.remove(foodtableDto);
  }

  @EventSubscriber(eventClass = ChangeFoodTable.class)
  public void subscribChangeFoodTableEvent(Object event) {
    ChangeFoodTable changeEvent = (ChangeFoodTable) event;
    FoodTableDto fromTable = changeEvent.getFromTable();
    FoodTableDto toTable = changeEvent.getToTable();
    InvoiceDto fromInvoice = fTableViewInfoHandler.getInvoice(fromTable);

    InvoiceDto changedInvoice = posContextProvider.getInvoiceService().moveInvoice(fromInvoice, toTable);

    fTableViewInfoHandler.remove(fromTable);
    fTableViewInfoHandler.add(changedInvoice);

    EventBus.publish(new EmptyTableViewEvent(fromTable));
    EventBus.publish(new UpdateFoodTableEvent(fTableViewInfoHandler.getTableViewInformation(toTable)));
  }

  @EventSubscriber(eventClass = ShowTableEvent.class)
  public void subscribeShowTableEvent(Object event) {
    ShowTableEvent showTableEvent = (ShowTableEvent) event;
    switch (showTableEvent.getFilter()) {
      case SHOW_SPECIFIC:
        String area = showTableEvent.getFoodTable().getArea().getCode();
        fAreaTabbedPane.setSelectedIndex(fTabMap.get(area));
        break;
      default:
        break;
    }
  }

  public void saveData() throws IOException {
    if (fNeedToStoreData) {
      fAppContext.getLocalStorage().save(fAreaList, AREA_LIST_STORED_FILE);
    }
  }

  @SuppressWarnings("unchecked")
  public void loadData() throws IOException {
    fAreaList = (List<AreaDto>) fAppContext.getLocalStorage().load(AREA_LIST_STORED_FILE);
  }
}
