package com.s3s.ssm.view.edit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;

public class NotifyPanel extends JPanel {
  private static final long serialVersionUID = -7566700380195941863L;

  private JLabel label;

  private JButton btnClose;

  public NotifyPanel() {
    setLayout(new BorderLayout());
    label = new JLabel();
    label.setName("NotifyPanelLabel");
    label.setText("  ");
    add(label, BorderLayout.WEST);

    btnClose = new JButton();
    btnClose.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.DELETE_ICON));
    btnClose.setName("btnCloseNotifyPanel");
    add(btnClose, BorderLayout.EAST);
    btnClose.setContentAreaFilled(false);
    btnClose.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        setVisible(false);
      }
    });
    btnClose.setVisible(false);
  }

  public void clearMessage() {
    label.setText("");
  }

  public void setProblem(Problem problem) {
    switch (problem.getSeverity()) {
      case WARNING:
        label.setIcon(IziImageUtils.getIcon(IziImageConstants.WARNING_ICON));
        label.setForeground(Color.YELLOW);
        setBorder(new LineBorder(Color.YELLOW, 1, true));
        break;
      case FATAL:
        label.setIcon(IziImageUtils.getIcon(IziImageConstants.ERROR_ICON));
        label.setForeground(Color.RED);
        setBorder(new LineBorder(Color.RED, 1, true));
        break;
      case INFO:
        label.setIcon(IziImageUtils.getIcon(IziImageConstants.INFO_ICON));
        label.setForeground(Color.BLUE);
        setBorder(new LineBorder(Color.BLUE, 1, true));
        break;
    }
    label.setText(problem.getMessage());
    setVisible(true);
  }

  @Override
  public void setVisible(boolean aFlag) {
    super.setVisible(aFlag);
    btnClose.setVisible(aFlag);
  }

}
