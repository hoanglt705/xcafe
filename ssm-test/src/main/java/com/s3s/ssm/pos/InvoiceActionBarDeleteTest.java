package com.s3s.ssm.pos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.s3s.ssm.widget.vbadge.VBadge;

public class InvoiceActionBarDeleteTest extends AbstractPosTest {

  @Override
  public void setUp() {
    super.setUp();
    moveToOrderPanel();
  }

  @Test
  public void testDeleteDetail() {
    assertFalse(getDeleteQuantityOpr().isEnabled());

    getButtonOpr("Tiger").clickMouse();
    assertTrue(getAddQuantityOpr().isEnabled());
    assertTrue(getSubQuantityOpr().isEnabled());
    assertTrue(getDeleteQuantityOpr().isEnabled());
    validateTotalAmount("17.000");

    getButtonOpr("Heineken_lon").clickMouse();
    getButtonOpr("cafeDa").clickMouse(3);
    assertEquals(2, getInvoiceDetailTableOpr().getSelectedRow());
    assertEquals(3, getInvoiceDetailTableOpr().getRowCount());
    validateTotalAmount("113.000");

    getDeleteQuantityOpr().clickMouse();
    assertEquals(2, getInvoiceDetailTableOpr().getRowCount());
    assertEquals(1, getInvoiceDetailTableOpr().getSelectedRow());
    VBadge cafeDaBadge = (VBadge) getButtonOpr("cafeDa").getSource();
    assertNull(cafeDaBadge.getClientProperty("Synthetica.background.alpha"));
    validateTotalAmount("38.000");
    assertEquals("-38.000", getTextFieldOpr("tfdTotalReturnAmount").getText());

    getDeleteQuantityOpr().clickMouse();
    VBadge heinekenBadge = (VBadge) getButtonOpr("Heineken_lon").getSource();
    assertEquals(30, heinekenBadge.getBadgeValue());
    assertEquals(1, getInvoiceDetailTableOpr().getRowCount());
    assertEquals(0, getInvoiceDetailTableOpr().getSelectedRow());
    validateTotalAmount("17.000");
    assertEquals("-17.000", getTextFieldOpr("tfdTotalReturnAmount").getText());

    getDeleteQuantityOpr().clickMouse();
    assertEquals(0, getInvoiceDetailTableOpr().getRowCount());
    assertEquals(-1, getInvoiceDetailTableOpr().getSelectedRow());

    assertFalse(getAddQuantityOpr().isEnabled());
    assertFalse(getSubQuantityOpr().isEnabled());
    assertFalse(getDeleteQuantityOpr().isEnabled());
    validateTotalAmount("0");
    assertEquals("0", getTextFieldOpr("tfdTotalReturnAmount").getText());

    // test delete first row
    getButtonOpr("Tiger").clickMouse();
    getButtonOpr("Heineken_lon").clickMouse();
    getInvoiceDetailTableOpr().setRowSelectionInterval(0, 0);
    getDeleteQuantityOpr().clickMouse();
    assertEquals(0, getInvoiceDetailTableOpr().getSelectedRow());
  }
}
