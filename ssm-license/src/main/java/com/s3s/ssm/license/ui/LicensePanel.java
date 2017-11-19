package com.s3s.ssm.license.ui;

import info.clearthought.layout.TableLayout;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import com.s3s.ssm.license.License;
import com.s3s.ssm.license.LicenseValidator;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

public class LicensePanel extends JPanel {
  private static final String LICENSE_PATH = "./license.txt";
  private static final long serialVersionUID = 1L;
  private JTextField tfdFullName;
  private JTextField tfdOrgName;
  private JXDatePicker datePickerExpDate;
  private JMacAddressField tfdMacAddress;
  private SerialPanel serialPanel;

  public LicensePanel() {
    super();
    TableLayout tableLayout = new TableLayout(new double[][] { {120, -1}, {-1, -1, -1, -1, -1}});
    tableLayout.setVGap(2);
    setLayout(tableLayout);
    add(new JLabel(ControlConfigUtils.getString("label.License.fullname")), "0, 0");
    tfdFullName = new JTextField();
    add(tfdFullName, "1, 0");
    add(new JLabel(ControlConfigUtils.getString("label.License.organizationName")), "0, 1");
    tfdOrgName = new JTextField();
    add(tfdOrgName, "1, 1");
    add(new JLabel(ControlConfigUtils.getString("label.License.expireDate")), "0, 2");
    datePickerExpDate = new JXDatePicker();
    add(datePickerExpDate, "1, 2");
    add(new JLabel(ControlConfigUtils.getString("label.License.macAddress")), "0, 3");
    tfdMacAddress = new JMacAddressField();
    add(tfdMacAddress, "1, 3");
    add(new JLabel(ControlConfigUtils.getString("label.License.serial")), "0, 4");
    serialPanel = new SerialPanel();
    add(serialPanel, "1, 4");
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
  }

  public License getLicense() {
    License license = new License();
    license.setFullName(tfdFullName.getText());
    license.setOrganizationName(tfdOrgName.getText());
    license.setExpireDate(datePickerExpDate.getDate());
    license.setMacAddress(tfdMacAddress.getMacAddressText());
    return license;
  }

  public boolean matchLicense() {
    try {
      return LicenseValidator.validate(getLicense(), serialPanel.getSerialNumber());
    } catch (NoSuchAlgorithmException e) {
      return false;
    }
  }

  public void storeLicenseFile() {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(LICENSE_PATH));) {
      oos.writeObject(getLicense());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
