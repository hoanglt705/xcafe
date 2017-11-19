package com.s3s.ssm.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.SecurityRoleDto;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.WindowUtilities;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.AbstractView.EditActionEnum;
import com.s3s.ssm.view.database.BackupDialog;
import com.s3s.ssm.view.database.RestoreDialog;
import com.s3s.ssm.view.detail.config.EditCompanyView;
import com.s3s.ssm.view.printer.EditPrinterView;
import com.s3s.ssm.view.printer.PrinterDto;
import com.s3s.ssm.view.printer.PrinterServiceImpl;
import com.s3s.ssm.view.registry.RegistryDialog;

import de.javasoft.swing.AboutDialog;

public class MenuHandler {
  private Logger fLogger = Logger.getLogger(MenuHandler.class.getName());
  private MainApplication mainApplication;
  private ManagerContextProvider managerContextProvider = ManagerContextProvider.getInstance();
  private SecurityRoleDto roleDto = managerContextProvider.getSecurityRoleService().findOne(1L);

  public MenuHandler(MainApplication mainApplication) {
    this.mainApplication = mainApplication;
  }

  public JMenuBar generateMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu systemMenu = initSystemMenu();
    // systemMenu.setVisible(roleDto.isSettingAuth());
    menuBar.add(systemMenu);
    menuBar.add(initConfigMenu());
    menuBar.add(initHelpMenu());
    menuBar.add(new JMenu(ControlConfigUtils.getString("label.menu.contact")));
    return menuBar;
  }

  private JMenu initSystemMenu() {
    JMenu sytemMenu = new JMenu(ControlConfigUtils.getString("label.menu.system"));
    JMenuItem changePasswordItem = new JMenuItem(ControlConfigUtils.getString("label.menu.changePass")
            + "...");
    changePasswordItem.setIcon(IziImageUtils.getSmallIcon("/images/key.png"));
    JMenuItem logoutItem = new JMenuItem(ControlConfigUtils.getString("label.menu.logout"));
    logoutItem.setIcon(IziImageUtils.getSmallIcon("/images/logout.png"));
    JMenuItem exitItem = new JMenuItem(ControlConfigUtils.getString("label.menu.exit"));
    exitItem.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.EXIT_ICON));
    exitItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Component c = (Component) e.getSource();
        JRootPane rootPane = SwingUtilities.getRootPane(c);
        int showConfirmDialog = JOptionPane.showConfirmDialog(rootPane,
                ControlConfigUtils.getString("label.dialog.exit"),
                ControlConfigUtils.getString("label.menu.exit"), JOptionPane.YES_NO_OPTION);
        if (showConfirmDialog == JOptionPane.YES_OPTION) {
          mainApplication.exit();
        }
      }
    });

    sytemMenu.add(initDatabaseMenuItem());
    sytemMenu.addSeparator();
    sytemMenu.add(changePasswordItem);
    sytemMenu.addSeparator();
    sytemMenu.add(logoutItem);
    sytemMenu.add(exitItem);
    return sytemMenu;
  }

  private JMenu initDatabaseMenuItem() {
    JMenu databaseMenuItem = new JMenu(ControlConfigUtils.getString("label.menu.database"));
    databaseMenuItem.setIcon(IziImageUtils.getSmallIcon("/images/database.png"));
    JMenuItem backupMenuItem = new JMenuItem(ControlConfigUtils.getString("label.menu.backup"));
    backupMenuItem.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.BACKUP_ICON));
    backupMenuItem.addActionListener(new ActionListener() {
      @SuppressWarnings("unused")
      @Override
      public void actionPerformed(ActionEvent e) {
        BackupDialog backupDialog = new BackupDialog();
        WindowUtilities.centerOnParent(backupDialog, null);
        backupDialog.setVisible(true);
      }
    });
    databaseMenuItem.add(backupMenuItem);
    JMenuItem restoreMenuItem = new JMenuItem(ControlConfigUtils.getString("label.menu.restore"));
    restoreMenuItem.setIcon(IziImageUtils.getSmallIcon("/images/restore.png"));
    restoreMenuItem.addActionListener(new ActionListener() {

      @SuppressWarnings("unused")
      @Override
      public void actionPerformed(ActionEvent e) {
        RestoreDialog restoreDialog = new RestoreDialog();
        WindowUtilities.centerOnParent(restoreDialog, null);
        restoreDialog.setVisible(true);
      }
    });
    databaseMenuItem.add(restoreMenuItem);
    return databaseMenuItem;
  }

  private JMenu initConfigMenu() {
    JMenu configMenu = new JMenu(ControlConfigUtils.getString("label.menu.config"));
    JMenuItem posMenuItem = new JMenuItem(ControlConfigUtils.getString("label.menu.generalInformation"));
    posMenuItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
        JDialog dialog = new JDialog();
        dialog.setSize(800, 600);
        dialog.setTitle(ControlConfigUtils.getString("label.menu.generalInformation"));
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setIconImage(MainApplication.APP_IMAGE);
        dialog.getContentPane().add(new EditCompanyView(initCompanyRequest()));
        WindowUtilities.centerOnParent(dialog, null);
        dialog.setVisible(true);
      }
    });
    posMenuItem.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.SETTING_ICON));
    JMenuItem printerMenuItem = new JMenuItem(ControlConfigUtils.getString("label.menu.printer"));
    printerMenuItem.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.PRINT_ICON));
    printerMenuItem.addActionListener(new ActionListener() {

      @SuppressWarnings("unused")
      @Override
      public void actionPerformed(ActionEvent e) {
        JDialog dialog = new JDialog();
        dialog.setSize(800, 600);
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setTitle(ControlConfigUtils.getString("label.menu.printer"));
        dialog.setIconImage(MainApplication.APP_IMAGE);
        dialog.getContentPane().add(new EditPrinterView(initPrinterRequest()));
        WindowUtilities.centerOnParent(dialog, null);
        dialog.setVisible(true);
      }
    });
    configMenu.add(posMenuItem);
    configMenu.add(printerMenuItem);
    return configMenu;
  }

  private Map<String, Object> initPrinterRequest() {
    PrinterServiceImpl printerServiceImpl = new PrinterServiceImpl();
    PrinterDto printerDto = printerServiceImpl.findOne(1L);
    Map<String, Object> request = new HashMap<String, Object>();
    if (printerDto != null) {
      request.put(AbstractView.PARAM_ENTITY_ID, 1L);
      request.put(AbstractView.PARAM_ACTION, EditActionEnum.EDIT);
    } else {
      request.put(AbstractView.PARAM_ACTION, EditActionEnum.NEW);
    }
    return request;
  }

  private Map<String, Object> initCompanyRequest() {
    Long id = ManagerContextProvider.getInstance().getCompanyService().getCompany().getId();
    Map<String, Object> request = new HashMap<String, Object>();
    request.put(AbstractView.PARAM_ENTITY_ID, id);
    request.put(AbstractView.PARAM_ACTION, EditActionEnum.EDIT);
    return request;
  }

  private JMenu initHelpMenu() {
    JMenu helpMenu = new JMenu(ControlConfigUtils.getString("label.menu.help"));
    JMenuItem guideMenuItem = new JMenuItem(ControlConfigUtils.getString("label.menu.guide"));
    guideMenuItem.setIcon(IziImageUtils.getSmallIcon("/images/question.png"));
    helpMenu.add(guideMenuItem);
    helpMenu.addSeparator();
    JMenuItem registryMenuItem = new JMenuItem(ControlConfigUtils.getString("label.menu.register"));
    registryMenuItem.setIcon(IziImageUtils.getSmallIcon("/images/registry.png"));
    registryMenuItem.addActionListener(new ActionListener() {

      @SuppressWarnings("unused")
      @Override
      public void actionPerformed(ActionEvent e) {
        RegistryDialog dialog = new RegistryDialog();
        WindowUtilities.centerOnParent(dialog, null);
        dialog.setVisible(true);
      }
    });
    helpMenu.add(registryMenuItem);
    helpMenu.addSeparator();
    JMenuItem inforMenuItem = new JMenuItem(ControlConfigUtils.getString("label.menu.about"));
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
}
