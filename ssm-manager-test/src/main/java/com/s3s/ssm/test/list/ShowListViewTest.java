package com.s3s.ssm.test.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.netbeans.jemmy.operators.JRadioButtonMenuItemOperator;

import com.s3s.ssm.test.AbstractTest;

public class ShowListViewTest extends AbstractTest {
  public static final Log logger = LogFactory.getLog(ShowListViewTest.class);

  @Override
  public void setUp() {
    super.setUp();
    getButtonOpr("btnArea").clickMouse();
  }

  @Test
  public void testInit() {
    assertTrue(getButtonOpr("btnShow").isEnabled());
    getButtonOpr("btnShow").clickMouse();
    delay(500);
    JRadioButtonMenuItemOperator showActiveOpr = new JRadioButtonMenuItemOperator(mainFrameOperator, 0);
    JRadioButtonMenuItemOperator showInactiveOpr = new JRadioButtonMenuItemOperator(mainFrameOperator, 1);
    JRadioButtonMenuItemOperator showAllOpr = new JRadioButtonMenuItemOperator(mainFrameOperator, 2);
    assertTrue(showActiveOpr.isSelected());
    assertFalse(showInactiveOpr.isSelected());
    assertFalse(showAllOpr.isSelected());
  }

  @Test
  public void testShowInactive() {
    getButtonOpr("btnShow").clickMouse();
    delay(500);
    JRadioButtonMenuItemOperator showInactiveOpr = new JRadioButtonMenuItemOperator(mainFrameOperator, 1);
    showInactiveOpr.push();
    delay(1000);
    assertEquals(1, getTableListEntityOpr().getRowCount());
    assertEquals("INACTIVE_AREA", getTableListEntityOpr().getValueAt(0, 0));
    assertEquals("INACTIVE_AREA", getTableListEntityOpr().getValueAt(0, 1));
  }

  @Test
  public void testShowActive() {
    getButtonOpr("btnShow").clickMouse();
    delay(500);
    JRadioButtonMenuItemOperator showActiveOpr = new JRadioButtonMenuItemOperator(mainFrameOperator, 0);
    showActiveOpr.push();
    delay(1000);
    assertTrue(getTableListEntityOpr().getRowCount() > 1);
  }

  @Test
  public void testShowAll() {
    getButtonOpr("btnShow").clickMouse();
    delay(500);
    JRadioButtonMenuItemOperator showAllOpr = new JRadioButtonMenuItemOperator(mainFrameOperator, 2);
    showAllOpr.push();
    delay(1000);
    assertTrue(getTableListEntityOpr().getRowCount() > 2);

    int lastIndex = getTableListEntityOpr().getRowCount() - 1;

    assertEquals("INACTIVE_AREA", getTableListEntityOpr().getValueAt(lastIndex, 0));
    assertEquals("INACTIVE_AREA", getTableListEntityOpr().getValueAt(lastIndex, 1));
  }
}
