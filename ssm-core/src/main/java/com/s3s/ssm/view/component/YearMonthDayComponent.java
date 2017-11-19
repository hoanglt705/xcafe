/*
 * YearMonthDayComponent
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

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * @author Phan Hong Phuc
 * @since May 11, 2012
 */
public class YearMonthDayComponent extends JPanel implements ChangeListener {
  private static final long serialVersionUID = 3938641713515938912L;

  private static final int YEAR_MAX = 99;
  private static final int MONTH_MAX = 12;
  private static final int DAY_MAX = 30;
  private JSpinner yearSpn;
  private JSpinner monthSpn;
  private JSpinner daySpn;

  public YearMonthDayComponent() {
    FlowLayout fl = new FlowLayout(0, 0, 0);
    setLayout(fl);

    SpinnerNumberModel yearModel = new CycleSpinnerNumberModel(0, 0, YEAR_MAX, 1);
    yearSpn = new JSpinner(yearModel);
    yearSpn.setPreferredSize(new Dimension(46, yearSpn.getPreferredSize().height));
    SpinnerNumberModel monthModel = new CycleSpinnerNumberModel(0, 0, MONTH_MAX, 1);
    monthSpn = new JSpinner(monthModel);
    monthSpn.setPreferredSize(new Dimension(46, yearSpn.getPreferredSize().height));
    SpinnerNumberModel dayModel = new CycleSpinnerNumberModel(0, 0, DAY_MAX, 1);
    daySpn = new JSpinner(dayModel);
    daySpn.setPreferredSize(new Dimension(46, yearSpn.getPreferredSize().height));

    daySpn.addChangeListener(this);
    monthSpn.addChangeListener(this);
    yearSpn.addChangeListener(this);

    add(yearSpn);
    add(new JLabel(ControlConfigUtils.getString("TimeComponent.year")));
    add(monthSpn);
    add(new JLabel(ControlConfigUtils.getString("TimeComponent.month")));
    add(daySpn);
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    JSpinner source = (JSpinner) e.getSource();
    if (source == daySpn && ((int) source.getValue() == DAY_MAX)) {
      daySpn.setValue(0);
      monthSpn.setValue(monthSpn.getNextValue());
    }
    if (source == monthSpn && ((int) source.getValue() == MONTH_MAX)) {
      monthSpn.setValue(0);
      yearSpn.setValue(yearSpn.getNextValue());
    }
  }

  public class CycleSpinnerNumberModel extends SpinnerNumberModel {
    private static final long serialVersionUID = -8398387199137895712L;

    public CycleSpinnerNumberModel(int value, int minimum, int maximum, int stepSize) {
      super(value, minimum, maximum, stepSize);
    }

    @Override
    public Object getNextValue() {
      Object value = super.getNextValue();
      if (value == null) {
        value = getMinimum();
      }
      return value;

    }

    @Override
    public Object getPreviousValue() {
      Object value = super.getPreviousValue();
      if (value == null) {
        value = (Integer) getMaximum() - 1;
      }
      return value;
    }
  }

  public void setValueByDay(long day) {
    long dVal = day % DAY_MAX;
    daySpn.setValue((int) dVal);
    setValueByMonth(day / DAY_MAX);

  }

  public void setValueByMonth(long month) {
    long monVal = month % MONTH_MAX;
    monthSpn.setValue((int) monVal);
    setValueByYear(month / MONTH_MAX);

  }

  public void setValueByYear(long year) {
    yearSpn.setValue((int) year);
  }

  /**
   * Get time as mili-seconds.
   * 
   * @return
   */
  public long getValue() {
    return ((Integer) daySpn.getValue()) * 24 * 60 * 60 * 1000L + ((Integer) monthSpn.getValue()) * 30 * 24
            * 60
            * 60 * 1000L + ((Integer) yearSpn.getValue()) * 12 * 30 * 24 * 60 * 60 * 1000L;
  }

  /**
   * Set the time by miliseconds
   * 
   * @param milisecond
   */
  public void setValue(long milisecond) {
    setValueByDay(milisecond / 1000 / 60 / 60 / 24);
  }

  public void addChangeListener(ChangeListener listener) {
    yearSpn.addChangeListener(listener);
    monthSpn.addChangeListener(listener);
    daySpn.addChangeListener(listener);
  }

  public void setEditable(boolean editable) {
    ((DefaultEditor) yearSpn.getEditor()).getTextField().setEditable(editable);
    ((DefaultEditor) monthSpn.getEditor()).getTextField().setEditable(editable);
    ((DefaultEditor) daySpn.getEditor()).getTextField().setEditable(editable);
  }
}
