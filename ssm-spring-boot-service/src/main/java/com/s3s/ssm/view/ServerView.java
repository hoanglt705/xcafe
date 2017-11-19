package com.s3s.ssm.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;
import org.jdesktop.swingx.JXStatusBar;
import org.jdesktop.swingx.plaf.basic.BasicStatusBarUI;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.s3s.ssm.config.ApplicationConfig;
import com.s3s.ssm.data.SSMDataLoader;

public class ServerView extends FrameView {

  private JProgressBar progressBar;
  private JLabel lblStatus;

  public ServerView(Application application) {
    super(application);
    initComponent();
    initStatusBar();
    getFrame().setPreferredSize(new Dimension(300, 200));
    PrimeNumbersTask task = new PrimeNumbersTask();
    task.execute();
  }

  private void initComponent() {
    JTabbedPane tabbedPane = new JTabbedPane();
    JPanel serverPanel = new JPanel();
    tabbedPane.add("Server information", serverPanel);
    JPanel content = new JPanel(new BorderLayout());
    content.add(tabbedPane);
    setComponent(content);
    initStatusBar();
  }

  private void initStatusBar() {
    JXStatusBar statusBar = new JXStatusBar();
    statusBar.putClientProperty(BasicStatusBarUI.AUTO_ADD_SEPARATOR, Boolean.FALSE);
    lblStatus = new JLabel();
    statusBar.add(lblStatus);
    statusBar.add(new JLabel(""), JXStatusBar.Constraint.ResizeBehavior.FILL);
    JXStatusBar.Constraint ct = new JXStatusBar.Constraint(new Insets(0, 5, 0, 5));
    statusBar.add(new JSeparator(SwingConstants.VERTICAL), ct);
    statusBar.add(new JLabel("INS"), ct);
    statusBar.add(new JSeparator(SwingConstants.VERTICAL), ct);
    getRootPane().putClientProperty("Synthetica.statusBar", statusBar);
    statusBar.add(new JLabel("Load"), ct);
    progressBar = new JProgressBar();
    progressBar.setIndeterminate(true);
    progressBar.setName("StatusProgressBar");
    statusBar.add(progressBar);
    getRootPane().putClientProperty("Synthetica.statusBar", statusBar);
    setStatusBar(statusBar);
  }

  private void startServer() {
    SpringApplicationBuilder builder = new SpringApplicationBuilder(ApplicationConfig.class);
    builder.headless(false);
    builder.run(new String[] {});
    SSMDataLoader.initDatabase();
  }

  class PrimeNumbersTask extends SwingWorker<Integer, Integer> {

    @Override
    protected Integer doInBackground() throws Exception {
      lblStatus.setText("Loading");
      startServer();
      return 1;
    }

    @Override
    protected void done() {
      lblStatus.setText("Ready");
      progressBar.setIndeterminate(false);
    }
  }
}
