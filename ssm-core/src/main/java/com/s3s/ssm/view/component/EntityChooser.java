/*
 * EntityChooser
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

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.s3s.ssm.util.UIConstants;

/**
 * Entity chooser component.
 * 
 * @author Le Thanh Hoang
 * 
 */
public class EntityChooser<T> extends JPanel {
    private static final long serialVersionUID = 7757253525648364105L;
    private JTextField txtFldCode;
    private JLabel lblName;
    private JButton chooseBtn;
    private List<T> entityList;
    private T entity;
    private BeanWrapper beanWrapper;

    public EntityChooser() {
        this(null, null);
    }

    public EntityChooser(List<T> values) {
        this(values, null);
    }

    public EntityChooser(List<T> values, T entity) {
        entityList = values;
        if (entity != null) {
            this.entity = entity;
        }
        initialComponent();
    }

    private void initialComponent() {
        setLayout(new MigLayout("wrap 3, ins 0"));

        txtFldCode = new JTextField(20);
        lblName = new JLabel();

        setComponentValue();

        chooseBtn = new JButton();
        chooseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performChooseAction(e);
            }
        });

        chooseBtn.setText(UIConstants.ELLIPSIS);

        add(txtFldCode);
        add(lblName);
        add(chooseBtn);
    }

    private void performChooseAction(ActionEvent e) {
        EntityDialog<T> entityDialog = new EntityDialog<T>(null, Dialog.ModalityType.APPLICATION_MODAL, entityList);
        entityDialog.setLocationRelativeTo(SwingUtilities.getRootPane(EntityChooser.this)); // Display the dialog in the
                                                                                            // center.
        entityDialog.setVisible(true);
        if (entityDialog.isPressedOK() == 1) {
            entity = entityDialog.getSelectedEntity();
            setComponentValue();
        }
    }

    private void setComponentValue() {
        if (entity != null) {
            beanWrapper = new BeanWrapperImpl(entity);
            Long id = (Long) beanWrapper.getPropertyValue("id");
            String name = entity.toString();
            txtFldCode.setText(id.toString());
            lblName.setText("<html>" + name + "</html>");
            this.repaint();
        }
    }

    public T getSelectedEntity() {
        return entity;
    }

}
