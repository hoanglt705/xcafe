/*
 * Utf8ResourceBundle
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

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Utf8ResourceBundle {
    public static final ResourceBundle getBundle(String baseName) {
        ResourceBundle bundle = ResourceBundle.getBundle(baseName);
        return createUtf8PropertyResourceBundle(bundle);
    }

    public static final ResourceBundle getBundle(String baseName, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
        return createUtf8PropertyResourceBundle(bundle);
    }

    public static ResourceBundle getBundle(String baseName, Locale locale, ClassLoader loader) {
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
        return createUtf8PropertyResourceBundle(bundle);
    }

    private static ResourceBundle createUtf8PropertyResourceBundle(ResourceBundle bundle) {
        if (!(bundle instanceof PropertyResourceBundle))
            return bundle;

        return new Utf8PropertyResourceBundle((PropertyResourceBundle) bundle);
    }

    private static class Utf8PropertyResourceBundle extends ResourceBundle {
        PropertyResourceBundle bundle;

        private Utf8PropertyResourceBundle(PropertyResourceBundle bundle) {
            this.bundle = bundle;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.util.ResourceBundle#getKeys()
         */
        public Enumeration getKeys() {
            return bundle.getKeys();
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
         */
        protected Object handleGetObject(String key) {
            String value = (String) bundle.handleGetObject(key);
            try {
                if (value != null) {
                    return new String(value.getBytes("ISO-8859-1"), "UTF-8");
                }
                throw new MissingResourceException("Could not find out key", "string", key);
            } catch (UnsupportedEncodingException e) {
                throw new MissingResourceException("Could not find out key", "string", key);
            }
        }
    }
}
