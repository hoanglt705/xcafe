/*
 * RadioButtonsGroup
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

import com.s3s.ssm.interfaces.IDirtiableObject;

/**
 * The radio buttons group component.
 * 
 * @author Phan Hong Phuc
 * 
 * @param <T>
 *          the data type of value for each radio button
 */
public class RadioButtonsGroup<T> extends JPanel implements IDirtiableObject {
  private static final long serialVersionUID = 6365217325772644824L;
  private Map<JRadioButton, T> radioBtn2Value;

  /**
   * Init the radio button group with the labels is <code>value.toString()</code>.
   * 
   * @param values
   */
  public RadioButtonsGroup(List<T> values) {
    this(values, null);
  }

  /**
   * Init the radio button group with the labels is <code>value.toString()</code>.
   * 
   * @param values
   * @param selectedValue
   *          the value is selected.
   */
  public RadioButtonsGroup(List<T> values, T selectedValue) {
    Map<T, String> value2Label = new HashMap<>(values.size());
    for (T val : values) {
      value2Label.put(val, val.toString());
    }
    initComponent(value2Label, selectedValue);
  }

  public RadioButtonsGroup(Map<T, String> value2Label) {
    this(value2Label, null);
  }

  /**
   * 
   * @param value2Label
   * @param selectedValue
   */
  public RadioButtonsGroup(Map<T, String> value2Label, T selectedValue) {
    initComponent(value2Label, selectedValue);
  }

  private void initComponent(Map<T, String> value2Label, T selectedValue) {
    setLayout(new MigLayout("ins 0"));
    radioBtn2Value = new HashMap<>(value2Label.size());
    ButtonGroup btnGroup = new ButtonGroup();
    for (Entry<T, String> val2Lbl : value2Label.entrySet()) {
      JRadioButton rb = new JRadioButton(val2Lbl.getValue());
      T val = val2Lbl.getKey();
      radioBtn2Value.put(rb, val);
      btnGroup.add(rb);
      if (val.equals(selectedValue)) {
        rb.setSelected(true);
      }
      add(rb);
    }
  }

  /**
   * Get the value selected.
   * 
   * @return the value is selected.
   */
  public T getSelectedValue() {
    for (JRadioButton rb : radioBtn2Value.keySet()) {
      if (rb.isSelected()) {
        return radioBtn2Value.get(rb);
      }
    }
    return null;
  }

  @Override
  public void setEnabled(boolean enabled) {
    for (JRadioButton rb : radioBtn2Value.keySet()) {
      rb.setEnabled(enabled);
    }
  }

  @Override
  public void fireChangeEvent() {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isDirty() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void addChangeListener(ChangeListener listener) {
    // TODO Auto-generated method stub

  }
}
