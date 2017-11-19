package com.s3s.ssm.pos;

import java.awt.Dimension;
import java.awt.Window;
import java.util.Locale;

import javax.swing.UIManager;

import org.jdesktop.application.SingleFrameApplication;

import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.controlpanel.LookAndFeelUtil;
import com.s3s.ssm.view.controlpanel.LookAndFeelUtil.LookAndFeel;

public class PosAppTest extends SingleFrameApplication {
  public static final String POS_APP = "PosApp";
  private static final String[] MESSSAGE_FILES = new String[] {"i18n/pos_label", "i18n/control_panel_label",
      "i18n/table_view_label"};

  @Override
  protected void startup() {
    PosViewTest timeLineView = new PosViewTest(this);
    show(timeLineView);
    getMainFrame().setTitle(POS_APP);
  }

  @Override
  protected void configureWindow(Window root) {
    super.configureWindow(root);
    root.setPreferredSize(new Dimension(800, 600));
  }

  @Override
  protected void initialize(String[] args) {
    Locale.setDefault(new Locale("vi"));
    setLooknFeel();
    ControlConfigUtils.setLabelMessageBundle(Locale.getDefault(), MESSSAGE_FILES);
  }

  private void setLooknFeel() {
    LookAndFeelUtil.installLF(LookAndFeel.SYNTHETICA_SKY_METALLIC);
    LookAndFeelUtil.setLF(LookAndFeel.SYNTHETICA_SKY_METALLIC);
    UIManager.put("Synthetica.tabbedPane.keepOpacity", true);
  }

  public static void main(String[] args) {
    launch(PosAppTest.class, args);
  }
}
