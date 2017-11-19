package com.s3s.crm.view.dashboard;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXTitledPanel;

import com.s3s.ssm.util.i18n.ControlConfigUtils;

public class Dashboard extends JPanel {
  private static final long serialVersionUID = 1L;

  public Dashboard() {
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    setLayout(new GridLayout(2, 1));
    JXTitledPanel titlePotentialCustomerPanel = new JXTitledPanel(
            ControlConfigUtils.getString("label.Dashboard.PotentialCustomerDto.title"));
    ListPotentialCustomerView potentialCustomerView = new ListPotentialCustomerView();
    potentialCustomerView.loadView();
    titlePotentialCustomerPanel.setContentContainer(potentialCustomerView);
    JXTitledPanel titleLuckyCustomerPanel = new JXTitledPanel(
            ControlConfigUtils.getString("label.Dashboard.LuckyCustomerDto.title"));
    ListLuckyCustomerView luckyCustomerView = new ListLuckyCustomerView();
    luckyCustomerView.loadView();
    titleLuckyCustomerPanel.setContentContainer(luckyCustomerView);
    add(titlePotentialCustomerPanel);
    add(titleLuckyCustomerPanel);
  }

}
