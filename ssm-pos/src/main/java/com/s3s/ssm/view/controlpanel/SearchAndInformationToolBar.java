package com.s3s.ssm.view.controlpanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.bushe.swing.event.EventBus;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.s3s.ssm.config.PosContextProvider;
import com.s3s.ssm.dto.FoodTableDto;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.cashier.event.foodtable.ChangeFoodTable;
import com.s3s.ssm.view.cashier.event.foodtable.CombineFoodTable;
import com.s3s.ssm.view.cashier.event.foodtable.TableViewStatusChangeEvent;
import com.s3s.ssm.view.controlpanel.event.ShowTableEvent;
import com.s3s.ssm.view.controlpanel.event.ShowTableEvent.ShowTableEnum;
import com.s3s.ssm.view.factory.SComponentFactory;

public class SearchAndInformationToolBar extends JPanel {
  private static final long serialVersionUID = 1L;

  private final JPanel informationBar;
  private final TableViewInfoHandler tableViewInfoHandler;

  private final List<FoodTableDto> foodTableList = PosContextProvider.getInstance().getFoodTableService()
          .findAll(0, Integer.MAX_VALUE);

  private JButton btnCombine;

  private JButton btnChange;

  public SearchAndInformationToolBar(TableViewInfoHandler tableViewInfoHandler) {
    this.tableViewInfoHandler = tableViewInfoHandler;

    setLayout(new BorderLayout());
    final JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
    actionBar.add(new JLabel(ControlConfigUtils.getString("label.searchToolbar.show")));
    actionBar.add(showFoodTable());
    actionBar.add(new JLabel(ControlConfigUtils.getString("label.searchToolbar.findTable")));
    actionBar.add(generateCbxFindTable(foodTableList));
    actionBar.add(generateBtnCombineTable());
    actionBar.add(generateBtnChangeTable());

    informationBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    updateInformation();
    add(actionBar, BorderLayout.WEST);
    add(informationBar, BorderLayout.EAST);
  }

  private Component generateBtnChangeTable() {
    btnChange = new JButton(ControlConfigUtils.getString("label.searchToolbar.changeTable"));
    btnChange.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.REFRESH_ICON));
    btnChange.addActionListener(e -> {
      SelectTwoTableDialog dialog = new SelectTwoTableDialog(tableViewInfoHandler.getOperatingFoodTable(),
              foodTableList);
      dialog.setTitle(ControlConfigUtils.getString("label.searchToolbar.changeTable"));
      dialog.setAlwaysOnTop(true);
      dialog.setModalityType(ModalityType.APPLICATION_MODAL);
      dialog.setVisible(true);
      if (dialog.getResult() == JOptionPane.OK_OPTION) {
        EventBus.publish(new ChangeFoodTable(dialog.getFromTable(), dialog.getToTable()));
        EventBus.publish(new TableViewStatusChangeEvent());
      }
    });
    return btnChange;
  }

  @SuppressWarnings("rawtypes")
  private JComboBox showFoodTable() {
    String showAllLabel = ControlConfigUtils.getString("label.ShowFoodTable.showAll");
    String showEmptyLabel = ControlConfigUtils.getString("label.ShowFoodTable.showEmpty");
    String showServingLabel = ControlConfigUtils.getString("label.ShowFoodTable.showServing");
    String showWaitingLabel = ControlConfigUtils.getString("label.ShowFoodTable.showWaiting");
    JComboBox cbxShowFoodTable = SComponentFactory.createDropdown(new String[] {showAllLabel,
        showEmptyLabel, showServingLabel, showWaitingLabel});
    cbxShowFoodTable.addItemListener(e -> {
      JComboBox cbx = (JComboBox) e.getSource();
      if (cbx.getSelectedIndex() == 0) {
        EventBus.publish(new ShowTableEvent(ShowTableEnum.SHOW_ALL));
      } else if (cbx.getSelectedIndex() == 1) {
        EventBus.publish(new ShowTableEvent(ShowTableEnum.SHOW_EMPTY));
      } else if (cbx.getSelectedIndex() == 2) {
        EventBus.publish(new ShowTableEvent(ShowTableEnum.SHOW_SERVING));
      } else if (cbx.getSelectedIndex() == 3) {
        EventBus.publish(new ShowTableEvent(ShowTableEnum.SHOW_BOOKING));
      }
    });
    return cbxShowFoodTable;
  }

  private JComboBox<FoodTableDto> generateCbxFindTable(List<FoodTableDto> allFoodTable) {
    @SuppressWarnings("unchecked")
    JComboBox<FoodTableDto> cbx = SComponentFactory.createDropdownComponent(allFoodTable);
    cbx.addItemListener(e -> {
      if (cbx.getSelectedItem() == null) {
        EventBus.publish(new ShowTableEvent(ShowTableEnum.SHOW_ALL));
      } else {
        EventBus.publish(new ShowTableEvent(ShowTableEnum.SHOW_SPECIFIC, (FoodTableDto) cbx
                .getSelectedItem()));
      }
    });
    AutoCompleteDecorator.decorate(cbx);
    return cbx;
  }

  private JButton generateBtnCombineTable() {
    btnCombine = new JButton(ControlConfigUtils.getString("label.searchToolbar.combineTable"));
    btnCombine.setIcon(IziImageUtils.getSmallIcon("/images/combineIcon.png"));
    btnCombine.addActionListener(e -> {
      List<FoodTableDto> operatingFoodTable = tableViewInfoHandler.getOperatingFoodTable();
      SelectTwoTableDialog dialog = new SelectTwoTableDialog(operatingFoodTable);
      dialog.setTitle(ControlConfigUtils.getString("label.searchToolbar.combineTable"));
      dialog.setAlwaysOnTop(true);
      dialog.setModalityType(ModalityType.APPLICATION_MODAL);
      dialog.setVisible(true);
      if (dialog.getResult() == JOptionPane.OK_OPTION) {
        EventBus.publish(new CombineFoodTable(dialog.getFromTable(), dialog.getToTable()));
        EventBus.publish(new TableViewStatusChangeEvent());
      }
    });
    return btnCombine;
  }

  public void updateInformation() {
    JLabel lblServing = new JLabel();
    long servingCount = tableViewInfoHandler.servingFoodTableNumber();
    long bookingCount = tableViewInfoHandler.countBookingFoodTable();
    lblServing.setText("<html><b>" + ControlConfigUtils.getString("label.searchToolbar.isServing")
            + ": " + servingCount + "</b></html>");
    lblServing.setIcon(IziImageUtils.getSmallIcon("/images/servingIcon.png"));
    JLabel lblWaiting = new JLabel();
    lblWaiting = new JLabel("<html><b>"
            + ControlConfigUtils.getString("label.searchToolbar.isWaiting") + ": "
            + bookingCount + "</b></html>");
    lblWaiting.setIcon(IziImageUtils.getSmallIcon("/images/waitingIcon.png"));
    informationBar.removeAll();
    informationBar.add(lblServing);
    informationBar.add(lblWaiting);
    informationBar.revalidate();
    informationBar.repaint();
    if (servingCount + bookingCount >= 2) {
      btnCombine.setEnabled(true);
      btnChange.setEnabled(true);
    } else {
      btnCombine.setEnabled(false);
      btnChange.setEnabled(false);
    }
  }
}
