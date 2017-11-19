package com.s3s.ssm.view;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

import de.javasoft.swing.JYTabbedPane;
import de.javasoft.swing.JYTabbedPane.CloseButtonStrategy;

public class ReportPanel extends JSplitPane {
  private static final long serialVersionUID = 1L;

  public ReportPanel() {
    JScrollPane treeMenuScrollPane = new JScrollPane();
    JYTabbedPane contentViewPanel = new JYTabbedPane();
    contentViewPanel.setCloseButtonStrategy(CloseButtonStrategy.ALL_TABS);

    treeMenuScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    treeMenuScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    setContinuousLayout(false);

    setOneTouchExpandable(true);
    setRightComponent(contentViewPanel);

    JSplitPane leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
    leftSplitPane.setResizeWeight(1);
    leftSplitPane.setDividerSize(2);
    ReportTaskPaneBuilder context = new ReportTaskPaneBuilder(contentViewPanel);
    setLeftComponent(context.buildApplicationContext());
  }
}
