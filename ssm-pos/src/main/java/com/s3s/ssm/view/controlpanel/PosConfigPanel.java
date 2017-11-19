package com.s3s.ssm.view.controlpanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.jidesoft.spinner.DateSpinner;
import com.s3s.ssm.util.WidgetConstant;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

import de.javasoft.swing.JYPropertyTable;
import de.javasoft.swing.table.JYComboBoxTableCellEditor;
import de.javasoft.swing.table.PropertyTableModel;
import de.javasoft.swing.table.SpinnerTableCellEditor;
import de.javasoft.swing.table.SpinnerTableCellRenderer;
import de.javasoft.swing.table.TableSeparator;

public class PosConfigPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  private static final String INVOICE_PREVIEW_BEFORE_PRINT = ControlConfigUtils
          .getString("label.PosConfigPanel.invoice.preview");
  private static final String INVOICE_PRINT_AFTER_PAYMENT = ControlConfigUtils
          .getString("label.PosConfigPanel.invoice.printAfterPayment");
  private static final String INVOICE_NUMBER_PRINT = ControlConfigUtils
          .getString("label.PosConfigPanel.invoice.printNumber");
  private static final String INVOICE_PAPER_FORMAT = ControlConfigUtils
          .getString("label.PosConfigPanel.invoice.paper_format");
  private static final String INVOICE_PRINTER = ControlConfigUtils
          .getString("label.PosConfigPanel.invoice.printer");
  private static final String KITCHEN_PRINTER = ControlConfigUtils
          .getString("label.PosConfigPanel.kitchen.printer");
  private static final String KITCHEN_PAPER_FORMAT = ControlConfigUtils
          .getString("label.PosConfigPanel.kitchen.paper_format");
  private static final String KITCHEN_PREVIEW_BEFORE_PRINT = ControlConfigUtils
          .getString("label.PosConfigPanel.kitchen.preview");
  private static Properties prop;
  private static String[] SUPPORTED_PAPER_FORMAT = new String[] {"A4", "80 mm", "80 mmList"};
  static {
    prop = new Properties();

    try (InputStream inputStream = PosConfigPanel.class.getClassLoader().getResourceAsStream(
            "properties/config.properties")) {
      prop.load(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public PosConfigPanel() {
    super();
    setLayout(new BorderLayout());
    JTabbedPane settingTabbedPane = new JTabbedPane();
    add(settingTabbedPane);
    JPanel posConfigPanel = new JPanel(new GridLayout(2, 2));
    posConfigPanel.add(new JLabel());
    posConfigPanel.add(new JCheckBox());
    posConfigPanel.add(new JLabel());
    posConfigPanel.add(new DateSpinner(WidgetConstant.HH_MM_FORMAT));
    settingTabbedPane.addTab(ControlConfigUtils.getString("label.PosConfigPanel.tabTitle.pos"),
            posConfigPanel);
    settingTabbedPane.addTab(ControlConfigUtils.getString("label.PosConfigPanel.tabTitle.print"),
            generatePrintPanel());
  }

  private JPanel generatePrintPanel() {
    JPanel printPanel = new JPanel(new BorderLayout());
    GroupPanel p = new GroupPanel();
    p.addComponent(ControlConfigUtils.getString("label.PosConfigPanel.invoice.title"),
            initInvoiceSettingPropertyTable());
    p.addComponent(ControlConfigUtils.getString("label.PosConfigPanel.kitchen.title"),
            initKitchenSettingPropertyTable());
    printPanel.add(p);
    return printPanel;
  }

  private JYPropertyTable initPosSettingPropertyTable() {
    JYPropertyTable posSetting = new JYPropertyTable() {
      private static final long serialVersionUID = 1L;

      @Override
      protected void installEditors() {
        super.installEditors();
        setPropertyEditor(KitchenPrinterComboModel.class, new PrinterCellEditor(defaultEditor));
      }
    };
    posSetting.addProperty(KITCHEN_PREVIEW_BEFORE_PRINT,
            BooleanUtils.toBoolean(prop.getProperty("pos.showProductInStore")));
    posSetting.addProperty("TimeToWarningLate",
            BooleanUtils.toBoolean(prop.getProperty("pos.showProductInStore")));
    posSetting.getModel().addTableModelListener(new ModelListener());
    return posSetting;
  }

  private JYPropertyTable initKitchenSettingPropertyTable() {
    JYPropertyTable kitchenSetting = new JYPropertyTable() {
      private static final long serialVersionUID = 1L;

      @Override
      protected void installEditors() {
        super.installEditors();
        setPropertyEditor(KitchenPrinterComboModel.class, new PrinterCellEditor(defaultEditor));
        setPropertyEditor(KitchenPaperFormatComboModel.class, new PaperFormatCellEditor(defaultEditor));
      }
    };
    kitchenSetting.addProperty(KITCHEN_PRINTER, new KitchenPrinterComboModel());
    kitchenSetting.addProperty(KITCHEN_PAPER_FORMAT, new KitchenPaperFormatComboModel());
    kitchenSetting.addProperty(KITCHEN_PREVIEW_BEFORE_PRINT, prop.getProperty("print.kitchen.preview"));
    kitchenSetting.getModel().addTableModelListener(new ModelListener());
    return kitchenSetting;
  }

  private JYPropertyTable initInvoiceSettingPropertyTable() {
    JYPropertyTable invoiceSetting = new JYPropertyTable() {
      private static final long serialVersionUID = 1L;

      @Override
      protected void installEditors() {
        super.installEditors();
        setPropertyEditor(InvoicePrinterComboModel.class, new PrinterCellEditor(defaultEditor));
        setPropertyEditor(InvoicePaperFormatComboModel.class, new PaperFormatCellEditor(defaultEditor));
      }
    };
    invoiceSetting.addProperty(INVOICE_PRINTER, new InvoicePrinterComboModel());
    invoiceSetting.addProperty(INVOICE_PAPER_FORMAT, new InvoicePaperFormatComboModel());
    invoiceSetting.addProperty(INVOICE_NUMBER_PRINT,
            NumberUtils.toInt(prop.getProperty("print.invoice.paper_format"), 1));
    TableCellRenderer defaultRenderer = invoiceSetting.getDefaultRenderer();
    TableCellEditor defaultEditor = invoiceSetting.getDefaultEditor();
    invoiceSetting.setPropertyRenderer(INVOICE_NUMBER_PRINT, new SpinnerTableCellRenderer(defaultRenderer));
    SpinnerTableCellEditor editor = new SpinnerTableCellEditor(defaultEditor);
    editor.getEditorComponent().setModel(new SpinnerNumberModel(1, 1, 10, 1));
    invoiceSetting.setPropertyEditor(INVOICE_NUMBER_PRINT, editor);
    invoiceSetting.addProperty(INVOICE_PRINT_AFTER_PAYMENT,
            BooleanUtils.toBoolean(prop.getProperty("print.invoice.print_after_payment")));
    invoiceSetting.addProperty(INVOICE_PREVIEW_BEFORE_PRINT,
            BooleanUtils.toBoolean(prop.getProperty("print.invoice.preview")));
    invoiceSetting.getModel().addTableModelListener(new ModelListener());
    return invoiceSetting;
  }

  private static class PrinterCellEditor extends JYComboBoxTableCellEditor {
    private static final long serialVersionUID = 1L;

    public PrinterCellEditor(TableCellEditor defaultEditor) {
      super(defaultEditor, false, false);
    }
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static class InvoicePrinterComboModel extends DefaultComboBoxModel {
    private static final long serialVersionUID = 1L;
    {
      PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
      String configPrinter = prop.getProperty("print.invoice.printer");
      for (PrintService printer : printServices) {
        super.addElement(printer.getName());
        if (configPrinter.equals(printer.getName())) {
          setSelectedItem(configPrinter);
        }
      }
    }

    @Override
    public void addElement(Object object) {
    }

    @Override
    public void removeElement(Object object) {
    }
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static class KitchenPrinterComboModel extends DefaultComboBoxModel {
    private static final long serialVersionUID = 1L;
    {
      PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
      String configPrinter = prop.getProperty("print.kitchen.printer");
      for (PrintService printer : printServices) {
        super.addElement(printer.getName());
        if (configPrinter.equals(printer.getName())) {
          setSelectedItem(configPrinter);
        }
      }
    }

    @Override
    public void addElement(Object object) {
    }

    @Override
    public void removeElement(Object object) {
    }
  }

  public static class PaperFormatCellEditor extends JYComboBoxTableCellEditor {
    private static final long serialVersionUID = 1L;

    public PaperFormatCellEditor(TableCellEditor defaultEditor) {
      super(defaultEditor, false, false);
    }
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static class InvoicePaperFormatComboModel extends DefaultComboBoxModel {
    private static final long serialVersionUID = 1L;
    {
      for (String format : SUPPORTED_PAPER_FORMAT) {
        super.addElement(format);
      }
      String paperFormat = prop.getProperty("print.invoice.paper_format");
      setSelectedItem(paperFormat);
    }

    @Override
    public void addElement(Object object) {
    }

    @Override
    public void removeElement(Object object) {
    }
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static class KitchenPaperFormatComboModel extends DefaultComboBoxModel {
    private static final long serialVersionUID = 1L;
    {
      for (String format : SUPPORTED_PAPER_FORMAT) {
        super.addElement(format);
      }
      String paperFormat = prop.getProperty("print.kitchen.paper_format");
      setSelectedItem(paperFormat);
    }

    @Override
    public void addElement(Object object) {
    }

    @Override
    public void removeElement(Object object) {
    }
  }

  private class ModelListener implements TableModelListener {
    @Override
    public void tableChanged(TableModelEvent evt) {
      if (evt.getColumn() == 0)
        return;

      int row = evt.getFirstRow();
      PropertyTableModel model = (PropertyTableModel) evt.getSource();
      Object val = model.getPropertyValue(row);

      if (val instanceof TableSeparator) {
        return;
      } else if (val instanceof InvoicePrinterComboModel) {
        // HAlignmentComboModel.Alignment align = (HAlignmentComboModel.Alignment) ((ComboBoxModel) val)
        // .getSelectedItem();
        // val = new Integer(align.toInt());
      }
      else if (model.getPropertyKey(row).toString().equals(INVOICE_PRINTER)) {
        // int size = (Integer) ((SpinnerNumberModel) val).getValue();
        // LineBorder border = (LineBorder) testLabel.getBorder();
        // testLabel.setBorder(new LineBorder(border.getLineColor(), size));
        // return;
      }
      else if (model.getPropertyKey(row).toString().equals("BorderColor")) {
        // Color color = (Color) val;
        // LineBorder border = (LineBorder) testLabel.getBorder();
        // testLabel.setBorder(new LineBorder(color, border.getThickness()));
        // return;
      }
    }
  }
}
