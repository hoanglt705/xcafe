/*
 * ImageUtils
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

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * 
 */
public class IziImageUtils {

  private IziImageUtils() {
  }

  /**
   * Get icon with original size.
   * 
   * @param imagePath
   * @return
   */
  public static ImageIcon getIcon(String imagePath) {
    return new ImageIcon(IziImageUtils.class.getResource(imagePath));
  }

  /**
   * Get icon scaled to ({@link #SMALL_SIZE} x {@link #SMALL_SIZE}).
   * 
   * @param imagePath
   * @return
   */
  public static ImageIcon getSmallIcon(String imagePath) {
    return getIcon(imagePath, WidgetConstant.SMALL_SIZE);
  }

  public static ImageIcon getSize24Icon(String imagePath) {
    return getIcon(imagePath, WidgetConstant.SIZE_24);
  }

  public static ImageIcon getSmallIcon(byte[] bytes) {
    return getIcon(bytes, WidgetConstant.SMALL_SIZE);
  }

  public static ImageIcon getMediumIcon(String imagePath) {
    return getIcon(imagePath, WidgetConstant.MEDIUM_SIZE);
  }

  public static ImageIcon getMediumIcon(byte[] bytes) {
    return getIcon(bytes, WidgetConstant.MEDIUM_SIZE);
  }

  public static ImageIcon getBigIcon(String imagePath) {
    return getIcon(imagePath, WidgetConstant.BIG_SIZE);
  }

  public static ImageIcon getBigIcon(byte[] bytes) {
    return getIcon(bytes, WidgetConstant.BIG_SIZE);
  }

  public static ImageIcon getIcon(String imagePath, int size) {
    Image image = new ImageIcon(IziImageUtils.class.getResource(imagePath)).getImage();
    ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(size, size, Image.SCALE_SMOOTH));
    imageIcon.setDescription(imagePath);
    return imageIcon;
  }

  public static ImageIcon getIcon(byte[] bytes, int size) {
    Image image = new ImageIcon(bytes).getImage();
    return new ImageIcon(image.getScaledInstance(size, size, Image.SCALE_SMOOTH));
  }

}
