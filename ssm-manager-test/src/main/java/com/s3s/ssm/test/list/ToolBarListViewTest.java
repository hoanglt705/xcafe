package com.s3s.ssm.test.list;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.s3s.ssm.test.AbstractTest;

public class ToolBarListViewTest extends AbstractTest {
  @Override
  public void setUp() {
    super.setUp();
    getButtonOpr("btnArea").clickMouse();
  }

  @Test
  public void testVisible() {
    assertTrue(getButtonOpr("btnNew").isEnabled());
    assertFalse(getButtonOpr("btnEntityEdit").isEnabled());
    assertFalse(getButtonOpr("btnEntityDelete").isEnabled());
    assertTrue(getButtonOpr("btnExport").isEnabled());
    assertTrue(getButtonOpr("btnRefresh").isEnabled());

    getTableListEntityOpr().selectCell(0, 0);

    assertTrue(getButtonOpr("btnNew").isEnabled());
    assertTrue(getButtonOpr("btnEntityEdit").isEnabled());
    assertTrue(getButtonOpr("btnEntityDelete").isEnabled());
    assertTrue(getButtonOpr("btnExport").isEnabled());
    assertTrue(getButtonOpr("btnRefresh").isEnabled());
  }
}
