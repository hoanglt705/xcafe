/*
 * IziReportUtils
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

import java.awt.Window;
import java.util.Collection;
import java.util.Map;

import javax.swing.JDialog;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

/**
 * @author Phan Hong Phuc
 * @since May 19, 2012
 */
public class IziJasperReportUtils {
    private IziJasperReportUtils() {
    }

    /**
     * Show the report in a dialog.
     * 
     * @param parentView
     *            The parent view of the dialog.
     * @param pathToJasper
     *            path to the .jasper file.
     * @param param
     *            parameters of the report.
     * @param data
     *            the data of the report.
     */
    public static <T> void showReportDialog(Window parentView, String pathToJasper, Map<String, Object> param,
            Collection<T> data) {
        JDialog dialog = new JDialog(parentView);
        dialog.setModal(true);
        JRDataSource ds = new JRBeanCollectionDataSource(data);
        try {
            JasperPrint jp = JasperFillManager.fillReport(IziJasperReportUtils.class.getResourceAsStream(pathToJasper),
                    param, ds);
            JRViewer jv = new JRViewer(jp);
            dialog.add(jv);
            dialog.setSize(WindowUtilities.getFullScreenSize());
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } catch (JRException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }
}
