/*
 * DetailAttribute
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
package com.s3s.ssm.view.edit;

import java.util.List;

import org.springframework.util.Assert;

import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class DetailAttribute {
  private String name;
  private DetailFieldType type;
  private boolean isEditable = true;
  private boolean isEnable = true;
  private boolean isMandatory = false;
  private Object value; // The initial value for the raw attribute.
  private boolean isRaw = false;
  @SuppressWarnings("rawtypes")
  private List dataList;

  private IComponentInfo componentInfo;

  /** The property for layout the attribute. The attribute is rendered in new line or not. */
  private boolean newColumn = false;
  private int width;
  private String label;

  public DetailAttribute(String name, DetailFieldType type, boolean raw) {
    this.name = name;
    this.type = type;
    this.isRaw = raw;
  }

  public String getName() {
    return name;
  }

  public DetailFieldType getType() {
    return type;
  }

  public boolean isEditable() {
    return isEditable;
  }

  public DetailAttribute editable(boolean isEditable) {
    this.isEditable = isEditable;
    return this;
  }

  public boolean isEnable() {
    return isEnable;
  }

  public DetailAttribute enable(boolean isEnable) {
    this.isEnable = isEnable;
    return this;
  }

  public boolean isMandatory() {
    return isMandatory;
  }

  public DetailAttribute mandatory(boolean isMandatory) {
    this.isMandatory = isMandatory;
    return this;
  }

  /** Add new column. (The attribute will be rendered in new column). */
  public DetailAttribute newColumn() {
    this.newColumn = true;
    return this;
  }

  public boolean isNewColumn() {
    return newColumn;
  }

  /**
   * The width of field. The default value is {@link UIConstants#DEFAULT_WIDTH}.
   * 
   * @param width
   * @return
   */
  public DetailAttribute width(int width) {
    this.width = width;
    return this;
  }

  public int getWidth() {
    return width;
  }

  public boolean isRaw() {
    return isRaw;
  }

  public Object getValue() {
    return value;
  }

  /**
   * Set the initial value for the raw attribute.
   * 
   * @param value
   *          the initial value for the attribute.
   * @return
   */
  public DetailAttribute value(Object value) {
    Assert.isTrue(isRaw, "Not allow to set value for not raw attribute.");
    this.value = value;
    return this;
  }

  public DetailAttribute componentInfo(IComponentInfo componentInfo) {
    this.componentInfo = componentInfo;
    return this;
  }

  public IComponentInfo getComponentInfo() {
    return componentInfo;
  }

  public String getLabel() {
    return label;
  }

  public DetailAttribute label(String label) {
    this.label = label;
    return this;
  }

  public DetailAttribute setRaw(boolean isRaw) {
    this.isRaw = isRaw;
    return this;
  }

  @SuppressWarnings("rawtypes")
  public List getDataList() {
    return dataList;
  }

  @SuppressWarnings("rawtypes")
  public void setDataList(List dataList) {
    this.dataList = dataList;
  }
}
