/*
 * LayoutBuilder
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

import javax.swing.JPanel;

/**
 * LayoutBuilders exist to make it easier to do component layouts. Typical usage is to create an instance of the
 * builder, add components to it, the call the builder's {@link #getPanel()}method.
 */
public interface LayoutBuilder {
    /**
     * Creates and returns a JPanel with all the given components in it, using the "hints" that were provided to the
     * builder.
     * 
     * @return a new JPanel with the components laid-out in it; never null
     */
    JPanel getPanel();
}
