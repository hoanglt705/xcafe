package com.s3s.ssm.pos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

import com.s3s.ssm.widget.vbadge.VBadge;

public class FoodOrderTest extends AbstractPosTest {
  private JButtonOperator tigerButtonOpr;
  private JButtonOperator heinekenButtonOpr;

  @Before
  @Override
  public void setUp() {
    super.setUp();
    moveToOrderPanel();
    tigerButtonOpr = new JButtonOperator(mainFrameOperator, new NameComponentChooser("Tiger"));
    heinekenButtonOpr = new JButtonOperator(mainFrameOperator, new NameComponentChooser("Heineken_lon"));
  }

  @Test
  public void testOrder() {
    VBadge tigerBag = (VBadge) tigerButtonOpr.getSource();
    int currentQuantity = (int) tigerBag.getBadgeValue();
    tigerButtonOpr.clickMouse();
    heinekenButtonOpr.clickMouse();

    JTableOperator invoiceDetailTableOpr = getInvoiceDetailTableOpr();
    assertEquals(2, invoiceDetailTableOpr.getRowCount());

    assertEquals(1, (int) invoiceDetailTableOpr.getValueAt(0, 0));
    assertEquals("Tiger chai", invoiceDetailTableOpr.getValueAt(0, 1));
    assertEquals(1, (int) invoiceDetailTableOpr.getValueAt(0, 3));
    assertEquals(17000, (long) invoiceDetailTableOpr.getValueAt(0, 4));

    assertEquals(2, (int) invoiceDetailTableOpr.getValueAt(1, 0));
    assertEquals("Heineken lon", invoiceDetailTableOpr.getValueAt(1, 1));
    assertEquals(1, (int) invoiceDetailTableOpr.getValueAt(1, 3));
    assertEquals(21000, (long) invoiceDetailTableOpr.getValueAt(1, 4));

    assertTrue(validateTotalAmount("38.000"));

    JButtonOperator tigerButtonOpr1 = getButtonOpr("Tiger");
    VBadge tigerBag1 = (VBadge) tigerButtonOpr1.getSource();

    assertEquals(currentQuantity - 1, tigerBag1.getBadgeValue());

    tigerButtonOpr.clickMouse();

    assertEquals(2, (int) invoiceDetailTableOpr.getValueAt(0, 3));
    assertEquals(34000, (long) invoiceDetailTableOpr.getValueAt(0, 4));

    assertTrue(validateTotalAmount("55.000"));
  }

  @After
  public void tearDown() {
    try {
      Runtime.getRuntime().exec("cmd /c start C:\\temp\\kill_server.bat");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
