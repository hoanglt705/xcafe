package com.s3s.ssm.pos;

import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.BeforeClass;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.Timeouts;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JLabelOperator;
import org.netbeans.jemmy.operators.JSpinnerOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

import com.s3s.ssm.view.controlpanel.NavigationPanel;
import com.s3s.ssm.view.controlpanel.TableView;

public class AbstractPosTest {

  protected JFrameOperator mainFrameOperator;

  @BeforeClass
  public static void setUp1() throws Exception {
    startServer();
    Thread.sleep(20000);

    ClassReference classReference = new ClassReference(PosAppTest.class.getName());
    classReference.startApplication();
    JemmyProperties.getCurrentTimeouts().loadDebugTimeouts();
    Timeouts timeouts = new Timeouts();
    JemmyProperties.setCurrentTimeouts(timeouts);
  }

  public static String getParentDirPath(String fileOrDirPath) {
    boolean endsWithSlash = fileOrDirPath.endsWith(File.separator);
    return fileOrDirPath.substring(0, fileOrDirPath.lastIndexOf(File.separatorChar,
            endsWithSlash ? fileOrDirPath.length() - 2 : fileOrDirPath.length() - 1));
  }

  @Before
  public void setUp() {
    mainFrameOperator = new JFrameOperator(PosAppTest.POS_APP);
  }

  protected void moveToOrderPanel() {
    JButtonOperator btnShoppingCartOpr = new JButtonOperator(new ContainerOperator(getTableView()),
            new NameComponentChooser("btnShoppingCart"));
    btnShoppingCartOpr.clickMouse();
  }

  protected JTableOperator getInvoiceDetailTableOpr() {
    return new JTableOperator(mainFrameOperator, new NameComponentChooser("fInvoiceDetailTable"));
  }

  protected JButtonOperator getSubQuantityOpr() {
    return new JButtonOperator(mainFrameOperator, new NameComponentChooser(
            "btnSubQuantity"));
  }

  protected JButtonOperator getAddQuantityOpr() {
    return new JButtonOperator(mainFrameOperator, new NameComponentChooser(
            "btnAddQuantity"));
  }

  protected JButtonOperator getDeleteQuantityOpr() {
    return new JButtonOperator(mainFrameOperator, new NameComponentChooser(
            "btnDeleteDetail"));
  }

  protected JButtonOperator getButtonOpr(String name) {
    return new JButtonOperator(mainFrameOperator, new NameComponentChooser(name));
  }

  protected JTextFieldOperator getTextFieldOpr(String name) {
    return new JTextFieldOperator(mainFrameOperator, new NameComponentChooser(name));
  }

  protected JLabelOperator getLabelOpr(String name) {
    return new JLabelOperator(mainFrameOperator, new NameComponentChooser(name));
  }

  protected JComboBoxOperator getComboBoxOpr(String name) {
    return new JComboBoxOperator(mainFrameOperator, new NameComponentChooser(name));
  }

  protected JSpinnerOperator getJSpinnerOpr(String name) {
    return new JSpinnerOperator(mainFrameOperator, new NameComponentChooser(name));
  }

  protected boolean validateTotalAmount(String totalAmount) {
    JTextFieldOperator lblTotalAmountOpr = new JTextFieldOperator(mainFrameOperator,
            new NameComponentChooser(
                    "lblTotalAmount"));
    return totalAmount.equals(lblTotalAmountOpr.getText());
  }

  protected void moveToPosScreen() {
    getButtonOpr("btnBack").clickMouse();
  }

  protected static void startServer() throws IOException {
    File currentDirFile = new File("");
    String parentDirPath = getParentDirPath(currentDirFile.getAbsolutePath());
    String runServerScriptPath = parentDirPath + File.separator
            + "ssm-spring-boot-service\\install\\run_server.bat";
    Runtime.getRuntime().exec("cmd /c start " + runServerScriptPath);
  }

  protected TableView getTableView() {
    JTabbedPaneOperator areaTabbedPaneOpr = new JTabbedPaneOperator(mainFrameOperator);
    JPanel panel = (JPanel) areaTabbedPaneOpr.getComponentAt(0);
    NavigationPanel navPanel = (NavigationPanel) panel.getComponent(0);
    JPanel contentPanel = (JPanel) navPanel.getComponent(0);
    return (TableView) contentPanel.getComponent(0);
  }
}
