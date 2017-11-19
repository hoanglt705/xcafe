package com.s3s.ssm.util;

import java.awt.Color;

/**
 * Wrapper for basic colors (13 of java.awt.color) as enum
 * 
 */
public enum ColorEnum {
  /**  */
  WHITE("WHITE"),
  /**  */
  DARK_GRAY("DARK_GRAY"),
  /**  */
  GRAY("GRAY"),
  /**  */
  LIGHT_GRAY("LIGHT_GRAY"),
  /**  */
  BLACK("BLACK"),
  /**  */
  RED("RED"),
  /**  */
  PINK("PINK"),
  /**  */
  ORANGE("ORANGE"),
  /**  */
  YELLOW("YELLOW"),
  /**  */
  GREEN("GREEN"),
  /**  */
  CYAN("CYAN"),
  /**  */
  MAGENTA("MAGENTA"),
  /**  */
  BLUE("BLUE");

  /**  */
  private String fName;

  /**
   * Constructor
   * 
   * @param name
   */
  ColorEnum(String name) {
    fName = name;
  }

  /**
   * @return color
   */
  public Color getColor() {
    Color c = null;
    if (fName.equalsIgnoreCase("WHITE")) {
      c = Color.white;
    } else if (fName.equalsIgnoreCase("DARK_GRAY")) {
      c = Color.darkGray;
    } else if (fName.equalsIgnoreCase("GRAY")) {
      c = Color.gray;
    } else if (fName.equalsIgnoreCase("LIGHT_GRAY")) {
      c = Color.lightGray;
    } else if (fName.equalsIgnoreCase("BLACK")) {
      c = Color.black;
    } else if (fName.equalsIgnoreCase("RED")) {
      c = Color.red;
    } else if (fName.equalsIgnoreCase("PINK")) {
      c = Color.pink;
    } else if (fName.equalsIgnoreCase("ORANGE")) {
      c = Color.orange;
    } else if (fName.equalsIgnoreCase("YELLOW")) {
      c = Color.yellow;
    } else if (fName.equalsIgnoreCase("GREEN")) {
      c = Color.green;
    } else if (fName.equalsIgnoreCase("CYAN")) {
      c = Color.cyan;
    } else if (fName.equalsIgnoreCase("MAGENTA")) {
      c = Color.magenta;
    } else if (fName.equalsIgnoreCase("BLUE")) {
      c = Color.blue;
    }
    return c;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return fName;
  }

  /**
   * @param name
   * @return color with specified name
   */
  public static Color getColorByName(String name) {
    if (name != null) {
      for (ColorEnum color : ColorEnum.values()) {
        if (color.toString().equals(name)) {
          return color.getColor();
        }
      }
    }
    return null;
  }

  /**
   * @param nativeColor
   * @return color
   */
  public static Color getContrastNativeColor(Color nativeColor) {
    if (nativeColor.equals(Color.white)) {
      return Color.RED;
    }
    if (nativeColor.equals(Color.lightGray)) {
      return Color.BLACK;
    }
    if (nativeColor.equals(Color.gray)) {
      return Color.PINK;
    }
    if (nativeColor.equals(Color.darkGray)) {
      return Color.ORANGE;
    }
    if (nativeColor.equals(Color.black)) {
      return Color.CYAN;
    }
    if (nativeColor.equals(Color.red)) {
      return Color.WHITE;
    }
    if (nativeColor.equals(Color.pink)) {
      return Color.GRAY;
    }
    if (nativeColor.equals(Color.orange)) {
      return Color.DARK_GRAY;
    }
    if (nativeColor.equals(Color.yellow)) {
      return Color.BLUE;
    }
    if (nativeColor.equals(Color.green)) {
      return Color.MAGENTA;
    }
    if (nativeColor.equals(Color.magenta)) {
      return Color.GREEN;
    }
    if (nativeColor.equals(Color.cyan)) {
      return Color.BLACK;
    }
    if (nativeColor.equals(Color.blue)) {
      return Color.YELLOW;
    }
    return Color.WHITE;
  }

}
