package com.s3s.ssm.view;

import java.util.EventObject;
import java.util.Locale;

import javax.swing.UIManager;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.LookAndFeelUtil.LookAndFeel;

public class ManagerApplication extends SingleFrameApplication {
  public static final String APP_TITLE = "Izi Coffee Shop";

  private static final String[] MESSSAGE_FILES = new String[] {
      "i18n/messages", "i18n/label", "i18n/gui_label", "i18n/error", "i18n/gui_error",
      "i18n/config_messages", "i18n/config_label",
      "i18n/config_error", "i18n/catalog_label", "i18n/employee_label",
      "i18n/store_label", "i18n/finance_label", "i18n/contact_label",
      "i18n/sale_label", "i18n.validation/ValidationMessages"};

  private ManagerView mainView;

  @Override
  protected void initialize(String[] args) {
    Locale.setDefault(new Locale("vi"));
    setLooknFeel();
    // ControlConfigUtils.init();
    ControlConfigUtils.setLabelMessageBundle(Locale.getDefault(), MESSSAGE_FILES);
  }

  @Override
  protected void startup() {
    mainView = new ManagerView(this);
    show(mainView);
    addExistListener();
    getMainFrame().setTitle(APP_TITLE);
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
    // SyntheticaLookAndFeel.setFont(UIConstants.DEFAULT_FONT);

  }

  public static ManagerApplication getApplication() {
    return Application.getInstance(ManagerApplication.class);
  }

  public static void main(String[] args) {
    launch(ManagerApplication.class, args);
  }
}
