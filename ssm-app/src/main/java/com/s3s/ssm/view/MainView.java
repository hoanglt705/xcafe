/*
 * MainProgram
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

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.jdesktop.application.FrameView;
import org.jdesktop.swingx.JXStatusBar;
import org.jdesktop.swingx.plaf.basic.BasicStatusBarUI;

import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.WindowUtilities;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.controlpanel.ControlPanel;
import com.s3s.ssm.view.controlpanel.PosConfigPanel;
import com.s3s.ssm.view.dashboard.DashboardPanel;
import com.s3s.ssm.view.timeline.TimeLinePanel;

import de.javasoft.swing.AboutDialog;

@SuppressWarnings("unused")
public class MainView extends FrameView {
  private static final String CONTROL_PANE = "CONTROL_PANE";
  private static final String DASHBOARD_PANE = "DASHBOARD_PANE";
  private static final String TIMELINE_PANE = "TIMELINE_PANE";
  private static final String MANAGE_PANE = "MANAGE_PANE";
  private static final String REPORT_PANE = "REPORT_PANE";

  private Logger fLogger = Logger.getLogger(MainView.class.getName());
  private JPanel fCardPane;
  private TimeLinePanel fTimeLinePanel;
  private ControlPanel fControlPanel;
  private MainApplication mainApplication;
  private MenuHandler menuHandler;

  public MainView(MainApplication application) {
    super(application);
    mainApplication = application;
    menuHandler = new MenuHandler(mainApplication);
    initComponents();
    initMenuBar();
    initToolbar();
    initStatusBar();
  }

  private void initMenuBar() {
    getFrame().setJMenuBar(menuHandler.generateMenuBar());
  }

  private JMenu initSystemMenu() {
    JMenu sytemMenu = new JMenu("He thong");
    JMenu databaseMenuItem = new JMenu("Co so du lieu");
    databaseMenuItem.setIcon(IziImageUtils.getSmallIcon("/images/database.png"));
    JMenuItem changePasswordItem = new JMenuItem("Doi mat khau");
    changePasswordItem.setIcon(IziImageUtils.getSmallIcon("/images/key.png"));
    JMenuItem logoutItem = new JMenuItem("Dang xuat");
    logoutItem.setIcon(IziImageUtils.getSmallIcon("/images/logout.png"));
    JMenuItem exitItem = new JMenuItem("Thoát");
    exitItem.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.EXIT_ICON));

    JMenuItem backupMenuItem = new JMenuItem("Sao luu...");
    backupMenuItem.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.BACKUP_ICON));
    databaseMenuItem.add(backupMenuItem);
    JMenuItem restoreMenuItem = new JMenuItem("Phuc hoi...");
    restoreMenuItem.setIcon(IziImageUtils.getSmallIcon("/images/restore.png"));
    databaseMenuItem.add(restoreMenuItem);

    sytemMenu.add(databaseMenuItem);
    sytemMenu.addSeparator();
    sytemMenu.add(changePasswordItem);
    sytemMenu.addSeparator();
    sytemMenu.add(logoutItem);
    sytemMenu.add(exitItem);
    return sytemMenu;
  }

  private JMenu initConfigMenu() {
    JMenu configMenu = new JMenu("Cau hinh");
    JMenuItem posMenuItem = new JMenuItem("Cau hinh POS");
    posMenuItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JDialog dialog = new JDialog();
        dialog.setSize(500, 500);
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setTitle(ControlConfigUtils.getString("label.PosConfigPanel.config"));
        dialog.getContentPane().add(new PosConfigPanel());
        WindowUtilities.centerOnParent(dialog, null);
        dialog.setVisible(true);
      }
    });
    posMenuItem.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.SETTING_ICON));
    configMenu.add(posMenuItem);
    return configMenu;
  }

  private JMenu initHelpMenu() {
    JMenu helpMenu = new JMenu("Tro giup");
    JMenuItem guideMenuItem = new JMenuItem("Huong dan");
    guideMenuItem.setIcon(IziImageUtils.getSmallIcon("/images/question.png"));
    helpMenu.add(guideMenuItem);
    helpMenu.addSeparator();
    JMenuItem registryMenuItem = new JMenuItem("Dang ki ban quyen...");
    registryMenuItem.setIcon(IziImageUtils.getSmallIcon("/images/registry.png"));
    helpMenu.add(registryMenuItem);

    helpMenu.addSeparator();

    JMenuItem reportIssueMenuItem = new JMenuItem("Bao loi...");
    helpMenu.add(reportIssueMenuItem);

    JMenuItem updateSoftwareMenuItem = new JMenuItem("Cap nhat phien ban moi");
    helpMenu.add(updateSoftwareMenuItem);

    JMenuItem inforMenuItem = new JMenuItem("Thong tin phan mem");
    inforMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        try {
          AboutDialog aboutDialog = new AboutDialog(SwingUtilities.getWindowAncestor((Component) evt
                  .getSource()), false, new Locale("vi"), false);
          aboutDialog.setTitle("Thong tin phan mem");
          aboutDialog.setAboutText(this.getClass().getResource("/about.html"));
          aboutDialog.showDialog();
        } catch (IOException e1) {
          fLogger.log(Level.SEVERE, e1.getMessage());
        }
      }
    });
    inforMenuItem.setIcon(IziImageUtils.getSmallIcon("/images/information.png"));
    helpMenu.add(inforMenuItem);
    return helpMenu;
  }

  private JMenu initProductMenu() {
    JMenu productMenu = new JMenu("Quan ly san pham");
    productMenu.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.PRODUCT_TYPE_ICON));

    JMenuItem productTypeMenuItem = new JMenuItem();
    productTypeMenuItem.setText(ControlConfigUtils.getString("JTree.Catalog.ProductType"));
    productTypeMenuItem.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.PRODUCT_TYPE_ICON));

    JMenuItem productMenuItem = new JMenuItem();
    productMenuItem.setText(ControlConfigUtils.getString("JTree.Catalog.Food"));
    productMenuItem.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.FOOD_ICON));

    JMenuItem materialMenuItem = new JMenuItem();
    materialMenuItem.setText(ControlConfigUtils.getString("JTree.Catalog.Material"));
    materialMenuItem.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.MATERIAL_ICON));

    productMenu.add(productTypeMenuItem);
    productMenu.add(productMenuItem);
    productMenu.add(materialMenuItem);
    return productMenu;
  }

  private void initStatusBar() {
    JXStatusBar statusBar = new JXStatusBar();
    statusBar.putClientProperty(BasicStatusBarUI.AUTO_ADD_SEPARATOR, Boolean.FALSE);
    statusBar.add(new JLabel("San sang"));
    statusBar.add(new JLabel(""), JXStatusBar.Constraint.ResizeBehavior.FILL);
    JXStatusBar.Constraint ct = new JXStatusBar.Constraint(new Insets(0, 5, 0, 5));
    statusBar.add(new JSeparator(SwingConstants.VERTICAL), ct);
    statusBar.add(new JLabel("INS"), ct);
    statusBar.add(new JSeparator(SwingConstants.VERTICAL), ct);
    JLabel lblUser = new JLabel("Tai khoan: Admin");
    lblUser.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.USER_ICON));
    statusBar.add(lblUser, ct);
    // add the statusbar to the rootPane - some themes require this for proper appearance
    getRootPane().putClientProperty("Synthetica.statusBar", statusBar);
    setStatusBar(statusBar);
  }

  private void initComponents() {
    JPanel container = new JPanel(new BorderLayout());
    fCardPane = new JPanel(new CardLayout());
    container.add(fCardPane, BorderLayout.CENTER);

    fControlPanel = new ControlPanel(getContext());
    fCardPane.add(fControlPanel, CONTROL_PANE);
    fCardPane.add(new DashboardPanel(), DASHBOARD_PANE);
    fTimeLinePanel = new TimeLinePanel(getContext());
    fCardPane.add(fTimeLinePanel, TIMELINE_PANE);
    fCardPane.add(new ManagePanel(), MANAGE_PANE);
    fCardPane.add(new ReportPanel(), REPORT_PANE);
    setComponent(container);
  }

  private void initToolbar() {
    JToolBar toolbar = new JToolBar();
    JToggleButton controlButton = createControlButton();
    JToggleButton dashboardButton = createDashboardButton();
    JToggleButton timeLineButton = createTimeLineButton();
    JToggleButton managementButton = createManagementButton();
    JToggleButton reportButton = createReportButton();

    ButtonGroup buttonGroup = new ButtonGroup();
    buttonGroup.add(controlButton);
    buttonGroup.add(dashboardButton);
    buttonGroup.add(timeLineButton);
    buttonGroup.add(reportButton);
    buttonGroup.add(managementButton);

    toolbar.add(controlButton);
    toolbar.addSeparator();
    toolbar.add(dashboardButton);
    toolbar.addSeparator();
    toolbar.add(timeLineButton);
    toolbar.addSeparator();
    toolbar.add(reportButton);
    toolbar.addSeparator();
    toolbar.add(managementButton);
    toolbar.addSeparator();
    setToolBar(toolbar);
  }

  private JToggleButton createTimeLineButton() {
    JToggleButton timeLineBtn = new JToggleButton(IziImageUtils.getSmallIcon(IziImageConstants.TIMELINE_ICON));
    timeLineBtn.setName("timeLineBtn");
    timeLineBtn.setText(ControlConfigUtils.getString("label.navigation.timeline"));
    timeLineBtn.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        CardLayout cl = (CardLayout) (fCardPane.getLayout());
        fTimeLinePanel.start();
        cl.show(fCardPane, TIMELINE_PANE);
      }
    });
    return timeLineBtn;
  }

  private JToggleButton createReportButton() {
    JToggleButton reportBtn = new JToggleButton(IziImageUtils.getSmallIcon(IziImageConstants.REPORT_ICON));
    reportBtn.setName("reportBtn");
    reportBtn.setText(ControlConfigUtils.getString("label.navigation.report"));
    reportBtn.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        CardLayout cl = (CardLayout) (fCardPane.getLayout());
        cl.show(fCardPane, REPORT_PANE);
        fTimeLinePanel.stop();
      }
    });
    return reportBtn;
  }

  private JToggleButton createManagementButton() {
    JToggleButton manageBtn = new JToggleButton(IziImageUtils.getSmallIcon(IziImageConstants.SETTING_ICON));
    manageBtn.setName("manageBtn");
    manageBtn.setText(ControlConfigUtils.getString("label.navigation.manage"));
    manageBtn.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        CardLayout cl = (CardLayout) (fCardPane.getLayout());
        cl.show(fCardPane, MANAGE_PANE);
        // fTimeLinePanel.stop();
      }
    });
    return manageBtn;
  }

  private JToggleButton createControlButton() {
    JToggleButton controlBtn = new JToggleButton(
            IziImageUtils.getSmallIcon(IziImageConstants.CONTROLPANEL_ICON));
    controlBtn.setSelected(true);
    controlBtn.setName("controlBtn");
    controlBtn.setText(ControlConfigUtils.getString("label.navigation.control_panel"));
    controlBtn.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        CardLayout cl = (CardLayout) (fCardPane.getLayout());
        cl.show(fCardPane, CONTROL_PANE);
        // fTimeLinePanel.stop();
      }
    });
    return controlBtn;
  }

  private JToggleButton createDashboardButton() {
    JToggleButton dashboardBtn = new JToggleButton(
            IziImageUtils.getSmallIcon(IziImageConstants.DASHBOARD_ICON));
    dashboardBtn.setName("dashboardBtn");
    dashboardBtn.setText(ControlConfigUtils.getString("label.navigation.dashboard"));
    dashboardBtn.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        CardLayout cl = (CardLayout) (fCardPane.getLayout());
        cl.show(fCardPane, DASHBOARD_PANE);
        // fTimeLinePanel.stop();
      }
    });
    return dashboardBtn;
  }

  public void saveDateBeforeShutdown() throws IOException {
    fControlPanel.saveData();
  }
}
