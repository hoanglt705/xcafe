/*
 * UIConstants
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

package com.s3s.ssm.util;

import java.awt.Color;
import java.awt.Font;

/**
 * Constants for swing user interface classes.
 * 
 * 
 */
public interface UIConstants {

  Color DARK_GREEN = new Color(0, 100, 0);

  Color LIGHT_YELLOW = new Color(255, 255, 224);

  Color VIOLET = new Color(233, 207, 236);

  String ELLIPSIS = "...";

  String BLANK = " ";

  String HYPHEN = "-";

  /**
   * The Java look and Feel standard for one screen space between GUI Components.
   */
  int ONE_SPACE = 5;

  /**
   * The Java look and Feel standard for two screen spaces between GUI Components.
   */
  int TWO_SPACES = 11;

  /**
   * The Java look and Feel standard for three screen spaces between GUI Components.
   */
  int THREE_SPACES = 17;

  /**
   * The Java look and Feel standard for border spacing.
   */
  int STANDARD_BORDER = TWO_SPACES;

  /**
   * Symbolic name for absence of keystroke mask.
   */
  int NO_KEYSTROKE_MASK = 0;

  /**
   * Suggested width for a component in edit view.
   */
  public final static int DEFAUL_TEXTAREA_ROWS = 4;

  /**
   * Suggested width for a fixed cell of the table
   */
  public final static int DEFAULT_ROW_HEADER_WIDTH = 20;

  /**
   * Suggested width for a <code>JTextField</code> storing a file path.
   */
  int FILE_PATH_FIELD_WIDTH = 30;

  /**
   * Maximum length for some <code>JLabel</code>s, beyond which the text will be truncated.
   */
  int MAX_LABEL_WIDTH = 150;

  /**
   * Color of row when mouse over on a row.
   */
  Color HIGHLIGHT_ROW_COLOR = new Color(133, 205, 255);

  Color GRID_COLOR = Color.GRAY;

  int PRODUCT_CODE_COLUMN_WIDTH = 180;

  int PRODUCT_NAME_COLUMN_WIDTH = 250;

  int UOM_COLUMN_WIDTH = 60;

  int QTY_COLUMN_WIDTH = 93;

  int AMT_COLUMN_WIDTH = 110;

  int NUMBER_FIELD_WIDTH = 125;

  int DEFAULT_COLUMN_WIDTH = 110;

  Font DEFAULT_BOLD_FONT = new Font("Arial", Font.BOLD, 12);
  Font DEFAULT_ITALIC_FONT = new Font("Arial", Font.ITALIC, 12);
  Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);

  Color INFO_COLOR = new Color(252, 252, 209);

  int REMARK_COLUMN_WIDTH_300 = 300;

  int ROW_HEIGHT = 25;

  int FONT_SIZE = 12;
}
