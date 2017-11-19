/*
 * SListUtilLnF
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

package com.s3s.ssm.view.list;

import javax.swing.JFormattedTextField;

/**
 * The util apply some custom L&F for each particular component.
 * 
 * @author Phan Hong Phuc
 * @since Apr 7, 2012
 */
public class SLooknFeelUtil {

    public static void applyFormattedTextFieldCellEditor(JFormattedTextField formattedTextField) {
        // if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
        // UIDefaults overrides = UIManager.getLookAndFeel().getDefaults();
        // overrides.put("FormattedTextField.contentMargins", new Insets(0, 0, 0, 0));
        // formattedTextField.putClientProperty("Nimbus.Overrides", overrides);
        // formattedTextField.putClientProperty("Nimbus.Overrides.InheritDefaults", false);
        // }
    }

}
