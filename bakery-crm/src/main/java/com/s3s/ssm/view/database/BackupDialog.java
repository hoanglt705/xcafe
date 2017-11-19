package com.s3s.ssm.view.database;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.widget.HeaderDialog;

import de.javasoft.swing.DetailsDialog;
import de.javasoft.swing.ExtendedFileChooser;

public class BackupDialog extends HeaderDialog {
  private static final long serialVersionUID = 1L;

  public BackupDialog() {
    super();
    final String title = ControlConfigUtils.getString("label.menu.backup").replace("...", "");
    setTitle(title);
    setHeaderTitle(title);
    setSize(445, 260);
    setHeaderIcon(IziImageUtils.getMediumIcon(IziImageConstants.BACKUP_ICON));
    JPanel contentPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel label = new JLabel("Luu tai");
    JTextField backupField = new JTextField();
    backupField.setColumns(30);
    JButton btnOpenDialog = new JButton("...");
    btnOpenDialog.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        Component c = (Component) event.getSource();
        ExtendedFileChooser fileChooser = new ExtendedFileChooser();
        fileChooser.setDialogTitle(title);
        if (fileChooser.showOpenDialog(c) == JFileChooser.APPROVE_OPTION) {
          try {
            backupField.setText(fileChooser.getSelectedFile().getCanonicalPath());
          } catch (IOException e) {
            DetailsDialog.showDialog(SwingUtilities.getWindowAncestor(c), null, null, e);
          }
        }
      }
    });
    contentPane.add(label);
    contentPane.add(backupField);
    contentPane.add(btnOpenDialog);
    setContentPane(contentPane);
  }

}
