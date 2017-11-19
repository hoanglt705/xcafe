/*
 * SSplashScreen
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

package com.s3s.ssm.view;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import com.s3s.ssm.util.IziImageUtils;

public class SSplashScreen extends JWindow {
    private static final long serialVersionUID = 9219296900804342289L;
    private JProgressBar progressBar;
    private JLabel loadingInfo;

    public SSplashScreen() {
        super();
        JPanel panel = new JPanel(new MigLayout("ins 0, fill, wrap", "fill, grow"));
        JLabel image = new JLabel(IziImageUtils.getIcon("/images/iziRMS.png"));
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        loadingInfo = new JLabel("Loading");
        JLabel versionLabel = new JLabel("Version 1.3.4.0 - Realease Date: 05/05/2012", SwingConstants.RIGHT);
        panel.add(image, "grow");
        panel.add(progressBar, "grow");
        panel.add(loadingInfo, "grow");
        panel.add(versionLabel, "grow");
        panel.setBorder(BorderFactory.createRaisedBevelBorder());
        add(panel);
        pack();
    }

    /**
     * Set progress value
     */
    public void setValue(int value, String string) {
        progressBar.setValue(value);
        loadingInfo.setText(string);
    }

}
