package com.s3s.ssm.pos.tableview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

import com.s3s.ssm.pos.AbstractPosTest;
import com.s3s.ssm.widget.vbadge.VBadge;

public class StopTableViewTest extends AbstractPosTest {

  @Before
  @Override
  public void setUp() {
    super.setUp();
  }

  @Test
  public void testStopInvoice() {
    moveToOrderPanel();
    int initvalue = ((VBadge) getButtonOpr("Tiger").getSource()).getBadgeValue().intValue();
    getButtonOpr("Tiger").clickMouse();
    moveToPosScreen();

    JButtonOperator btnStopOpr = new JButtonOperator(new ContainerOperator(getTableView()),
            new NameComponentChooser("btnStop"));
    assertTrue(btnStopOpr.isEnabled());
    btnStopOpr.clickMouse();

    assertNull(getTableView().getLeftDecoration());
    JButtonOperator btnStopOpr1 = new JButtonOperator(new ContainerOperator(getTableView()),
            new NameComponentChooser("btnStop"));
    assertFalse(btnStopOpr1.isEnabled());
    JButtonOperator btnDeleteOpr = new JButtonOperator(new ContainerOperator(getTableView()),
            new NameComponentChooser("btnDelete"));
    assertFalse(btnDeleteOpr.isEnabled());
    JButtonOperator btnShoppingCartOpr = new JButtonOperator(new ContainerOperator(getTableView()),
            new NameComponentChooser("btnShoppingCart"));
    assertTrue(btnShoppingCartOpr.isEnabled());
    assertEquals(0, getTableView().getContentContainer().getComponentCount());

    moveToOrderPanel();
    assertEquals(initvalue, ((VBadge) getButtonOpr("Tiger").getSource()).getBadgeValue().intValue());
  }
}
