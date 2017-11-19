package com.s3s.ssm.pos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class InvoiceActionBarMoveDownTest extends AbstractPosTest {

  @Before
  @Override
  public void setUp() {
    super.setUp();
    moveToOrderPanel();
  }

  @Test
  public void testMoveDown() {
    assertFalse(getButtonOpr("btnMoveDown").isEnabled());
    getButtonOpr("Tiger").clickMouse();
    getButtonOpr("Heineken_lon").clickMouse();
    getButtonOpr("cafeDa").clickMouse();
    assertTrue(getButtonOpr("btnMoveDown").isEnabled());
    getButtonOpr("btnMoveDown").clickMouse();
    assertEquals(0, getInvoiceDetailTableOpr().getSelectedRow());

    getButtonOpr("btnMoveDown").clickMouse();
    assertEquals(1, getInvoiceDetailTableOpr().getSelectedRow());

    getButtonOpr("btnMoveDown").clickMouse();
    assertEquals(2, getInvoiceDetailTableOpr().getSelectedRow());

    getButtonOpr("btnMoveDown").clickMouse();
    assertEquals(0, getInvoiceDetailTableOpr().getSelectedRow());
  }

}
