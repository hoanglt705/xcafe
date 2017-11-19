package com.s3s.ssm.pos;

import static org.junit.Assert.assertEquals;

import java.awt.event.KeyEvent;

import org.junit.Before;
import org.junit.Test;

public class InvoiceTest extends AbstractPosTest {

  @Before
  @Override
  public void setUp() {
    super.setUp();
    moveToOrderPanel();
  }

  @Test
  public void testCalculateMoney() {
    assertEquals("0", getTextFieldOpr("tfdTotalPaymentAmount").getText());
    assertEquals(0, (int) getJSpinnerOpr("vatSpinner").getValue());
    assertEquals(0, (int) getJSpinnerOpr("discountSpinner").getValue());
    assertEquals("0", getTextFieldOpr("tfdDiscountAmount").getText());
    assertEquals("0", getTextFieldOpr("tfdTotalReturnAmount").getText());
    validateTotalAmount("0");
    assertEquals("0", getTextFieldOpr("tfdTotalReturnAmount").getText());

    getButtonOpr("Tiger").clickMouse();
    validateTotalAmount("17.000");
    getTextFieldOpr("tfdTotalPaymentAmount").clearText();
    getTextFieldOpr("tfdTotalPaymentAmount").typeText("20000");
    validateTotalAmount("17.000");
    assertEquals("-17.000", getTextFieldOpr("tfdTotalReturnAmount").getText());

    getTextFieldOpr("tfdTotalPaymentAmount").pressKey(KeyEvent.VK_TAB);
    assertEquals(0, (int) getJSpinnerOpr("vatSpinner").getValue());
    assertEquals("0", getTextFieldOpr("tfdDiscountAmount").getText());
    assertEquals("3.000", getTextFieldOpr("tfdTotalReturnAmount").getText());

    getJSpinnerOpr("vatSpinner").getIncreaseOperator().clickMouse();// 5%
    assertEquals("2.150", getTextFieldOpr("tfdTotalReturnAmount").getText());
    getJSpinnerOpr("discountSpinner").getIncreaseOperator().clickMouse(10);
    getJSpinnerOpr("discountSpinner").pressKey(KeyEvent.VK_TAB);
    assertEquals("3.850", getTextFieldOpr("tfdTotalReturnAmount").getText());
  }
}
