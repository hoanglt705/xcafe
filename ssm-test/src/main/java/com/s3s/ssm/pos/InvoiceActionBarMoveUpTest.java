package com.s3s.ssm.pos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

public class InvoiceActionBarMoveUpTest extends AbstractPosTest {
  @Override
  public void setUp() {
    super.setUp();
    moveToOrderPanel();
  }

  @Test
  public void testMoveUp() {
    assertFalse(getButtonOpr("btnMoveUp").isEnabled());
    getButtonOpr("Tiger").clickMouse();
    getButtonOpr("Heineken_lon").clickMouse();
    getButtonOpr("cafeDa").clickMouse();
    assertTrue(getButtonOpr("btnMoveUp").isEnabled());
    assertEquals(2, getInvoiceDetailTableOpr().getSelectedRow());
    JButtonOperator btnMoveUpOpr = new JButtonOperator(mainFrameOperator, new NameComponentChooser(
            "btnMoveUp"));
    btnMoveUpOpr.clickMouse();
    assertEquals(1, getInvoiceDetailTableOpr().getSelectedRow());

    btnMoveUpOpr.clickMouse();
    assertEquals(0, getInvoiceDetailTableOpr().getSelectedRow());

    btnMoveUpOpr.clickMouse();
    assertEquals(2, getInvoiceDetailTableOpr().getSelectedRow());

    btnMoveUpOpr.clickMouse();
    assertEquals(1, getInvoiceDetailTableOpr().getSelectedRow());
  }
}
