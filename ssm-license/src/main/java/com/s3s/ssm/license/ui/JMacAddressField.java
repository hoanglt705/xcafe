package com.s3s.ssm.license.ui;

import java.awt.BorderLayout;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JMacAddressField extends JPanel {
  private static final long serialVersionUID = 1L;
  private JTextField tfdMacAddress;

  public JMacAddressField() {
    super();
    setLayout(new BorderLayout());
    tfdMacAddress = new JTextField();
    add(tfdMacAddress, BorderLayout.CENTER);
    JButton btnGetMacAddress = new JButton("Get MacAddress");
    btnGetMacAddress.addActionListener(e -> {
      try {
        tfdMacAddress.setText(getMacAddress());
      } catch (UnknownHostException | SocketException ex) {
        tfdMacAddress.setText("Can not get mac address");
      }
    });
    add(btnGetMacAddress, BorderLayout.EAST);
  }

  private String getMacAddress() throws UnknownHostException, SocketException {
    InetAddress address = InetAddress.getLocalHost();

    NetworkInterface ni = NetworkInterface.getByInetAddress(address);
    byte[] mac = ni.getHardwareAddress();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < mac.length; i++) {
      sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
    }
    return sb.toString();
  }

  public String getMacAddressText() {
    return tfdMacAddress.getText();
  }
}
