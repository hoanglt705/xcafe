/*
 * ListBankView
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.s3s.ssm.view.dashboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.Timer;

import com.s3s.ssm.config.DashboardContextProvider;
import com.s3s.ssm.dto.PaymentDto;
import com.s3s.ssm.view.list.AListStatisticView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;
import com.s3s.ssm.view.util.IUpdatable;

public class ListTopLatestPaymentView extends AListStatisticView<PaymentDto> implements IUpdatable {
  private static final long serialVersionUID = 1898147147716601668L;
  private static final int PAGE_SIZE = 10;
  private final Timer refreshTimer;

  public ListTopLatestPaymentView() {
    remove(tabbedPane);
    add(contentPane);
    setVisiblePagingNavigator(false);
    getTable().setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    getTable().setShowVerticalLines(true);
    getTable().setFilterRowVisible(false);
    refreshTimer = new Timer(DELAY, new ActionListener() {
      @SuppressWarnings("unused")
      @Override
      public void actionPerformed(ActionEvent e) {
        refreshAndBackToFirstPage();
      }
    });
    refreshTimer.start();
    refreshData(0);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("paymentDate", ListRendererType.DATE);
    listDataModel.addColumn("paymentContent.name", ListRendererType.TEXT);
    listDataModel.addColumn("amount", ListRendererType.NUMBER);
  }

  @SuppressWarnings("unused")
  @Override
  protected List<PaymentDto> loadData(int firstIndex, int maxResults) {
    return DashboardContextProvider.getInstance().getDashboardService().getLatestPayment(PAGE_SIZE);
  }

  @Override
  protected int getDefaultPageSize() {
    return PAGE_SIZE;
  }

  @Override
  public void stop() {
    refreshTimer.stop();

  }

  @Override
  public void start() {
    refreshTimer.start();
  }

  @Override
  protected int getTotalPages() {
    // TODO Auto-generated method stub
    return 1;
  }

  @Override
  protected JComponent createSearchPanel() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected void clearCriteria() {

  }
}
