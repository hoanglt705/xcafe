package com.s3s.ssm.view.timeline;

import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;

public class TimeLinePanelView extends FrameView {

  public TimeLinePanelView(Application application) {
    super(application);
    setComponent(new TimeLinePanel(getContext()));
  }

}
