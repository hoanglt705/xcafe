package com.s3s.ssm.app;

import java.awt.Toolkit;
import java.util.EventObject;
import java.util.Locale;

import javax.swing.UIManager;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import com.s3s.ssm.app.LookAndFeelUtil.LookAndFeel;
import com.s3s.ssm.util.UIConstants;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;

@SuppressWarnings("unused")
public class CrmApplication extends SingleFrameApplication {
  private static final String APP_TITLE = "Woa Bakery";

  private static final String[] MESSSAGE_FILES = new String[] {"i18n/messages", "i18n/label",
      "i18n/gui_label", "i18n/error", "i18n/config_messages", "i18n/config_label", "i18n/menu_label_vi",
      "i18n/gui_label_vi", "i18n/license_label_vi"};

  private MainView mainView;

  @Override
  protected void initialize(String[] args) {
    Locale.setDefault(new Locale("vi"));
    setLooknFeel();
    ControlConfigUtils.setLabelMessageBundle(Locale.getDefault(), MESSSAGE_FILES);
  }

  @Override
  protected void startup() {
    mainView = new MainView(this);
    show(mainView);
    addExistListener();
    getMainFrame().setTitle(APP_TITLE);
    getMainFrame().setIconImage(
            Toolkit.getDefaultToolkit().getImage(MainView.class.getResource("/images/logo.png")));
    getMainFrame().toFront();
  }

  @Override
  public void shutdown() {
    saveData();
    super.shutdown();
  }

  private void saveData() {
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

  public static CrmApplication getApplication() {
    return Application.getInstance(CrmApplication.class);
  }

  public static void main(String[] args) {
    launch(CrmApplication.class, args);
  }
}
