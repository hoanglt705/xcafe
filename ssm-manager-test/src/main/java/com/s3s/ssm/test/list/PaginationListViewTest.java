package com.s3s.ssm.test.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.s3s.ssm.test.AbstractTest;

public class PaginationListViewTest extends AbstractTest {

  @Test
  public void testInitialization() {
    getButtonOpr("btnFoodTable").clickMouse();
    assertFalse(getButtonOpr("btnFirst").isEnabled());
    assertFalse(getButtonOpr("btnPrevious").isEnabled());
    assertTrue(getButtonOpr("btnNext").isEnabled());
    assertTrue(getButtonOpr("btnLast").isEnabled());
    assertEquals("1", getTextFieldOpr("txtCurrentPageNumber").getText());
    assertFalse(getButtonOpr("btnEntityEdit").isEnabled());
    assertFalse(getButtonOpr("btnEntityDelete").isEnabled());

    getButtonOpr("btnNext").clickMouse();
    assertEquals("2", getTextFieldOpr("txtCurrentPageNumber").getText());

    assertTrue(getButtonOpr("btnFirst").isEnabled());
    assertTrue(getButtonOpr("btnPrevious").isEnabled());
    assertTrue(getButtonOpr("btnNext").isEnabled());
    assertTrue(getButtonOpr("btnLast").isEnabled());

    assertFalse(getButtonOpr("btnEntityEdit").isEnabled());
    assertFalse(getButtonOpr("btnEntityDelete").isEnabled());

    getButtonOpr("btnLast").clickMouse();
    assertTrue(getButtonOpr("btnFirst").isEnabled());
    assertTrue(getButtonOpr("btnPrevious").isEnabled());
    assertFalse(getButtonOpr("btnNext").isEnabled());
    assertFalse(getButtonOpr("btnLast").isEnabled());

    getButtonOpr("btnFirst").clickMouse();
    assertFalse(getButtonOpr("btnFirst").isEnabled());
    assertFalse(getButtonOpr("btnPrevious").isEnabled());
    assertTrue(getButtonOpr("btnNext").isEnabled());
    assertTrue(getButtonOpr("btnLast").isEnabled());
  }
}
