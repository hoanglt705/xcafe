package com.s3s.ssm.pos;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.s3s.ssm.view.util.InvoiceUtil;

public class InvoiceUtilTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();
  private InvoiceUtil invoiceUtil = new InvoiceUtil();

  @Test
  public void testCalculateVatTaxAmount() {
    assertEquals(0l, invoiceUtil.calculateVatTaxAmount(500_000l, 0));
    assertEquals(25_000l, invoiceUtil.calculateVatTaxAmount(500_000l, 5));
    assertEquals(50_000l, invoiceUtil.calculateVatTaxAmount(500_000l, 10));
    assertEquals(75_000l, invoiceUtil.calculateVatTaxAmount(500_000l, 15));
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("The parameter must be greater than 0");
    assertEquals(0l, invoiceUtil.calculateVatTaxAmount(-1, 15));
    assertEquals(0l, invoiceUtil.calculateVatTaxAmount(500_000, -1));
  }

  @Test
  public void testEnoughForSell() {
    assertEquals(0l, invoiceUtil.calculateVatTaxAmount(500_000l, 0));
  }
}
