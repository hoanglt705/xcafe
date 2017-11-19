package com.s3s.ssm.pos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

import com.s3s.ssm.widget.vbadge.VBadge;

public class ProductPanelTest extends AbstractPosTest {

  @Before
  @Override
  public void setUp() {
    super.setUp();
    JButtonOperator btnStopOpr = new JButtonOperator(new ContainerOperator(getTableView()),
            new NameComponentChooser("btnStop"));
    btnStopOpr.clickMouse();
    moveToOrderPanel();
  }

  @Test
  public void testBadgeValue() {
    VBadge heinekenBadge = (VBadge) getButtonOpr("Heineken_lon").getSource();
    assertTrue(heinekenBadge.isBadgeShowing());
    assertEquals(30, heinekenBadge.getBadgeValue());
    assertEquals(0.0f, (float) heinekenBadge.getClientProperty("Synthetica.background.alpha"), 0);
    VBadge cafeDaBadge = (VBadge) getButtonOpr("cafeDa").getSource();
    assertTrue(!cafeDaBadge.isBadgeShowing());
    assertNull(cafeDaBadge.getClientProperty("Synthetica.background.alpha"));

    int initialValue = getTigerVBadge();

    getButtonOpr("Tiger").clickMouse(1);
    assertEquals(initialValue - 1, getTigerVBadge());
    assertEquals(0.0f, getAlpha(), 0);

    getButtonOpr("Tiger").clickMouse(19);
    assertEquals(0, getTigerVBadge());
    assertEquals(0.45f, getAlpha(), 0);

    getButtonOpr("Tiger").clickMouse(1);
    assertEquals(0, getTigerVBadge());
    assertEquals(0.45f, getAlpha(), 0);

    getSubQuantityOpr().clickMouse();
    assertEquals(1, getTigerVBadge());
    assertEquals(0.0f, getAlpha(), 0);
    getSubQuantityOpr().clickMouse(18);
    assertEquals(initialValue - 1, getTigerVBadge());
    assertEquals(0.0f, getAlpha(), 0);
    getSubQuantityOpr().clickMouse(1);
    assertEquals(initialValue - 1, getTigerVBadge());
    assertEquals(0.0f, getAlpha(), 0);
  }

  private float getAlpha() {
    return (float) getTigerBadge().getClientProperty("Synthetica.background.alpha");
  }

  private int getTigerVBadge() {
    return getTigerBadge().getBadgeValue().intValue();
  }

  private VBadge getTigerBadge() {
    return (VBadge) getButtonOpr("Tiger").getSource();
  }
}
