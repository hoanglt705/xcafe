/*
 * IPageChangeListener
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

package com.s3s.ssm.view.component;

import java.util.EventListener;

import javax.swing.event.ChangeEvent;

/**
 * The listener interface for receiving the page number changed.
 * 
 * @see PagingNavigator
 * @author Phan Hong Phuc
 * @since Nov 13, 2011
 * 
 */
public interface IPageChangeListener extends EventListener {
    /**
     * Invoke when the page number is changed.
     */
    public void doPageChanged(ChangeEvent e);
}