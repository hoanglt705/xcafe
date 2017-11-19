package com.s3s.ssm.view.controlpanel;

import java.awt.Color;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXTable;

import com.s3s.ssm.dto.InvoiceDetailDto;
import com.s3s.ssm.dto.InvoiceDto;
import com.s3s.ssm.dto.ProductDto;
import com.s3s.ssm.util.UIConstants;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

public class DetailTableView extends JXTable {
  private static final long serialVersionUID = 1L;
  private static final int TOTAL_AMOUNT_COLUMN_WIDTH = 80;
  private static final int QUANTITY_COLUMN_WIDTH = 65;
  private static final int FOOD_COLUMN_WIDTH = 145;
  private static final int STT_COLUMN_WIDTH = 36;
  private InvoiceDto fInvoice;

  public DetailTableView(InvoiceDto invoiceDto) {
    fInvoice = invoiceDto;
    setModel(new DetaiTableModel());
    setRowHeight(UIConstants.ROW_HEIGHT);
    setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    setShowHorizontalLines(true);
    setShowVerticalLines(true);
    setTotalAmountRenderer();
    setSelectionBackground(Color.blue);
    setSelectionForeground(Color.white);
    setColumnWidth();
    if (fInvoice != null) {
      reloadData();
    }
    setEditable(false);
  }

  public void setInvoice(InvoiceDto invoice) {
    fInvoice = invoice;
    reloadData();
  }

  private void setTotalAmountRenderer() {
    TableColumnModel tcm = getColumnModel();
    tcm.getColumn(4).setCellRenderer(new TotalAmountRenderer());
  }

  private void setColumnWidth() {
    TableColumnModel tcm = getColumnModel();
    tcm.getColumn(0).setPreferredWidth(STT_COLUMN_WIDTH);
    tcm.getColumn(1).setPreferredWidth(FOOD_COLUMN_WIDTH);
    tcm.getColumn(2).setPreferredWidth(QUANTITY_COLUMN_WIDTH);
    tcm.getColumn(3).setPreferredWidth(TOTAL_AMOUNT_COLUMN_WIDTH);
  }

  public void setColumnWidth(int index, int width) {
    TableColumnModel tcm = getColumnModel();
    tcm.getColumn(index).setPreferredWidth(width);
  }

  private class DetaiTableModel extends DefaultTableModel {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("rawtypes")
    Class[] columnTypes = new Class[] {Integer.class, String.class, String.class, Integer.class, Long.class};

    public DetaiTableModel() {
      setDataVector(new Object[][] {}, new String[] {
          ControlConfigUtils.getString("label.InvoiceDetail.order"),
          ControlConfigUtils.getString("label.InvoiceDetail.product"),
          ControlConfigUtils.getString("label.InvoiceDetail.unit"),
          ControlConfigUtils.getString("label.InvoiceDetail.quantity"),
          ControlConfigUtils.getString("label.InvoiceDetail.amount")});

      setRowHeight(UIConstants.ROW_HEIGHT);
      setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      setShowHorizontalLines(true);
      setShowVerticalLines(true);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      return columnTypes[columnIndex];
    }
  }

  private class TotalAmountRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1L;
    private DecimalFormat decimalFormat = new DecimalFormat();

    public TotalAmountRenderer() {
      setHorizontalAlignment(SwingConstants.RIGHT);
      setBackground(UIConstants.DARK_GREEN);
      setForeground(Color.WHITE);
    }

    @Override
    protected void setValue(Object value) {
      setText((value == null) ? "" : decimalFormat.format(value));
    }
  }

  private void deleteAllRow() {
    DefaultTableModel tableModel = (DefaultTableModel) (getModel());
    int rowTotal = tableModel.getRowCount();
    for (int i = 0; i < rowTotal; i++) {
      tableModel.removeRow(0);
    }
  }

  private void reAddInvoiceDetailRow() {
    DefaultTableModel tableModel = (DefaultTableModel) (getModel());
    for (InvoiceDetailDto detail : fInvoice.getInvoiceDetails()) {
      int no = tableModel.getRowCount() + 1;
      String productName = detail.getProduct().getName();
      String unit = detail.getProduct().getUom().getName();
      int quantity = detail.getQuantity();
      long sellPrice = detail.getProduct().getSellPrice();
      long amount = quantity * sellPrice;
      Object[] newRowData = new Object[] {no, productName, unit, quantity, amount};
      tableModel.addRow(newRowData);
    }
  }

  public void reloadData() {
    reloadData(-1);
  }

  public void reloadData(int selectedRow) {
    deleteAllRow();
    reAddInvoiceDetailRow();
    if (selectedRow > -1) {
      setRowSelectionInterval(selectedRow, selectedRow);
    }
  }

  public void addProduct(ProductDto productDto, int quantity) {
    int selectedRow = -1;
    boolean existed = false;
    for (InvoiceDetailDto detail : fInvoice.getInvoiceDetails()) {
      if (detail.getProduct().getCode().equals(productDto.getCode())) {
        int newQuantity = detail.getQuantity() + quantity > 1 ? detail.getQuantity() + quantity : 1;
        detail.setQuantity(newQuantity);
        detail.setAmount(productDto.getSellPrice() * detail.getQuantity());
        selectedRow = fInvoice.getInvoiceDetails().indexOf(detail);
        existed = true;
        break;
      }
    }
    if (!existed) {
      initInvoiceDetail(productDto);
      selectedRow = fInvoice.getInvoiceDetails().size() - 1;
    }
    reloadData(selectedRow);
  }

  private void initInvoiceDetail(ProductDto productDto) {
    Long unitPrice = productDto.getSellPrice();
    InvoiceDetailDto detail = new InvoiceDetailDto();
    detail.setProduct(productDto);
    detail.setUom(productDto.getUom());
    detail.setQuantity(1);
    detail.setUnitPrice(unitPrice);
    detail.setAmount(unitPrice);
    detail.setInvoiceCode(fInvoice.getCode());
    fInvoice.getInvoiceDetails().add(detail);
  }

  public boolean isAtFirstRow() {
    return getSelectedRow() == 0;
  }

  public boolean isLastRow() {
    int selectedRow = getSelectedRow();
    int lastRowIndex = getRowCount() - 1;
    return selectedRow == lastRowIndex;
  }

  public void moveToFirstRow() {
    if (getModel().getRowCount() > 0) {
      setRowSelectionInterval(0, 0);
    }
  }

  public void moveUp() {
    if (getModel().getRowCount() > 1) {
      int upRow = getSelectedRow() - 1;
      setRowSelectionInterval(upRow, upRow);
    }
  }

  public void moveToLastRow() {
    if (getModel().getRowCount() > 1) {
      int lastRowIndex = getRowCount() - 1;
      setRowSelectionInterval(lastRowIndex, lastRowIndex);
    }
  }

  public boolean isSelected() {
    return getSelectedRow() != -1;
  }

  public InvoiceDetailDto getSelectedInvoiceDetailDto() {
    if (getSelectedRow() > -1) {
      return fInvoice.getInvoiceDetails().get(getSelectedRow());
    }
    return null;
  }

  public void moveDown() {
    if (getModel().getRowCount() > 1) {
      int downRow = getSelectedRow() + 1;
      setRowSelectionInterval(downRow, downRow);
    }
  }

  public void updateQuantity(Integer quantity) {
    int selectedRow = getSelectedRow();
    int currentQuantity = (int) getModel().getValueAt(selectedRow, 2);
    int newQuantity = currentQuantity + quantity;
    if (newQuantity >= 0) {
      getModel().setValueAt(newQuantity, selectedRow, 2);
      InvoiceDetailDto invoiceDetail = fInvoice.getInvoiceDetails().get(selectedRow);
      invoiceDetail.setQuantity(newQuantity);
      setRowSelectionInterval(selectedRow, selectedRow);
    }
  }
}
