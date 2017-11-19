package com.s3s.crm.view.security;

import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.widget.HeaderDialog;

public class ChangePasswordDialog extends HeaderDialog {
  private static final long serialVersionUID = 1L;
  private JTextField tfdUsername;
  private JTextField tfdPassword;
  private JTextField tfdNewPassword;
  private JLabel lblStatus;

  public ChangePasswordDialog() {
    super();
    setHeaderIcon(IziImageUtils.getMediumIcon("/images/registry.png"));
    String title = ControlConfigUtils.getString("label.menu.register");
    title = title.replace("...", "");
    setTitle(title);
    setHeaderTitle(title);

    TableLayout tableLayout = new TableLayout(new double[][] { {120, -1}, {10, 20, 20, 20}});
    tableLayout.setVGap(2);
    JPanel contentPanel = new JPanel(tableLayout);
    lblStatus = new JLabel();
    contentPanel.add(new JLabel("Username"), "0, 1");
    tfdUsername = new JTextField();
    contentPanel.add(tfdUsername, "1, 1");
    contentPanel.add(new JLabel("Password"), "0, 2");
    tfdPassword = new JPasswordField();
    contentPanel.add(tfdPassword, "1, 2");
    contentPanel.add(new JLabel("New Password"), "0, 3");
    tfdNewPassword = new JPasswordField();
    contentPanel.add(tfdNewPassword, "1, 3");

    contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    contentPanel.getActionMap().put(HeaderDialog.EXECUTE_ACTION_COMMAND, new AbstractAction() {
      private static final long serialVersionUID = 1L;

      @SuppressWarnings("unused")
      @Override
      public void actionPerformed(ActionEvent e) {
        String username = tfdUsername.getText();
        boolean succeed = CrmContextProvider.getInstance().getSecurityUserService()
                .changePassword(username, tfdPassword.getText(), tfdNewPassword.getText());
        if (succeed) {
          JOptionPane.showMessageDialog(ChangePasswordDialog.this, "Succeed");
        } else {
          JOptionPane.showMessageDialog(ChangePasswordDialog.this, "Failed");
        }
        ChangePasswordDialog.this.setVisible(false);
        ChangePasswordDialog.this.dispose();
      }
    });
    setSize(410, 280);
    setContentPane(contentPanel);
  }
}
