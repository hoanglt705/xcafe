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
import com.s3s.ssm.dto.LatestInvoiceDto;
import com.s3s.ssm.view.list.AListStatisticView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;
import com.s3s.ssm.view.util.IUpdatable;

public class ListTopLatestTableInvoiceView extends AListStatisticView<LatestInvoiceDto> implements IUpdatable {
  private static final long serialVersionUID = 1898147147716601668L;

  private static final int PAGE_SIZE = 10;
  private final Timer refreshTimer;

  public ListTopLatestTableInvoiceView() {
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
    listDataModel.addColumn("foodTable", ListRendererType.TEXT);
    listDataModel.addColumn("createdDate", ListRendererType.DATE);
    listDataModel.addColumn("amount", ListRendererType.NUMBER);
    listDataModel.addColumn("income", ListRendererType.NUMBER);
  }

  @SuppressWarnings("unused")
  @Override
  protected List<LatestInvoiceDto> loadData(int firstIndex, int maxResults) {
    return DashboardContextProvider.getInstance().getDashboardService().getLatestInvoice(PAGE_SIZE);
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
    return 1;
  }

  @Override
  protected JComponent createSearchPanel() {
    return null;
  }

  @Override
  protected void clearCriteria() {

  }
}
