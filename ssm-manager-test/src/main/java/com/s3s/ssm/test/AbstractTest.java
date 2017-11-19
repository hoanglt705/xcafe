package com.s3s.ssm.test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.Timeouts;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JLabelOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

import com.s3s.ssm.view.ManagerApplication;

public class AbstractTest {

  @BeforeClass
  public static void setUp1() throws Exception {
    ClassReference classReference = new ClassReference(ManagerApplication.class.getName());
    classReference.startApplication();
    JemmyProperties.getCurrentTimeouts().loadDebugTimeouts();
    Timeouts timeouts = new Timeouts();
    JemmyProperties.setCurrentTimeouts(timeouts);
  }

  protected JFrameOperator mainFrameOperator;

  protected JButtonOperator getButtonOpr(String name) {
    return new JButtonOperator(mainFrameOperator, new NameComponentChooser(name));
  }

  protected JTextFieldOperator getTextFieldOpr(String name) {
    return new JTextFieldOperator(mainFrameOperator, new NameComponentChooser(name));
  }

  protected ContainerOperator getPanelOpr(String name) {
    return new ContainerOperator(mainFrameOperator, new NameComponentChooser(name));
  }

  protected JLabelOperator getLabelOpr(String name) {
    return new JLabelOperator(mainFrameOperator, new NameComponentChooser(name));
  }

  protected JTabbedPaneOperator getAListViewOpr() {
    return new JTabbedPaneOperator(mainFrameOperator, new NameComponentChooser("tabListView"));
  }

  protected JTableOperator getTableListEntityOpr() {
    return new JTableOperator(mainFrameOperator, new NameComponentChooser("tblListEntities"));
  }

  @Before
  public void setUp() {
    mainFrameOperator = new JFrameOperator(ManagerApplication.APP_TITLE);
  }

  protected void deleteFirstEntity() {
    getTableListEntityOpr().selectCell(0, 0);
    getButtonOpr("btnEntityDelete").pushNoBlock();
    JDialogOperator confirmDialog = new JDialogOperator(mainFrameOperator);
    JButtonOperator btnYesOpr = new JButtonOperator(confirmDialog, "Yes");
    btnYesOpr.clickMouse();
  }

  protected void delay(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

}
