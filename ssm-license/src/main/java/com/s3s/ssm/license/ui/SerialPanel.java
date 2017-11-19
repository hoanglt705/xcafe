package com.s3s.ssm.license.ui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SerialPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  private JTextField tfdFirstNum;
  private JTextField tfdSecondNum;
  private JTextField tfdThridNum;
  private JTextField tfdFourthNum;

  public SerialPanel() {
    super();
    setLayout(new FlowLayout(SwingConstants.CENTER));
    tfdFirstNum = new JTextField();
    tfdFirstNum.setColumns(5);
    add(tfdFirstNum);
    add(new JLabel("-"));
    tfdSecondNum = new JTextField();
    tfdSecondNum.setColumns(5);
    add(tfdSecondNum);
    add(new JLabel("-"));
    tfdThridNum = new JTextField();
    tfdThridNum.setColumns(5);
    add(tfdThridNum);
    add(new JLabel("-"));
    tfdFourthNum = new JTextField();
    tfdFourthNum.setColumns(5);
    add(tfdFourthNum);
  }

  public String getSerialNumber() {
    return new StringBuilder().append(tfdFirstNum.getText()).append("-").append(tfdSecondNum.getText())
            .append("-").append(tfdThridNum.getText()).append("-").append(tfdFourthNum.getText()).toString();
  }
}
