package com.s3s.ssm.view;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import com.s3s.ssm.util.i18n.ControlConfigUtils;

public abstract class AbstractView extends JPanel {
  private static final long serialVersionUID = 1L;
  protected Map<String, Object> request = new HashMap<String, Object>();

  public enum EditActionEnum {
    EDIT, NEW
  }

  public static final String PARAM_ACTION = "action";
  public static final String PARAM_ENTITY_ID = "entityId";
  public static final String PARAM_LIST_VIEW = "listView";
  protected static final String PARAM_READONLY = "readOnly";

  public AbstractView(Map<String, Object> params) {
    this.request = params;
  }

  protected String getMessage(String key) {
    return ControlConfigUtils.getString(key);
  }
}
