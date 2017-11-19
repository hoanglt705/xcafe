package com.s3s.ssm.widget;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;

public class HeaderDialog extends JDialog {
  private static final long serialVersionUID = 1L;

  public static final String EXECUTE_ACTION_COMMAND = "execute";
  public static final String CLOSE_ACTION_COMMAND = "close";

  private JPanel fContentPanel = new JPanel(new BorderLayout());
  private JVHeader header = new JVHeader();

  private JButton executeButton;
  private JButton exitButton;

  public void setContentPane(JPanel contentPane) {
    fContentPanel.add(header, BorderLayout.NORTH);
    setModal(true);
    fContentPanel.add(contentPane, BorderLayout.CENTER);
    initActions();
    Action contentCloseAction = contentPane.getActionMap().get(CLOSE_ACTION_COMMAND);
    if (contentCloseAction != null) {
      putAction(CLOSE_ACTION_COMMAND, contentCloseAction);
    }
    Action contentExecuteAction = contentPane.getActionMap().get(EXECUTE_ACTION_COMMAND);
    if (contentExecuteAction != null) {
      putAction(EXECUTE_ACTION_COMMAND, contentExecuteAction);
    }
    fContentPanel.add(createButtonPanel(), BorderLayout.SOUTH);
    super.setContentPane(fContentPanel);
  }

  public void setHeaderTitle(String title) {
    header.setTitle(title);
  }

  public void setHeaderDescription(String description) {
    header.setDescription(description);
  }

  public void setHeaderIcon(Icon icon) {
    header.setIcon(icon);
  }

  protected JPanel createButtonPanel() {
    Action executeAction = getAction(EXECUTE_ACTION_COMMAND);
    Action closeAction = getAction(CLOSE_ACTION_COMMAND);

    JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    panel.setBorder(BorderFactory.createEmptyBorder(9, 0, 0, 0));
    executeButton = new JButton();
    if (executeAction != null) {
      executeButton.setAction(executeAction);
    }
    executeButton.setText("Execute");
    executeButton.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.SAVE_ICON));
    panel.add(executeButton);
    exitButton = new JButton();
    if (closeAction != null) {
      exitButton.setAction(closeAction);
    }
    exitButton.setText("Exit");
    exitButton.setIcon(IziImageUtils.getSmallIcon(IziImageConstants.DELETE_ICON));
    panel.add(exitButton);
    return panel;
  }

  private void initActions() {
    Action defaultAction = createCloseAction();
    putAction(CLOSE_ACTION_COMMAND, defaultAction);
    putAction(EXECUTE_ACTION_COMMAND, defaultAction);
  }

  private void putAction(Object key, Action action) {
    getRootPane().getActionMap().put(key, action);
  }

  private Action createCloseAction() {
    return new AbstractAction() {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
        HeaderDialog.this.setVisible(false);
      }
    };
  }

  private Action getAction(Object key) {
    return getRootPane().getActionMap().get(key);
  }

  public JButton getExecuteButton() {
    return executeButton;
  }

  public JButton getExitButton() {
    return exitButton;
  }
}
