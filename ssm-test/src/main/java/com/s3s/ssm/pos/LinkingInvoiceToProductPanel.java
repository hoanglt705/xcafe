package com.s3s.ssm.pos;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

public class LinkingInvoiceToProductPanel extends AbstractPosTest {
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
  public void testLinkingDetailTableAndProductPanel() {
    assertEquals(-1, getInvoiceDetailTableOpr().getSelectedRow());
    tigerButtonOpr.clickMouse();
    assertEquals(0, getInvoiceDetailTableOpr().getSelectedRow());
    tigerButtonOpr.clickMouse();
    assertEquals(0, getInvoiceDetailTableOpr().getSelectedRow());
    heinekenButtonOpr.clickMouse();
    assertEquals(1, getInvoiceDetailTableOpr().getSelectedRow());
    tigerButtonOpr.clickMouse();
    assertEquals(0, getInvoiceDetailTableOpr().getSelectedRow());
  }
}
