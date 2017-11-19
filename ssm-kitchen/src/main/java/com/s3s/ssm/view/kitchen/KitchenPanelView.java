package com.s3s.ssm.view.kitchen;

import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;

public class KitchenPanelView extends FrameView {

  public KitchenPanelView(Application application) {
    super(application);
    setComponent(new KitchenPanel(getContext()));
  }

}
