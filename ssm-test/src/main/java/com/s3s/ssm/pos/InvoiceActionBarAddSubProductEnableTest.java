package com.s3s.ssm.pos;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

public class InvoiceActionBarAddSubProductEnableTest extends AbstractPosTest {

  private JButtonOperator tigerButtonOpr;

  @Override
  public void setUp() {
    super.setUp();
    moveToOrderPanel();
    tigerButtonOpr = new JButtonOperator(mainFrameOperator, new NameComponentChooser("Tiger"));
  }

  @Test
  public void testEnable() {
    assertTrue(!getAddQuantityOpr().isEnabled());
    assertTrue(!getSubQuantityOpr().isEnabled());
    tigerButtonOpr.clickMouse();
    assertTrue(getAddQuantityOpr().isEnabled());
    assertTrue(getSubQuantityOpr().isEnabled());
  }

}
