package com.s3s.ssm.view.order;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.VerticalLayout;
import org.jdesktop.swingx.util.WindowUtils;

import com.s3s.ssm.config.PosContextProvider;
import com.s3s.ssm.dto.InvoiceDetailDto;
import com.s3s.ssm.dto.InvoiceDto;
import com.s3s.ssm.service.ICompanyService;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.POSHelper;
import com.s3s.ssm.util.UIConstants;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

import de.javasoft.swing.JYTextField;
import de.javasoft.swing.table.SpinnerTableCellEditor;

public class ChickenPrinterDialog extends JDialog {
  private static final long serialVersionUID = 1L;
  private static final String COMPANY_NAME_PARAM = "COMPANY_NAME_PARAM";
  private static final String NOTE_PARAM = "SSM_NOTE";
  private static final String TABLE_PARAM = "SSM_TABLE";
  private static final String TOTAL_ITEM_PARAM = "SSM_TOTAL_ITEM";
  private ICompanyService companyService = PosContextProvider.getInstance().getCompanyService();
  private final JPanel contentPanel = new JPanel();
  private JXTable table;
  private JYTextField tfdNote;
  private InvoiceDto invoiceDto;

  public ChickenPrinterDialog(InvoiceDto invoiceDto) {
    this.invoiceDto = invoiceDto;
    setTitle(ControlConfigUtils.getString("label.InvoicePanel.print"));
    setIconImage(Toolkit.getDefaultToolkit()
            .getImage(ChickenPrinterDialog.class.getResource("/images/CoffeeAppIcon.png")));
    setModal(true);
    setLayout(new BorderLayout());

    contentPanel.setLayout(new VerticalLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);

    JToolBar actionBar = new JToolBar();
    JButton btnDelete = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.DELETE_ICON));
    btnDelete.setText(ControlConfigUtils.getString("default.button.delete"));
    actionBar.add(btnDelete);
    JButton btnRefresh = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.REFRESH_ICON));
    btnRefresh.setText(ControlConfigUtils.getString("default.button.refresh"));
    actionBar.add(btnRefresh);
    contentPanel.add(actionBar);

    table = new JXTable();
    DefaultTableModel model = new DefaultTableModel() {
      private static final long serialVersionUID = 1L;
      @SuppressWarnings("rawtypes")
      Class[] columnTypes = new Class[] {Integer.class, String.class, String.class, Integer.class};

      @Override
      public Class<?> getColumnClass(int columnIndex) {
        return columnTypes[columnIndex];
      }
    };
    model.setDataVector(new Object[][] {}, new String[] {
        ControlConfigUtils.getString("label.InvoiceDetail.order"),
        ControlConfigUtils.getString("label.InvoiceDetail.product"),
        ControlConfigUtils.getString("label.InvoiceDetail.unit"),
        ControlConfigUtils.getString("label.InvoiceDetail.quantity")});

    for (InvoiceDetailDto detail : invoiceDto.getInvoiceDetails()) {
      int no = model.getRowCount() + 1;
      String productName = detail.getProduct().getName();
      String unit = detail.getProduct().getUom().getName();
      int quantity = detail.getQuantity();
      Object[] newRowData = new Object[] {no, productName, unit, quantity};
      model.addRow(newRowData);
    }

    table.setModel(model);
    table.setRowHeight(UIConstants.ROW_HEIGHT);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.setShowHorizontalLines(true);
    table.setShowVerticalLines(true);

    table.getColumn(0).setPreferredWidth(35);
    table.getColumn(1).setPreferredWidth(318);
    table.getColumn(2).setPreferredWidth(60);
    table.getColumn(3).setPreferredWidth(65);

    TableCellEditor defaultEditor = table.getDefaultEditor(Object.class);
    SpinnerTableCellEditor editor = new SpinnerTableCellEditor(defaultEditor);
    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
    editor.setCellEditorValue(spinnerModel);

    table.setDefaultEditor(Integer.class, editor);

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(480, 300));
    contentPanel.add(scrollPane);
    tfdNote = new JYTextField();
    tfdNote.setPromptText("Ghi chu");
    contentPanel.add(tfdNote);

    initControlPanel();
    setResizable(false);
    pack();
    setLocation(WindowUtils.getPointForCentering(this));
  }

  private void initControlPanel() {
    JButton okButton = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.PRINT_ICON));
    okButton.setText("In");
    okButton.setActionCommand("In");
    okButton.addActionListener(new OkAction());

    JButton cancelButton = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.EXIT_ICON));
    cancelButton.setText(ControlConfigUtils.getString("edit.button.exit"));
    cancelButton.setActionCommand("Cancel");
    cancelButton.addActionListener(new CancelAction());

    JPanel buttonPane = new JPanel(new GridLayout(1, 2));
    buttonPane.setOpaque(false);
    buttonPane.add(okButton);
    buttonPane.add(cancelButton);
    getRootPane().setDefaultButton(okButton);

    JXPanel controls = new JXPanel(new FlowLayout(FlowLayout.RIGHT));
    controls.setOpaque(false);
    controls.add(Box.createHorizontalGlue());
    controls.add(buttonPane);
    add(controls, BorderLayout.SOUTH);
  }

  public int getResult() {
    return JOptionPane.CANCEL_OPTION;
  }

  private class CancelAction implements ActionListener {
    @Override
    @SuppressWarnings("unused")
    public void actionPerformed(ActionEvent e) {
      ChickenPrinterDialog.this.setVisible(false);
    }
  }

  private class OkAction implements ActionListener {

    @SuppressWarnings("unused")
    @Override
    public void actionPerformed(ActionEvent e) {
      Map<String, Object> reportParameters = new HashMap<>();
      reportParameters.put(COMPANY_NAME_PARAM, companyService.getCompany().getName());
      reportParameters.put(NOTE_PARAM, tfdNote.getText());
      reportParameters.put(TABLE_PARAM, invoiceDto.getFoodTable().getName());
      List<ChickenDetailPrinterDto> printerDtos = new ArrayList<>();
      int itemTotal = 0;
      for (int i = 0; i < table.getRowCount(); i++) {
        ChickenDetailPrinterDto dto = new ChickenDetailPrinterDto();
        dto.setNo(i + 1);
        String productName = (String) table.getValueAt(i, 1);
        String unit = (String) table.getValueAt(i, 2);
        Integer quantity = (Integer) table.getValueAt(i, 3);
        dto.setProductName(productName);
        dto.setUnit(unit);
        dto.setQuantity(quantity);
        itemTotal += quantity;
        printerDtos.add(dto);
      }
      reportParameters.put(TOTAL_ITEM_PARAM, itemTotal);
      JRDataSource dataSource = new JRBeanCollectionDataSource(printerDtos, false);
      try (InputStream resourceAsStream = POSHelper.class.getResourceAsStream("/reports/kitchen.jasper")) {
        JasperPrint jasperPrint = JasperFillManager
                .fillReport(resourceAsStream, reportParameters, dataSource);
        JasperPrintManager.printReport(jasperPrint, true);
      } catch (JRException | IOException ex) {
        ex.printStackTrace();
      }
      ChickenPrinterDialog.this.setVisible(false);
    }
  }

  public class ChickenDetailPrinterDto {
    private int no;
    private String productName;
    private int quantity;
    private String unit;

    public int getNo() {
      return no;
    }

    public void setNo(int no) {
      this.no = no;
    }

    public String getProductName() {
      return productName;
    }

    public void setProductName(String productName) {
      this.productName = productName;
    }

    public int getQuantity() {
      return quantity;
    }

    public void setQuantity(int quantity) {
      this.quantity = quantity;
    }

    public String getUnit() {
      return unit;
    }

    public void setUnit(String unit) {
      this.unit = unit;
    }

  }
}
