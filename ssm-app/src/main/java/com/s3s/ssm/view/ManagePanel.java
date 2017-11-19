package com.s3s.ssm.view;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

public class ManagePanel extends JSplitPane {
  private static final long serialVersionUID = 1L;

  public ManagePanel() {
    JScrollPane treeMenuScrollPane = new JScrollPane();
    JScrollPane contentViewScrollPane = new JScrollPane();

    treeMenuScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    treeMenuScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    setContinuousLayout(false);

    setOneTouchExpandable(true);
    setRightComponent(contentViewScrollPane);

    JSplitPane leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
    leftSplitPane.setResizeWeight(1);
    leftSplitPane.setDividerSize(2);
    ManageTaskPaneBuilder context = new ManageTaskPaneBuilder(contentViewScrollPane);
    setLeftComponent(context.buildApplicationContext());
  }
}
