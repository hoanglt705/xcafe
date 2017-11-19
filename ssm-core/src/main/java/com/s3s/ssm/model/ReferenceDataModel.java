/*
 * ReferenceDataModel
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
package com.s3s.ssm.model;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.jdesktop.swingx.renderer.JRendererLabel;

/**
 * This class represent the model on the AbstractDetailView for reference data. Eg. ComboBox, MultiSelectBox
 * 
 * @author phamcongbang
 * 
 */
public class ReferenceDataModel {
  private final Map<String, ReferenceData<?>> refDataListMap = new HashMap<>();

  public void putRefDataList(String refId, ReferenceData<?> refData) {
    if (refData.getRenderer() == null) {
      refData.setRenderer(getDefaultListCellRenderer());
    }
    refDataListMap.put(refId, refData);
  }

  public void putRefDataList(String refId, List<?> values) {
    putRefDataList(refId, values, null);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public void putRefDataList(String refId, Set<?> values) {
    putRefDataList(refId, new ArrayList(values), null);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public void putRefDataList(String refId, List<?> values, ListCellRenderer<?> renderer) {
    ListCellRenderer<?> pRenderer = renderer;
    if (pRenderer == null) {
      pRenderer = getDefaultListCellRenderer();
    }
    ReferenceData refData = new ReferenceData(values, pRenderer);
    refDataListMap.put(refId, refData);
  }

  private ListCellRenderer<?> getDefaultListCellRenderer() {
    return new DefaultListCellRenderer() {
      private static final long serialVersionUID = -2480226005447134931L;

      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value, int index,
              boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        // if (value == null) {
        // setText(ControlConfigUtils.getString("label.dropdown.pleaseSelect"));
        // } else if (value instanceof Enum) {
        // Enum tempValue = (Enum) value;
        // setText(ControlConfigUtils.getEnumString(tempValue.getDeclaringClass(), tempValue));
        // }
        return this;
      }
    };
  }

  public void putRefDataList(String refId, Object[] values) {
    List<?> listValue = Arrays.asList(values);
    putRefDataList(refId, listValue, null);
  }

  public Map<String, ReferenceData<?>> getRefDataListMap() {
    return refDataListMap;
  }

  /**
   * The reference data include the list of values and the renderer of those.
   * 
   * @author Phan Hong Phuc
   * @param <T>
   *          the data type of <code>values</code>
   */
  public class ReferenceData<T> {
    private List<T> values;
    private ListCellRenderer<T> renderer;
    private Map<T, String> value2LabelMap;

    /**
     * Init reference data with a list of values and the renderer.
     * 
     * @param refDataList
     *          list of values
     */
    public ReferenceData(List<T> values, ListCellRenderer<T> renderer) {
      this.values = new ArrayList<>(values);
      this.renderer = renderer;

    }

    @SuppressWarnings("unchecked")
    public void setRenderer(ListCellRenderer<?> listCellRenderer) {
      this.renderer = (ListCellRenderer<T>) listCellRenderer;
    }

    /**
     * Init reference data with a map of value-label. The default renderer is created, it's simply just render
     * a string for each value base on the map.
     * 
     * @param value2Label
     *          the map: value - the label rendering for that value.
     */
    public ReferenceData(final Map<T, String> value2Label) {
      this.value2LabelMap = value2Label;
      this.values = new ArrayList<>(value2Label.keySet());
      renderer = new ListCellRenderer<T>() {
        @SuppressWarnings("unused")
        @Override
        public Component getListCellRendererComponent(JList<? extends T> list, T value, int index,
                boolean isSelected, boolean cellHasFocus) {
          JRendererLabel label = new JRendererLabel();
          label.setText(value2Label.get(value));
          // if (list.getWidth() < label.getWidth()) {
          label.setToolTipText(label.getText());
          // }
          return label;
        }
      };
    }

    public ListCellRenderer<T> getRenderer() {
      return renderer;
    }

    public List<T> getValues() {
      return values;
    }

    public Map<T, String> getValue2LabelMap() {
      return value2LabelMap;
    }
  }
}
