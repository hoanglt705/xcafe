/*
 * AbstractEditView
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

import java.awt.Dimension;
import java.awt.Window;
import java.util.Map;

import javax.swing.SwingUtilities;

import com.s3s.ssm.dto.base.IIdObject;
import com.s3s.ssm.util.IziClassUtils;
import com.s3s.ssm.util.WindowUtilities;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.AbstractView;
import com.s3s.ssm.view.list.AListEntityView;

/**
 * 
 */
public abstract class AbstractEditView<T extends IIdObject> extends AbstractView implements
        ITitleView {
  public static final String NEW_TITLE = ControlConfigUtils.getString("label.tab.new");

  private static final long serialVersionUID = 5467303241585854634L;

  protected T entity;

  public AbstractEditView(Map<String, Object> inputParams) {
    super(inputParams);
    EditActionEnum action = (EditActionEnum) request.get(PARAM_ACTION);
    if (action == null) {
      action = EditActionEnum.NEW;
      request.put(PARAM_ACTION, action);
    }
    if (action == EditActionEnum.NEW) {
      entity = loadForCreate(request);
    } else if (action == EditActionEnum.EDIT) {
      entity = loadForEdit();
    } else {
      throw new UnsupportedOperationException("This operation is not handled : " + action);
    }

  }

  protected boolean isCreateNew() {
    return (EditActionEnum) request.get(PARAM_ACTION) == EditActionEnum.NEW;
  }

  /**
   * Append default attributes for entity.
   * 
   * @param entity
   */
  // TODO: dont need to put request
  protected T loadForCreate(Map<String, Object> request) {
    try {
      return getEntityClass().newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException("There is a problem when init the entity");
    }
  }

  protected abstract T loadForEdit();

  @Override
  public String getTitle() {
    return entity.getId() == null ? NEW_TITLE : getDefaultTitle(entity);
  }

  protected abstract String getDefaultTitle(T entity);

  public boolean focus() {
    return requestFocusInWindow();
  }

  public void setListView(AListEntityView<T> listView) {
    this.request.put(PARAM_LIST_VIEW, listView);
  }

  @SuppressWarnings("unchecked")
  public AListEntityView<T> getListView() {
    return (AListEntityView<T>) request.get(PARAM_LIST_VIEW);
  }

  @SuppressWarnings("unchecked")
  protected Class<T> getEntityClass() {
    return (Class<T>) IziClassUtils.getArgumentClass(getClass());
  }

  public T getEntity() {
    return entity;
  }

  protected void setSizeParentWindoẉ̣(Dimension size) {
    Window window = (Window) SwingUtilities.getRoot(this);
    window.setSize(size);
    WindowUtilities.centerOnScreen(window);
  }

  /**
   * Get the size of the dialog fit with the edit view. Maximum size is equal with the fullscreen size.
   * 
   * @param detailViewSize
   * @return
   */
  public Dimension getFitSize() {
    Dimension detailViewSize = getPreferredSize();
    int w = detailViewSize.width + 25;
    int h = detailViewSize.height + 45;
    Dimension fullSize = WindowUtilities.getFullScreenSize();
    if (w > fullSize.width) {
      w = fullSize.width;
    }
    if (h > fullSize.height) {
      h = fullSize.height;
    }
    return new Dimension(w, h);
  }
}
