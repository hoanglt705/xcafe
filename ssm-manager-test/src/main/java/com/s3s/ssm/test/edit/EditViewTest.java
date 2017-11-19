package com.s3s.ssm.test.edit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;

import com.s3s.ssm.test.AbstractTest;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

public class EditViewTest extends AbstractTest {
  @Override
  public void setUp() {
    super.setUp();
    getButtonOpr("btnArea").clickMouse();
  }

  @Test
  public void testAddNew() {
    getButtonOpr("btnNew").clickMouse();

    assertEquals(2, getAListViewOpr().getTabCount());
    assertEquals(1, getAListViewOpr().getSelectedIndex());

    assertFalse(getButtonOpr("newEntityOnEditView").isEnabled());
    assertFalse(getButtonOpr("btnSave").isEnabled());

    String areaCode = "AreaCode" + Math.random();
    String areaName = "AreaName" + Math.random();

    getTextFieldOpr("code").clearText();
    getTextFieldOpr("code").typeText(areaCode);
    assertFalse(getButtonOpr("newEntityOnEditView").isEnabled());
    assertTrue(getButtonOpr("btnSave").isEnabled());
    getTextFieldOpr("name").clearText();
    getTextFieldOpr("name").typeText(areaName);

    getButtonOpr("btnSave").clickMouse();
    assertFalse(getButtonOpr("btnSave").isEnabled());
    assertTrue(getButtonOpr("newEntityOnEditView").isEnabled());

    assertTrue(getPanelOpr("notifyPanel").isEnabled());
    assertEquals(ControlConfigUtils.getString("edit.message.saveSuccess"), getLabelOpr("NotifyPanelLabel")
            .getText());
    getButtonOpr("btnCloseNotifyPanel").clickMouse();
    getAListViewOpr().selectPage(0);

    getButtonOpr("btnRefresh").clickMouse();

    assertTrue(findRow(areaCode) != -1);

    // test delete
    int rowCount = getTableListEntityOpr().getRowCount();
    getTableListEntityOpr().selectCell(findRow(areaCode), findRow(areaCode));
    getButtonOpr("btnEntityDelete").pushNoBlock();
    JDialogOperator confirmDialog = new JDialogOperator(mainFrameOperator);
    JButtonOperator btnYesOpr = new JButtonOperator(confirmDialog, "Yes");
    btnYesOpr.clickMouse();

    assertEquals(rowCount - 1, getTableListEntityOpr().getRowCount());
    assertFalse(getButtonOpr("btnEntityEdit").isEnabled());
    assertFalse(getButtonOpr("btnEntityDelete").isEnabled());
  }

  private int findRow(String areaCode) {
    for (int i = 0; i < getTableListEntityOpr().getRowCount(); i++) {
      if (getTableListEntityOpr().getValueAt(i, 0).equals(areaCode)) {
        return i;
      }
    }
    return -1;
  }

  @Test
  public void testEdit() {
    getTableListEntityOpr().selectCell(0, 0);
    getButtonOpr("btnEntityEdit").clickMouse();
    delay(1000);
    String areaCode = "AreaCode" + Math.random();
    String areaName = "AreaName" + Math.random();
    getTextFieldOpr("code").clearText();
    getTextFieldOpr("name").clearText();
    getTextFieldOpr("code").typeText(areaCode);
    getTextFieldOpr("name").typeText(areaName);
    getButtonOpr("btnSave").clickMouse();
    assertEquals(ControlConfigUtils.getString("edit.message.saveSuccess"), getLabelOpr("NotifyPanelLabel")
            .getText());
    getButtonOpr("btnCloseNotifyPanel").clickMouse();
    getAListViewOpr().selectPage(0);
    assertEquals(areaCode, getTableListEntityOpr().getValueAt(0, 0));
    assertEquals(areaName, getTableListEntityOpr().getValueAt(0, 1));
  }
}
