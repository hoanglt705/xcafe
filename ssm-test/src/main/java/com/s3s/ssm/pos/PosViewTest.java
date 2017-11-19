package com.s3s.ssm.pos;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;

import com.s3s.ssm.config.PosContextProvider;
import com.s3s.ssm.view.controlpanel.ControlPanel;

public class PosViewTest extends FrameView {

  public PosViewTest(Application application) {
    super(application);
    JPanel content = new JPanel(new BorderLayout());
    JPanel actionBar = new JPanel();
    JButton btnReload = new JButton("Reload data");
    btnReload.setName("btnReload");
    btnReload.addActionListener(e -> {
      PosContextProvider.getInstance().getDataTestService().reloadData();
    });

    content.add(actionBar, BorderLayout.NORTH);
    content.add(new ControlPanel(getContext()), BorderLayout.NORTH);
    setComponent(content);
  }
}
