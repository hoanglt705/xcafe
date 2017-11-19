/*
 * ControlConfigUtils
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
package com.s3s.ssm.util.i18n;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * TODO phamcongbang the purpose of this class ??? The class name is so common. We should separate the
 * function relate
 * to resource to the class name ResourceUtils such as.
 * 
 * @author phamcongbang
 * 
 */
public class ControlConfigUtils {
  private static final char MNEMONIC_CHAR = '&';
  private static final char TOOLTIP_CHAR = '#';
  private final static String CONSTANT_PROPERTY_FILE = "/config/overrides.properties";

  private static Font s_fontNormal;
  private static Font s_fontBold;
  private static Font s_fontSmallBold;
  private static Font s_fontMediumBold;
  private static Font s_fontCode;

  private static Properties m_properties = new Properties();
  /** Bundle of all messages, labels in current application language */
  transient protected static MultiFamilyResouceBundle s_messages = null;

  private static TimeZone s_defaultTimeZone = null;

  // ======================================================================
  // Static methods
  // ======================================================================

  public static void init() {
    try {
      m_properties.load(ControlConfigUtils.class.getResourceAsStream(CONSTANT_PROPERTY_FILE));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets font normal for all text in Ingest Application.
   * 
   * @return font normal
   */
  public static Font getFontNormal() {
    if (s_fontNormal == null) {
      init();
    }

    return s_fontNormal;
  }

  /**
   * Gets font bold for all text in Ingest Application.
   * 
   * @return font bold
   */
  public static Font getFontBold() {
    if (s_fontBold == null) {
      init();
    }

    return s_fontBold;
  }

  /**
   * Gets font small bold for all text in Ingest Application.
   * 
   * @return font small bold
   */
  public static Font getFontSmallBold() {
    if (s_fontSmallBold == null) {
      init();
    }

    return s_fontSmallBold;
  }

  /**
   * Gets font medium bold for all text in Ingest Application.
   * 
   * @return font small bold
   */
  public static Font getFontMediumBold() {
    if (s_fontMediumBold == null) {
      init();
    }

    return s_fontMediumBold;
  }

  /**
   * Gets font plain for all code used in Ingest Application.
   * 
   * @return font plain
   */
  public static Font getFontCode() {
    if (s_fontCode == null) {
      init();
    }

    return s_fontCode;
  }

  /**
   * Reads and returns the shortcut key from the id
   * 
   * @param key
   *          Id of message or control label
   * @return int shortcut key
   */
  public static int getShortcutKey(String key) {
    String text = s_messages.getString(key);
    return getMnemonic(text);
  }

  /**
   * Set message bundle. It is called at beginning of application or after login if language of user is
   * different from
   * english
   * 
   * @param lableMsgBundle
   *          bundle of message
   */
  public static void setLabelMessageBundle(Locale locale, String... messageFileNames) {
    s_messages = new MultiFamilyResouceBundle(locale, messageFileNames);
  }

  /**
   * Gets mnemonic character from given key
   * 
   * @param key
   *          key to get mnemonic character
   * @return mnemonic character
   */
  private static char getMnemonic(String message) {
    int pos = findSeparator(message, MNEMONIC_CHAR);
    if (pos >= 0 && pos < message.length()) {
      return message.charAt(pos + 1);
    }
    return '\0';
  }

  /**
   * Gets tooltip for the component.
   * 
   * @param key
   *          String
   * @return String
   */
  public static String getComponentTooltip(String key) {
    String result = null;
    try {
      String text = s_messages.getString(key);
      if (text != null) {
        int pos = findSeparator(text, TOOLTIP_CHAR);
        if (pos >= 0) {
          result = text.substring(pos + 1);
        }
      }
    } catch (Exception ex) {
      // nothing
    } // ignored
    return result;
  }

  private static int findSeparator(String text, char searchChar) {
    return findSeparator(text, searchChar, -1);
  }

  private static int findSeparator(String text, char searchChar, int from) {
    if (text == null) {
      return -1;
    }

    int pos = text.indexOf(searchChar, from);
    while (pos >= 0) {
      if (pos >= 0 && (pos >= text.length() - 1 || text.charAt(pos + 1) != searchChar)) {
        return pos;
      }
      pos = text.indexOf(searchChar, pos + 2);
    }
    return pos;
  }

  /**
   * Reads the message from the id and returns in a string.
   * 
   * @param strLabelMessageID
   *          Id of message or control label
   * @param key
   * @return String containing the full message
   */
  public static String getString(String key) {
    return getString(key, new String[] {});
  }

  /**
   * Reads the message from the id and returns in a string.
   * 
   * @param strLabelMessageID
   *          Id of message or control label
   * @param key
   * @param mesgPara
   * @return String containing the full message
   */
  public static String getString(String key, String... mesgPara) {
    String result = key;
    String text = s_messages.getString(key);
    if (text == null) {
      return null;
    }

    text = completeString(text, mesgPara);

    result = text;
    int pos = findSeparator(text, TOOLTIP_CHAR);
    if (pos >= 0) {
      result = text.substring(0, pos);
    }

    result = StringUtils.remove(result, MNEMONIC_CHAR);
    result = result.replaceAll("" + TOOLTIP_CHAR + TOOLTIP_CHAR, "" + TOOLTIP_CHAR);
    result = result.replaceAll("" + MNEMONIC_CHAR + MNEMONIC_CHAR, "" + MNEMONIC_CHAR);
    return result;
  }

  public static <T extends Enum<T>> String getEnumString(Class<T> enumClass, T enumValue) {
    return getString("enum" + '.' + enumClass.getSimpleName() + '.' + enumValue.name());
  }

  public static <T extends Enum<T>> List<String> getEnumStrings(Class<T> enumClass) {
    T[] enumConstants = enumClass.getEnumConstants();
    List<String> enumStrings = new ArrayList<String>(enumConstants.length);
    for (T e : enumConstants) {
      enumStrings.add(e.toString());
    }
    return enumStrings;
  }

  private static String completeString(String text, String[] mesgPara) {
    int i = 0;
    for (String param : mesgPara) {
      text.replaceFirst("{" + i + "}", param);
      i++;
    }
    return text;
  }

  /**
   * Gets name of temp directory in client.properties file.
   * 
   * @return temp dir.
   */
  public static String getTempDirectory() {
    return System.getProperty("java.io.tmpdir");
  }

  /**
   * Recursively set font for a component and its all components.
   * 
   * @param comp
   *          {@link Component}.
   * @param font
   *          {@link Font} to set.
   */
  public static void setFontRecursively(Component comp, Font font) {
    comp.setFont(font);
    if (comp instanceof Container) {
      Container cont = (Container) comp;
      for (int j = 0, ub = cont.getComponentCount(); j < ub; ++j) {
        setFontRecursively(cont.getComponent(j), font);
      }
    }
  }

  /**
   * Sets the default time zone for the whole GUI application.
   * 
   * @param timezone
   *          {@link TimeZone} default timezone to set. Normally this is gotten from server.
   */
  public static void setDefaultTimeZone(TimeZone timezone) {
    s_defaultTimeZone = timezone;
  }

  /**
   * Gets the default time zone of the GUI, which is set when the application starts up.
   * 
   * @return {@link TimeZone}.
   */
  public static TimeZone getDefaultTimeZone() {
    return s_defaultTimeZone;
  }
}
