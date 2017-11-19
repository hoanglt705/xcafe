package com.s3s.ssm.pos.tableview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

import com.s3s.ssm.pos.AbstractPosTest;
import com.s3s.ssm.widget.vbadge.VBadge;

public class DeleteTableViewTest extends AbstractPosTest {
  @Test
  public void testDeleteInvoice() {
    moveToOrderPanel();
    int initvalue = ((VBadge) getButtonOpr("Tiger").getSource()).getBadgeValue().intValue();
    getButtonOpr("Tiger").clickMouse();
    moveToPosScreen();

    JButtonOperator statusButtonOpr = new JButtonOperator(new ContainerOperator(getTableView()),
            new NameComponentChooser("btnStatus"));
    statusButtonOpr.clickMouse();

    JButtonOperator btnStopOpr = new JButtonOperator(new ContainerOperator(getTableView()),
            new NameComponentChooser("btnStop"));
    assertFalse(btnStopOpr.isEnabled());
    JButtonOperator btnDeleteOpr = new JButtonOperator(new ContainerOperator(getTableView()),
            new NameComponentChooser("btnDelete"));
    assertTrue(btnDeleteOpr.isEnabled());
    JButtonOperator btnShoppingCartOpr = new JButtonOperator(new ContainerOperator(getTableView()),
            new NameComponentChooser("btnShoppingCart"));
    assertTrue(btnShoppingCartOpr.isEnabled());

    btnDeleteOpr.clickMouse();

    assertEquals(0, getTableView().getContentContainer().getComponentCount());

    moveToOrderPanel();
    assertEquals(initvalue - 1, ((VBadge) getButtonOpr("Tiger").getSource()).getBadgeValue().intValue());
  }
}
