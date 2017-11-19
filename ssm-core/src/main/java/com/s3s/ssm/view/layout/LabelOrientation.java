/*
 * LabelOrientation
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

package com.s3s.ssm.view.layout;

import javax.swing.SwingConstants;

/**
 * @author Le Thanh Hoang
 * 
 */
public class LabelOrientation {
  public static final LabelOrientation TOP = new LabelOrientation(SwingConstants.TOP, "Top");

  public static final LabelOrientation BOTTOM = new LabelOrientation(SwingConstants.BOTTOM, "Bottom");

  public static final LabelOrientation LEFT = new LabelOrientation(SwingConstants.LEFT, "Left");

  public static final LabelOrientation RIGHT = new LabelOrientation(SwingConstants.RIGHT, "Right");

  private int code;
  private String label;

  private LabelOrientation(int code, String label) {
    this.code = code;
    this.label = label;
  }
}
