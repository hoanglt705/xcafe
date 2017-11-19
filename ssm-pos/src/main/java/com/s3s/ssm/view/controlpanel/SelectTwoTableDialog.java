package com.s3s.ssm.view.controlpanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.util.WindowUtils;

import com.s3s.ssm.dto.FoodTableDto;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.factory.SComponentFactory;
import com.s3s.ssm.widget.JVComboBox;

public class SelectTwoTableDialog extends JDialog {
  private static final long serialVersionUID = 1L;

  private final JPanel contentPanel = new JPanel();

  private final JVComboBox cbxFrom;

  private final JVComboBox cbxTo;

  private int result;

  public SelectTwoTableDialog(List<FoodTableDto> tableList) {
    this(tableList, tableList);
  }

  public SelectTwoTableDialog(List<FoodTableDto> fromTableList, List<FoodTableDto> toTableList) {
    setLayout(new BorderLayout());
    setBounds(100, 100, 450, 157);
    contentPanel.setLayout(new FlowLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);

    JLabel lblFromTable = new JLabel(ControlConfigUtils.getString("label.CombineTableDialog.fromTable"));
    contentPanel.add(lblFromTable);

    cbxFrom = SComponentFactory.createDropdownComponent(fromTableList);
    contentPanel.add(cbxFrom);

    JLabel lblToTable = new JLabel(ControlConfigUtils.getString("label.CombineTableDialog.toTable"));
    contentPanel.add(lblToTable);

    cbxTo = SComponentFactory.createDropdownComponent(toTableList);
    contentPanel.add(cbxTo);

    initControlPanel();
    setResizable(false);
    pack();
    setLocation(WindowUtils.getPointForCentering(this));
  }

  private void initControlPanel() {
    JButton okButton = new JButton("OK");
    okButton.setActionCommand("OK");
    okButton.addActionListener(new OkAction());

    JButton cancelButton = new JButton("Cancel");
    cancelButton.setActionCommand("Cancel");
    cancelButton.addActionListener(new CancelAction());

    JPanel buttonPane = new JPanel(new GridLayout(1, 2));
    buttonPane.setOpaque(false);
    buttonPane.add(okButton);
    buttonPane.add(cancelButton);
    getRootPane().setDefaultButton(okButton);

    JXPanel controls = new JXPanel(new FlowLayout(FlowLayout.RIGHT));
    controls.setOpaque(false);
    new BoxLayout(controls, BoxLayout.X_AXIS);
    controls.add(Box.createHorizontalGlue());
    controls.add(buttonPane);
    add(controls, BorderLayout.SOUTH);
  }

  public FoodTableDto getFromTable() {
    return (FoodTableDto) cbxFrom.getSelectedItem();
  }

  public FoodTableDto getToTable() {
    return (FoodTableDto) cbxTo.getSelectedItem();
  }

  public int getResult() {
    if (cbxFrom.getSelectedIndex() != -1 && cbxTo.getSelectedIndex() != -1) {
      return result;
    }
    return JOptionPane.CANCEL_OPTION;
  }

  private class CancelAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      result = JOptionPane.CANCEL_OPTION;
      SelectTwoTableDialog.this.setVisible(false);
    }
  }

  private class OkAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      result = JOptionPane.OK_OPTION;
      SelectTwoTableDialog.this.setVisible(false);
    }
  }
}
