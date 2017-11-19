package com.s3s.ssm.view;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.JXLoginPane.JXLoginDialog;
import org.jdesktop.swingx.auth.LoginService;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.context.ContextSession;
import com.s3s.ssm.dto.SecurityRoleDto;
import com.s3s.ssm.dto.SecurityUserDto;
import com.s3s.ssm.util.UIConstants;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.LookAndFeelUtil.LookAndFeel;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;

@SuppressWarnings("unused")
public class MainApplication extends SingleFrameApplication {
  private static final String APP_TITLE = "Izi Coffee Shop";

  private static final String[] MESSSAGE_FILES = new String[] {
      "i18n/messages", "i18n/label", "i18n/gui_label", "i18n/error",
      "i18n/config_messages", "i18n/config_label", "i18n/timeline_label", "i18n/widget_label",
      "i18n/config_error", "i18n/catalog_label", "i18n/employee_label",
      "i18n/store_label", "i18n/finance_label", "i18n/contact_label",
      "i18n/sale_label", "i18n/pos_label", "i18n.validation/ValidationMessages", "i18n/control_panel_label",
      "i18n/dashboard_panel_label", "i18n/table_view_label", "i18n/report_label", "i18n/menu_label_vi"};

  private MainView mainView;
  public final static Image APP_IMAGE = Toolkit.getDefaultToolkit().getImage(
          MainView.class.getResource("/images/CoffeeAppIcon.png"));

  @Override
  protected void initialize(String[] args) {
    Locale.setDefault(new Locale("vi"));
    setLooknFeel();
    // ControlConfigUtils.init();
    ControlConfigUtils.setLabelMessageBundle(Locale.getDefault(), MESSSAGE_FILES);
  }

  @Override
  protected void startup() {
    // JXLoginPane.Status status = login();
    // if (JXLoginPane.Status.SUCCEEDED.equals(status)) {
    mainView = new MainView(this);
    show(mainView);
    addExistListener();
    getMainFrame().setTitle(APP_TITLE);
    getMainFrame().setIconImage(APP_IMAGE);
    getMainFrame().toFront();
    // } else {
    // shutdown();
    // }
  }

  @Override
  public void shutdown() {
    saveData();
    super.shutdown();
  }

  private void saveData() {
    try {
      mainView.saveDateBeforeShutdown();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private JXLoginPane.Status login() {
    JXLoginPane panel = new JXLoginPane(new VLoginService());
    JXLoginDialog loginDialog = new JXLoginDialog(getMainFrame(), panel);
    loginDialog.setVisible(true);
    return loginDialog.getStatus();
  }

  private void addExistListener() {
    ExitListener exitListener = new ExitListener() {
      @Override
      public boolean canExit(EventObject e) {
        return true;
      }

      @Override
      public void willExit(EventObject e) {
      }
    };
    addExitListener(exitListener);
  }

  private void setLooknFeel() {
    LookAndFeelUtil.installLF(LookAndFeel.SYNTHETICA_SKY_METALLIC);
    LookAndFeelUtil.setLF(LookAndFeel.SYNTHETICA_SKY_METALLIC);
    UIManager.put("TabbedPane.background", UIManager.get("Panel.background"));
    UIManager.put("Synthetica.tabbedPane.keepOpacity", true);
    UIManager.put("Synthetica.dialog.icon.enabled", true);
    SyntheticaLookAndFeel.setFont(UIConstants.DEFAULT_FONT);

  }

  public static void main(String[] args) {
    LookAndFeelUtil.installLF(LookAndFeel.SYNTHETICA_SKY_METALLIC);
    LookAndFeelUtil.setLF(LookAndFeel.SYNTHETICA_SKY_METALLIC);
    JSplitPane splitPaneRoot = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPaneRoot.setSize(500, 500);
    JSplitPane splitPaneLevel1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    JSplitPane splitPaneLevel2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JSplitPane splitPaneLevel3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    splitPaneLevel3.add(new JLabel("AA"));
    splitPaneLevel3.add(new JLabel("AA"));
    splitPaneLevel3.setDividerLocation(1.0);

    splitPaneLevel2.add(new JLabel("LeftLevel2"));
    splitPaneLevel2.add(splitPaneLevel3);
    splitPaneLevel2.setDividerLocation(0.0);

    splitPaneLevel1.add(splitPaneLevel2);
    splitPaneLevel1.add(new JLabel("PaneBottomLevel1"));
    splitPaneLevel1.setDividerLocation(1.0);

    splitPaneRoot.add(new JLabel("PaneLeftRoot"));
    splitPaneRoot.add(splitPaneLevel1);
    splitPaneRoot.setDividerLocation(0.0);
    LookAndFeelUtil.setLF(LookAndFeel.SYNTHETICA_SIMPLE_2D);
    JFrame frame = new JFrame();
    frame.setTitle("hello");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(new JButton("hello"));
    frame.setPreferredSize(new java.awt.Dimension(500, 500));
    frame.setVisible(true);
  }

  public static MainApplication getApplication() {
    return Application.getInstance(MainApplication.class);
  }

  // public static void main(String[] args) {
  // launch(MainApplication.class, args);
  // }

  private class VLoginService extends LoginService {
    @Override
    public boolean authenticate(String username, char[] password, String server) {
      boolean exits = ManagerContextProvider.getInstance().getSecurityUserService()
              .exits(username, new String(password));
      ContextSession contextSession = ContextSession.getInstance();
      contextSession.setUsername(username);
      SecurityUserDto user = ManagerContextProvider.getInstance().getSecurityUserService().findUser(username);
      SecurityRoleDto role = user.getRole();
      Map<String, Boolean> rights = new HashMap<String, Boolean>();
      rights.put("SettingAuth", role.isSettingAuth());
      contextSession.setRights(rights);
      return exits;
    }

    @Override
    public void cancelAuthentication() {
      super.cancelAuthentication();
      System.exit(0);
    }
  }
}
