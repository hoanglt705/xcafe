package com.s3s.ssm.pos;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

public class InvoiceActionBarAddSubProductTest extends AbstractPosTest {
  @Before
  @Override
  public void setUp() {
    super.setUp();
    moveToOrderPanel();
  }

  @Test
  public void testAddSubProduct() {
    getButtonOpr("Tiger").clickMouse();
    getButtonOpr("Heineken_lon").clickMouse();
    getButtonOpr("cafeDa").clickMouse();

    JButtonOperator btnAddQuantityOpr = new JButtonOperator(mainFrameOperator, new NameComponentChooser(
            "btnAddQuantity"));
    JButtonOperator btnSubQuantityOpr = new JButtonOperator(mainFrameOperator, new NameComponentChooser(
            "btnSubQuantity"));
    assertEquals(1, (int) getInvoiceDetailTableOpr().getValueAt(0, 3));
    assertEquals(1, (int) getInvoiceDetailTableOpr().getValueAt(1, 3));
    assertEquals(1, (int) getInvoiceDetailTableOpr().getValueAt(2, 3));
    validateTotalAmount("63.000");

    JButtonOperator btnMoveDownOpr = new JButtonOperator(mainFrameOperator, new NameComponentChooser(
            "btnMoveDown"));
    btnMoveDownOpr.clickMouse();
    btnAddQuantityOpr.clickMouse();
    btnAddQuantityOpr.clickMouse();
    btnAddQuantityOpr.clickMouse();

    assertEquals(4, (int) getInvoiceDetailTableOpr().getValueAt(0, 3));
    validateTotalAmount("114.000");
    assertEquals("-114.000", getTextFieldOpr("tfdTotalReturnAmount").getText());

    btnSubQuantityOpr.clickMouse();
    assertEquals(3, (int) getInvoiceDetailTableOpr().getValueAt(0, 3));
    validateTotalAmount("97.000");
    assertEquals("-97.000", getTextFieldOpr("tfdTotalReturnAmount").getText());
    btnSubQuantityOpr.clickMouse(10);
    assertEquals(1, (int) getInvoiceDetailTableOpr().getValueAt(0, 3));
    validateTotalAmount("63.000");
    assertEquals("-63.000", getTextFieldOpr("tfdTotalReturnAmount").getText());
  }
}
