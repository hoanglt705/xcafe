package com.s3s.ssm.widget;

import org.jdesktop.swingx.JXHeader;
import org.jdesktop.swingx.plaf.HeaderUI;

public class JVHeader extends JXHeader {
  private static final long serialVersionUID = 1L;
  public final static String uiClassID = "VHeaderUI";

  public JVHeader(String title, String description) {
    super(title, description);
  }

  public JVHeader() {
    super();
  }

  @Override
  public HeaderUI getUI() {
    return super.getUI();
  }

  @Override
  public void setUI(HeaderUI ui) {
    super.setUI(ui);
  }

  @Override
  public String getUIClassID() {
    return uiClassID;
  }

  @Override
  public void updateUI() {
    setUI(new VHeaderUI());
  }
}
