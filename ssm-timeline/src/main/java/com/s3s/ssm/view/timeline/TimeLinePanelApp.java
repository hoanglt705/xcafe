package com.s3s.ssm.view.timeline;

import java.util.Locale;

import javax.swing.UIManager;

import org.jdesktop.application.SingleFrameApplication;

import com.s3s.ssm.view.timeline.LookAndFeelUtil.LookAndFeel;

public class TimeLinePanelApp extends SingleFrameApplication {

  @Override
  protected void startup() {
    TimeLinePanelView timeLineView = new TimeLinePanelView(this);
    show(timeLineView);
  }

  @Override
  protected void initialize(String[] args) {
    Locale.setDefault(new Locale("vi"));
    setLooknFeel();
  }

  private void setLooknFeel() {
    LookAndFeelUtil.installLF(LookAndFeel.SYNTHETICA_SKY_METALLIC);
    LookAndFeelUtil.setLF(LookAndFeel.SYNTHETICA_SKY_METALLIC);
    UIManager.put("TabbedPane.background", UIManager.get("Panel.background"));
    UIManager.put("Synthetica.tabbedPane.keepOpacity", true);
    UIManager.put("Synthetica.dialog.icon.enabled", true);
    // SyntheticaLookAndFeel.setFont(UIConstants.DEFAULT_FONT);

  }

  public static void main(String[] args) {
    launch(TimeLinePanelApp.class, args);
  }
}
