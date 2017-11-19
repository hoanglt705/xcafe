package com.s3s.ssm.view.controlpanel;

import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;

public class PosView extends FrameView {

  public PosView(Application application) {
    super(application);
    setComponent(new ControlPanel(getContext()));
  }

}
