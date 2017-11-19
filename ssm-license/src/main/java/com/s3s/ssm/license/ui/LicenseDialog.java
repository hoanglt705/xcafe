package com.s3s.ssm.license.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.widget.HeaderDialog;

public class LicenseDialog extends HeaderDialog {
  private static final long serialVersionUID = 1L;
  private LicensePanel contentPane;

  public LicenseDialog() {
    super();
    setHeaderIcon(IziImageUtils.getMediumIcon("/images/registry.png"));
    String title = ControlConfigUtils.getString("label.menu.register");
    title = title.replace("...", "");
    setTitle(title);
    setHeaderTitle(title);
    contentPane = new LicensePanel();
    contentPane.getActionMap().put(HeaderDialog.EXECUTE_ACTION_COMMAND, new AbstractAction() {
      private static final long serialVersionUID = 1L;

      @SuppressWarnings("unused")
      @Override
      public void actionPerformed(ActionEvent e) {
        boolean matchLicense = contentPane.matchLicense();
        if (matchLicense) {
          JOptionPane.showMessageDialog(LicenseDialog.this, "The License is match");
        } else {
          JOptionPane.showMessageDialog(LicenseDialog.this, "The License is not match");
        }
        contentPane.storeLicenseFile();
      }
    });
    setSize(410, 280);
    setContentPane(contentPane);
  }
}
