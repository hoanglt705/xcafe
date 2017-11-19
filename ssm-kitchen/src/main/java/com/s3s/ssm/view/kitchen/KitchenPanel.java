package com.s3s.ssm.view.kitchen;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.application.ApplicationContext;

import com.s3s.ssm.config.KitchenContextProvider;
import com.s3s.ssm.dto.WaitingFoodDto;
import com.s3s.ssm.service.ICompanyService;
import com.s3s.ssm.service.IKitchenService;

import de.javasoft.swing.JYTable;
import de.javasoft.swing.JYTableScrollPane;

public class KitchenPanel extends JPanel implements ActionListener {
  private static final long serialVersionUID = 1L;
  private IKitchenService kitchenService = KitchenContextProvider.getInstance().getKitchenService();
  private ICompanyService companyService = KitchenContextProvider.getInstance().getCompanyService();
  private LocalTime tableViewAlertTime = LocalTime.of(0, 5);

  private JPanel contentPanel;

  private JYTable waitingTable;
  private JYTable inProgressTable;

  public KitchenPanel(ApplicationContext appContext) {
    setLayout(new BorderLayout());
    addActionButton();
    contentPanel = new JPanel(new GridLayout(1, 2, 5, 5));
    add(contentPanel);
    // Collection<WaitingFoodDto> waitingFood = kitchenService.getAllWaitingFood();
    List<WaitingFoodDto> dtoList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      WaitingFoodDto dto = new WaitingFoodDto();
      dto.setFoodName("Ban " + i);
      dto.setFoodTableName("Mi goi");
      dto.setQuantity(10);
      dtoList.add(dto);
    }
    waitingTable = generateTable(dtoList);
    inProgressTable = generateTable(dtoList);
  }

  private JYTable generateTable(List<WaitingFoodDto> dtos) {
    JYTable waitingTable = new JYTable();
    waitingTable.setRowHeight(30);
    DefaultTableModel model = new DefaultTableModel();
    model.setDataVector(new Object[][] {}, new String[] {"STT", "foodTableName", "foodName", "quantity",
        "waitingTime"});
    int i = 1;
    for (WaitingFoodDto dto : dtos) {
      model.addRow(new Object[] {i++, dto.getFoodTableName(), dto.getFoodName(), dto.getQuantity(),
          LocalTime.of(0, 0)});
    }
    waitingTable.setModel(model);

    contentPanel.add(new JYTableScrollPane(waitingTable));

    waitingTable.getColumn(4).setCellRenderer(new ProgressCellRender());

    ActionListener taskPerformer = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        LocalTime valueAt = (LocalTime) model.getValueAt(0, 4);
        for (int i = 0; i < model.getRowCount(); i++) {
          model.setValueAt(valueAt.plusSeconds(1), i, 4);
        }
      }
    };
    Timer timer = new Timer(1000, taskPerformer);
    timer.start();
    return waitingTable;
  }

  private void addActionButton() {
    JPanel waitingActionBar = new JPanel(new FlowLayout(FlowLayout.LEFT));

    JButton btnStart = new JButton("Start");
    btnStart.setName("btnStart");
    btnStart.setPreferredSize(new Dimension(100, 30));
    btnStart.addActionListener(this);

    JButton btnCancel = new JButton("Cancel");
    btnCancel.setName("btnCancel");
    btnCancel.setPreferredSize(new Dimension(100, 30));
    btnCancel.addActionListener(this);

    waitingActionBar.add(btnStart);
    waitingActionBar.add(btnCancel);

    JPanel inProgressActionBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton btnFinish = new JButton("Finish");
    btnFinish.setName("btnFinish");
    btnFinish.setPreferredSize(new Dimension(100, 30));
    btnFinish.addActionListener(this);

    JButton btnDelete = new JButton("Delete");
    btnDelete.setPreferredSize(new Dimension(100, 30));
    btnDelete.setName("btnDelete");
    btnDelete.addActionListener(this);

    JButton btnLack = new JButton("Lack of material");
    btnLack.setPreferredSize(new Dimension(100, 30));
    btnLack.setName("btnLack");
    btnLack.addActionListener(this);

    inProgressActionBar.add(btnFinish);
    inProgressActionBar.add(btnLack);
    inProgressActionBar.add(btnDelete);

    JPanel actionPanel = new JPanel(new BorderLayout());
    actionPanel.add(waitingActionBar, BorderLayout.WEST);
    actionPanel.add(inProgressActionBar, BorderLayout.EAST);

    add(actionPanel, BorderLayout.NORTH);
  }

  public class ProgressCellRender extends JProgressBar implements TableCellRenderer {
    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
      LocalTime timeValue = (LocalTime) value;
      int progress = (int) ((100f / tableViewAlertTime.toSecondOfDay()) * timeValue.toSecondOfDay());
      if (progress <= 100) {
        setValue(progress);
        setStringPainted(true);
        setString(timeValue.toString());
      }
      return this;
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton source = (JButton) e.getSource();
    switch (source.getName()) {
      case "btnStart":
        startCooking();
        break;
      case "btnCancel":
        cancelCooking();
        break;
      case "btnFinish":
        finishCooking();
        break;
      case "btnDelete":
        deleteCooking();
        break;
      case "btnLack":
        informLackOfMaterial();
        break;
      default:
        break;
    }
  }

  private void informLackOfMaterial() {
    int[] selectedRows = inProgressTable.getSelectedRows();
    DefaultTableModel waitingModel = (DefaultTableModel) inProgressTable.getModel();
    for (int i : selectedRows) {
      waitingModel.removeRow(selectedRows[i]);
    }
    reArrangeOrderNumber(inProgressTable);

  }

  private void deleteCooking() {
    int[] selectedRows = inProgressTable.getSelectedRows();
    DefaultTableModel waitingModel = (DefaultTableModel) inProgressTable.getModel();
    for (int i : selectedRows) {
      waitingModel.removeRow(selectedRows[i]);
    }
    reArrangeOrderNumber(inProgressTable);
  }

  private void finishCooking() {
    int[] selectedRows = inProgressTable.getSelectedRows();
    DefaultTableModel waitingModel = (DefaultTableModel) inProgressTable.getModel();
    for (int i : selectedRows) {
      waitingModel.removeRow(selectedRows[i]);
    }
    reArrangeOrderNumber(inProgressTable);
  }

  private void startCooking() {
    int[] selectedRows = waitingTable.getSelectedRows();
    DefaultTableModel inProgressModel = (DefaultTableModel) inProgressTable.getModel();
    DefaultTableModel waitingModel = (DefaultTableModel) waitingTable.getModel();
    for (int i : waitingTable.getSelectedRows()) {
      String foodTableName = (String) waitingModel.getValueAt(i, 1);
      String foodName = (String) waitingModel.getValueAt(i, 2);
      Integer quantity = (Integer) waitingModel.getValueAt(i, 3);
      LocalTime waitingTime = (LocalTime) waitingModel.getValueAt(i, 4);
      Object[] newRowData = new Object[] {1, foodTableName, foodName, quantity, waitingTime};
      inProgressModel.insertRow(0, newRowData);
    }
    for (int i : waitingTable.getSelectedRows()) {
      waitingModel.removeRow(i);
    }
    reArrangeOrderNumber(waitingTable);
    reArrangeOrderNumber(inProgressTable);
  }

  private void reArrangeOrderNumber(JTable table) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    for (int i = 0; i < model.getRowCount(); i++) {
      model.setValueAt(i + 1, i, 0);
    }
  }

  private void cancelCooking() {
    int[] selectedRows = waitingTable.getSelectedRows();
    DefaultTableModel waitingModel = (DefaultTableModel) waitingTable.getModel();
    for (int i : selectedRows) {
      waitingModel.removeRow(selectedRows[i]);
    }
    reArrangeOrderNumber(waitingTable);
  }
}
