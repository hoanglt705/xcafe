package com.s3s.ssm.view.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.divxdede.swing.busy.DefaultBusyModel;
import org.divxdede.swing.busy.JBusyComponent;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.IziClassUtils;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.view.AbstractView;
import com.s3s.ssm.view.component.IPageChangeListener;
import com.s3s.ssm.view.component.PagingNavigator;

import de.javasoft.swing.JYTabbedPane;
import de.javasoft.swing.JYTable;
import de.javasoft.swing.JYTableHeader;
import de.javasoft.swing.JYTableScrollPane;
import de.javasoft.swing.jytable.renderer.CellLayoutHint;
import de.javasoft.swing.plaf.jytabbedpane.ICloseButtonStrategy;
import de.javasoft.swing.plaf.jytabbedpane.Tab;

public abstract class AListView<T> extends AbstractView implements IPageChangeListener,
        ICallbackAdvanceTableModel<T> {

  private static final int TABLE_HEIGH = 550;
  private static final long serialVersionUID = -1311942671249671111L;
  private static final Log logger = LogFactory.getLog(AListView.class);

  protected static final int DEFAULT_PAGE_SIZE = 522;

  protected JYTabbedPane tabbedPane;
  protected JPanel tablePane;

  protected JPanel toolbar;
  protected JYTable tblListEntities;
  protected JList<Integer> rowHeader;
  protected JBusyComponent<JScrollPane> busyPane;
  protected AdvanceTableModel<T> mainTableModel;
  private PagingNavigator pagingNavigator;

  // TODO use this flag temporarily to prevent init the view more than one
  // time. --> Need to use the Proxy
  // object
  // instead.
  public boolean isInitialized = false;

  protected final List<T> entities = new ArrayList<>();

  // This model is used by sub classes.
  protected final ListDataModel listDataModel = new ListDataModel();
  protected ReferenceDataModel refDataModel = new ReferenceDataModel();

  protected JPanel contentPane;

  public AListView() {
    this(new HashMap<String, Object>());
  }

  public AListView(Icon icon, String label) {
    this(new HashMap<String, Object>(), icon, label);
  }

  public AListView(Map<String, Object> request) {
    this(request, null, null);
  }

  public AListView(Map<String, Object> request, Icon icon, String label) {
    super(request);
    loadService();
    setLayout(new BorderLayout());
    initialPresentationView(listDataModel);
    initTabPane(icon, label);
    add(tabbedPane, BorderLayout.CENTER);
    addComponents();
  }

  protected void loadService() {

  }

  protected void setVisiblePagingNavigator(boolean visible) {
    getPagingNavigator().setVisible(visible);
  }

  protected int getCurrentPage() {
    return getPagingNavigator().getCurrentPage();
  }

  private void initTabPane(Icon icon, String label) {
    tabbedPane = new JYTabbedPane();
    tabbedPane.setName("tabListView");
    tabbedPane.setCloseButtonStrategy(new ICloseButtonStrategy() {
      @Override
      public boolean isButtonVisible(JYTabbedPane tabbedPane, Tab tab) {
        if (tab.getTabIndex() == 0) {
          return false;
        }
        return true;
      }
    });
    tablePane = new JPanel(new MigLayout("wrap, ins 0, hidemode 2", "grow, fill", "[]0[]0[]0[][][]"));
    contentPane = new JPanel(new BorderLayout(5, 5));
    contentPane.add(tablePane, BorderLayout.CENTER);
    Icon tabIcon = icon != null ? icon : IziImageUtils.getSize24Icon(IziImageConstants.LIST_ICON);
    tabbedPane.addTab(label, tabIcon, contentPane);
    tabbedPane.setAlignmentY(CENTER_ALIGNMENT);
  }

  /**
   * List fields need to show on the view.
   * 
   * @param listDataModel
   * @param summaryFieldNames
   *          the fields want to show sum values in footer. They must be
   *          Number type.
   */
  protected abstract void initialPresentationView(ListDataModel listDataModel);

  protected abstract List<T> loadData(int page, int number);

  protected int getVisibleRowCount() {
    return getDefaultPageSize();
  }

  protected int getDefaultPageSize() {
    return DEFAULT_PAGE_SIZE;
  }

  @Override
  public boolean requestFocusInWindow() {
    return getTable().requestFocusInWindow();
  }

  public JYTable getTable() {
    return tblListEntities;
  }

  protected void addComponents() {
    addButtonPanel();
    initMainTable();
    addListenerForDoubleClickRow();
    addBusyPane(setMainScrollpane());
  }

  private JScrollPane setMainScrollpane() {
    JYTableScrollPane mainScrollpane = new JYTableScrollPane(getTable());

    mainScrollpane.getViewport().setBackground(Color.WHITE);
    mainScrollpane.setRowHeaderView(rowHeader);

    JTableHeader th = new JTableHeader();
    th.setTable(getTable());
    mainScrollpane.setCorner(ScrollPaneConstants.UPPER_TRAILING_CORNER, th);
    mainScrollpane.setPreferredSize(new Dimension(20, TABLE_HEIGH));
    return mainScrollpane;
  }

  private void addBusyPane(JScrollPane mainScrollpane) {
    busyPane = createBusyPane(mainScrollpane);

    tablePane.add(busyPane);
  }

  protected void addListenerForDoubleClickRow() {

  }

  @SuppressWarnings("unchecked")
  protected void initMainTable() {
    mainTableModel = (AdvanceTableModel<T>) createTableModel();
    tblListEntities = new SAdvanceTable(mainTableModel, listDataModel);
    tblListEntities.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    tblListEntities.setName("tblListEntities");
    tblListEntities.setVisibleRowCount(getVisibleRowCount());
    JYTableHeader header = (JYTableHeader) tblListEntities.getTableHeader();
    CellLayoutHint hint = header.getCellLayoutHint();
    header.setCellLayoutHint(new CellLayoutHint(hint.sortMarkerPosition, SwingConstants.CENTER,
            hint.verticalAlignment));
  }

  protected void addButtonPanel() {
    pagingNavigator = new PagingNavigator(getTotalPages());
    getPagingNavigator().addPageChangeListener(this);
    tablePane.add(getPagingNavigator());

    refDataModel = initReferenceDataModel();
  }

  /**
   * Override this method to add the footer panel to the list view.
   * 
   * @return
   */
  protected JPanel createFooterPanel() {
    return new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Just create
  }

  /**
   * The subclass should override this method to set data model for the editor
   * component when the cell in edit mode.
   * 
   * @param rdm
   */
  protected ReferenceDataModel initReferenceDataModel() {
    return new ReferenceDataModel();
  }

  private JBusyComponent<JScrollPane> createBusyPane(JScrollPane mainScrollpane) {
    JBusyComponent<JScrollPane> bp = new JBusyComponent<JScrollPane>(mainScrollpane);
    ((DefaultBusyModel) bp.getBusyModel()).setDescription(getMessage("label.loading"));
    return bp;
  }

  protected abstract int getTotalPages();

  /**
   * Create model for the table. </br> <b>Pay attention:</b> if the child
   * class override this method to change the model of the table. It must
   * ensure that the first hide column must be the id of the entities. This
   * will warranty that the edit view linked to edit view correctly.
   * 
   * @return the model of the table.
   */
  protected TableModel createTableModel() {
    return new AdvanceTableModel<T>(listDataModel, entities,
            getGenericClass(), false, getVisibleRowCount(), this);
  }

  @Override
  public Object getAttributeValue(T entity, ColumnModel dataModel) {
    BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
    if (dataModel.isRaw()) {
      return dataModel.getValue();
    }
    return beanWrapper.getPropertyValue(dataModel.getName());
  }

  @Override
  public void setAttributeValue(T entity, ColumnModel dataModel, Object aValue) {
    // do not bind the property if it's raw. The sub class must bind this
    // property manual
    if (!dataModel.isRaw()) {
      BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
      beanWrapper.setPropertyValue(dataModel.getName(), aValue);
    } else {
      dataModel.value(aValue);
    }
  }

  /**
   * Add the listener for the main table data changed.
   * 
   * @param tableModelListener
   */
  public void addTableModelListener(TableModelListener tableModelListener) {
    mainTableModel.addTableModelListener(tableModelListener);
  }

  @SuppressWarnings("unchecked")
  protected Class<T> getGenericClass() {
    return (Class<T>) IziClassUtils.getArgumentClass(getClass());
  }

  public void refreshAndBackToFirstPage() {
    refreshData(0);
  }

  public void refreshData(final int page) {
    busyPane.setBusy(true);
    new SwingWorker<List<T>, Object>() {

      @Override
      protected List<T> doInBackground() {
        return loadData(page, getDefaultPageSize());
      }

      @Override
      protected void done() {
        try {
          List<T> refreshedList = get();
          if (refreshedList != null) {
            entities.clear();
            entities.addAll(refreshedList);
            mainTableModel.fireTableDataChanged();

            tblListEntities.packAll();
          }
        } catch (InterruptedException | ExecutionException e) {
          logger.error(e.getMessage());
          isInitialized = false;
          ErrorInfo errorInfo = new ErrorInfo(getMessage("error.title"),
                  getMessage("error.refreshData.message"), e.getMessage(), null, e, null, null);
          JXErrorPane.showDialog(AListView.this, errorInfo);
        } finally {
          busyPane.setBusy(false);
        }
      }
    }.execute();
  }

  @Override
  public void doPageChanged(ChangeEvent e) {
    PagingNavigator pagingNavigator = (PagingNavigator) e.getSource();
    pagingNavigator.setTotalPage(getTotalPages());
    refreshData(pagingNavigator.getCurrentPage() - 1);
  }

  public JTabbedPane getTabbedPane() {
    return tabbedPane;
  }

  public void loadView() {
    if (!isInitialized) {
      isInitialized = true;
      refreshAndBackToFirstPage();
    }
  }

  protected void setRowHeigh(int rowHeight) {
    tblListEntities.setRowHeight(rowHeight);
  }

  public PagingNavigator getPagingNavigator() {
    return pagingNavigator;
  }
}
