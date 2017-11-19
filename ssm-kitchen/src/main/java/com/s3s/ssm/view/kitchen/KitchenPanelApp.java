package com.s3s.ssm.view.kitchen;

import java.awt.Dimension;
import java.awt.Window;
import java.util.Locale;

import javax.swing.UIManager;

import org.jdesktop.application.SingleFrameApplication;

import com.s3s.ssm.view.kitchen.LookAndFeelUtil.LookAndFeel;

public class KitchenPanelApp extends SingleFrameApplication {

  @Override
  protected void startup() {
    KitchenPanelView timeLineView = new KitchenPanelView(this);
    show(timeLineView);
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
  }

  private void setLooknFeel() {
    LookAndFeelUtil.installLF(LookAndFeel.SYNTHETICA_SKY_METALLIC);
    LookAndFeelUtil.setLF(LookAndFeel.SYNTHETICA_SKY_METALLIC);
    UIManager.put("Synthetica.tabbedPane.keepOpacity", true);
  }

  public static void main(String[] args) {
    launch(KitchenPanelApp.class, args);
  }
}
