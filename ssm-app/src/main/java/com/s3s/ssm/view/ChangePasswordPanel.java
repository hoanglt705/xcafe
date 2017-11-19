package com.s3s.ssm.view;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXLoginPane;

import com.s3s.ssm.config.ManagerContextProvider;

public class ChangePasswordPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  private JPasswordField newPassword;
  private JTextField tfdUsername;
  private JXLoginPane.Status status;

  public ChangePasswordPanel() {
    super();
    setLayout(new BorderLayout());
    add(createLoginPanel());
    add(createActionPanel(), BorderLayout.SOUTH);
  }

  private JPanel createLoginPanel() {
    JPanel loginPanel = new JPanel();
    TableLayout tableLayout = new TableLayout(new double[][] { {50, -1}, {0, 30, 30}});
    setLayout(tableLayout);
    loginPanel.add(new JLabel("Username"));
    tfdUsername = new JTextField();
    tfdUsername.setEditable(false);
    loginPanel.add(tfdUsername);
    loginPanel.add(new JLabel("Password"));
    loginPanel.add(new JPasswordField());
    loginPanel.add(new JLabel("New password"));
    newPassword = new JPasswordField();
    loginPanel.add(newPassword);
    return loginPanel;
  }

  private JPanel createActionPanel() {
    JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton btnOK = new JButton(new OkAction());
    btnOK.setText("OK");
    JButton btnCancel = new JButton(new CancelAction());
    btnCancel.setText("Cancel");
    actionPanel.add(btnOK);
    actionPanel.add(btnCancel);
    return actionPanel;
  }

  public JXLoginPane.Status getStatus() {
    return status;
  }

  public void setStatus(JXLoginPane.Status status) {
    this.status = status;
  }

  private class OkAction extends AbstractAction {
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
      boolean changePassword = ManagerContextProvider.getInstance().getSecurityUserService()
              .changePassword(tfdUsername.getText(), null, new String(newPassword.getPassword()));
      if (changePassword) {
        setStatus(JXLoginPane.Status.SUCCEEDED);
      } else {
        setStatus(JXLoginPane.Status.FAILED);
      }
    }

  }

  private class CancelAction extends AbstractAction {

    /**  */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
      setStatus(JXLoginPane.Status.CANCELLED);
    }

  }
}
