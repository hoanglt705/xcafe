/*
 * PagingNavigator
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

package com.s3s.ssm.view.component;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.text.NumberFormatter;

import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;

public class PagingNavigator extends JPanel {
  private static final long serialVersionUID = -8782466982711611127L;
  private JButton btnFirst;
  private JButton btnPrevious;
  private JButton btnNext;
  private JButton btnLast;
  private final JLabel lblTotalPageNumber;
  private int totalPage = 1;
  private int currentPage = 1;
  private JFormattedTextField txtCurrentPageNumber;

  private final List<IPageChangeListener> pageChangeListeners = new ArrayList<>();

  public PagingNavigator(int totalPage) {
    this(totalPage, 1);
  }

  public PagingNavigator(int totalPage, int currentPage) {
    setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    checkTotalPage(totalPage);
    checkCurrentPage(totalPage, currentPage);

    initFirstButton();
    initLastButton();
    initPreviousButton();
    initNextButton();

    this.totalPage = totalPage;
    this.currentPage = currentPage;

    initCurrentPageNumber();

    lblTotalPageNumber = new JLabel();
    lblTotalPageNumber.setName("lblTotalPageNumber");
    setCurrentPage(currentPage);
    setTotalPage(totalPage);

    add(btnFirst);
    add(btnPrevious);
    add(txtCurrentPageNumber);
    add(lblTotalPageNumber);
    add(btnNext);
    add(btnLast);
  }

  private void initCurrentPageNumber() {
    NumberFormatter numFormatter = new NumberFormatter();
    numFormatter.setValueClass(Integer.class);
    txtCurrentPageNumber = new JFormattedTextField(numFormatter);
    txtCurrentPageNumber.setName("txtCurrentPageNumber");
    txtCurrentPageNumber.setColumns(3);
    txtCurrentPageNumber.setInputVerifier(new InRangeVerifier());
    txtCurrentPageNumber.setHorizontalAlignment(SwingConstants.RIGHT);
    txtCurrentPageNumber.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          validateCurrentPageInput(txtCurrentPageNumber);
        }
      }

    });
  }

  private void initNextButton() {
    btnNext = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.NEXT_PAGINATION_ICON));
    btnNext.setName("btnNext");
    btnNext.addActionListener(e -> {
      setCurrentPage(getCurrentPage() + 1);
    });
  }

  private void initPreviousButton() {
    btnPrevious = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.PREVIOUS_PAGINATION_ICON));
    btnPrevious.setName("btnPrevious");
    btnPrevious.addActionListener(e -> {
      setCurrentPage(getCurrentPage() - 1);
    });
  }

  private void initLastButton() {
    btnLast = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.LAST_PAGINATION_ICON));
    btnLast.setName("btnLast");
    btnLast.addActionListener(e -> {
      setCurrentPage(PagingNavigator.this.totalPage);
    });
  }

  private void initFirstButton() {
    btnFirst = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.FIRST_PAGINATION_ICON));
    btnFirst.setName("btnFirst");
    btnFirst.addActionListener(e -> {
      setCurrentPage(1);
    });
  }

  private void checkTotalPage(int totalPage) {
    if (totalPage <= 0) {
      throw new IllegalArgumentException("The total page must greater than 0");
    }
  }

  private void checkCurrentPage(int totalPage, int currentPage) {
    if (currentPage <= 0 || currentPage > totalPage) {
      throw new IllegalArgumentException(
              "The current page must greater than 0 and less than or equal totalPage");
    }
  }

  /**
   * Set the current page.
   * 
   * @param currentPage
   *          the currentPage.
   */
  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
    checkCurrentPage(totalPage, currentPage);
    enableDisableButtons(currentPage, totalPage);
    txtCurrentPageNumber.setValue(currentPage);
    txtCurrentPageNumber.revalidate();
    txtCurrentPageNumber.repaint();
    firePageChangeListener();
  }

  /**
   * Enable or disable the buttons when currentPage or totalPage is changed.
   */
  private void enableDisableButtons(int currentPage, int totalPage) {
    boolean isBeginning = (currentPage == 1);
    boolean isEndding = (currentPage == totalPage);
    btnPrevious.setEnabled(!isBeginning);
    btnFirst.setEnabled(!isBeginning);
    btnNext.setEnabled(!isEndding);
    btnLast.setEnabled(!isEndding);
  }

  /**
   * Re-set the total page.
   * 
   * @param totalPage
   *          the total page.
   */
  public void setTotalPage(int totalPage) {
    checkTotalPage(totalPage);
    if (totalPage < getCurrentPage()) {
      throw new IllegalArgumentException("The total page can't not less than currentPage");
    }

    this.totalPage = totalPage;
    enableDisableButtons(getCurrentPage(), totalPage);
    lblTotalPageNumber.setText("/" + totalPage);
  }

  /**
   * Get the current page.
   * 
   * @return the current page.
   */
  public int getCurrentPage() {
    return (int) (txtCurrentPageNumber.getValue() == null ? 1 : txtCurrentPageNumber.getValue());
  }

  private void firePageChangeListener() {
    ChangeEvent e = new ChangeEvent(this);
    for (IPageChangeListener pl : pageChangeListeners) {
      pl.doPageChanged(e);
    }
  }

  public void addPageChangeListener(IPageChangeListener listener) {
    pageChangeListeners.add(listener);
  }

  public void removePageChangeListener(IPageChangeListener listener) {
    pageChangeListeners.remove(listener);
  }

  public class InRangeVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
      JFormattedTextField currentPageNumberField = (JFormattedTextField) input;
      return validateCurrentPageInput(currentPageNumberField);
    }
  }

  private boolean validateCurrentPageInput(JFormattedTextField currentPageNumberField) {
    try {
      currentPageNumberField.commitEdit();
      int value = (int) currentPageNumberField.getValue();

      if ((value >= 1) && (value <= totalPage)) {
        if (currentPage != value) {
          setCurrentPage(value);
        }
        currentPageNumberField.setBackground(Color.WHITE);
        return true;
      }
      currentPageNumberField.setBackground(Color.PINK);
      return false;
    } catch (ParseException e) {
      currentPageNumberField.setBackground(Color.PINK);
    }
    return false;
  }

}
