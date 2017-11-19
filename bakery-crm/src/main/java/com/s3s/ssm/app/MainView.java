package com.s3s.ssm.app;

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

import com.s3s.crm.view.ManagePanel;
import com.s3s.crm.view.dashboard.Dashboard;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

import de.javasoft.swing.AboutDialog;

@SuppressWarnings("unused")
public class MainView extends FrameView {
  private static final String MANAGE_PANE = "MANAGE_PANE";
  private static final String DASHBOARD_PANE = "DASHBOARD_PANE";
  private Logger fLogger = Logger.getLogger(MainView.class.getName());
  private JPanel fCardPane;
  private CrmApplication mainApplication;
  private MenuHandler menuHandler;
  private ButtonGroup buttonGroup = new ButtonGroup();

  public MainView(CrmApplication application) {
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
    fCardPane.add(new ManagePanel(), MANAGE_PANE);
    fCardPane.add(new Dashboard(), DASHBOARD_PANE);
    setComponent(container);
  }

  private void initToolbar() {
    JToolBar toolbar = new JToolBar();
    toolbar.add(createSaleButton());
    toolbar.add(createDashboardButton());
    toolbar.add(createManagementButton());
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
      }
    });
    buttonGroup.add(manageBtn);
    return manageBtn;
  }

  private JToggleButton createSaleButton() {
    JToggleButton controlBtn = new JToggleButton(
            IziImageUtils.getSmallIcon(IziImageConstants.CONTROLPANEL_ICON));
    controlBtn.setSelected(true);
    controlBtn.setName("controlBtn");
    controlBtn.setText(ControlConfigUtils.getString("label.navigation.control_panel"));
    controlBtn.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
      }
    });
    buttonGroup.add(controlBtn);
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
      }
    });
    buttonGroup.add(dashboardBtn);
    return dashboardBtn;
  }

}
