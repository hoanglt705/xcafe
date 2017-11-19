/*
 * MultiFamilyResouceBundle
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MultiFamilyResouceBundle {

    private List<ResourceBundle> m_bundles = new ArrayList<ResourceBundle>();

    /**
     * Load and store the resouces for each family according to the current locale. Note that the order of the resource
     * in the list is counted for deciding the priority. The later the resource is added into the list, the higher is
     * its priority.
     * 
     * The message of FW is only loaded first.
     * 
     * @param locale
     *            the expected locale to load
     * @param resourceFamilyNames
     *            array of String. This is expected not empty.
     */
    public MultiFamilyResouceBundle(Locale locale, String... resourceFamilyNames) {
        m_bundles.clear();
        for (String resourceFamilyName : resourceFamilyNames) {
            // the add(int, ...) is used to make sure the last bundle has the
            // highest priority.
            // the justification is this method is only use one or twice when
            // starting the application
            m_bundles.add(0, Utf8ResourceBundle.getBundle(resourceFamilyName, locale));
        }
    }

    /**
     * See the default.
     */
    public MultiFamilyResouceBundle(String... resourceFamilyNames) {
        this(Locale.getDefault(), resourceFamilyNames);
    }

    /**
     * Keeps looking the text of the given key until it is found. Otherwise the key will be return;
     * 
     * @param key
     * @return text
     * @exception NullPointerException
     *                if <code>key</code> is <code>null</code>
     */
    public String getString(String key) {
        for (ResourceBundle bundle : m_bundles) {
            try {
                return bundle.getString(key);
            } catch (MissingResourceException e) {
                // no problem, we fall back to the other
            }
        }

        // finally, could not find out any message, return key.
        return key;
    }
}
