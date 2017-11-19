package com.s3s.ssm.view.util;

import com.s3s.ssm.dto.ProductDto;

public class InvoiceUtil {

  public long calculateVatTaxAmount(long totalAmount, int vatTax) {
    if (totalAmount < 0 || vatTax < 0) {
      throw new IllegalArgumentException("The parameter must be greater than 0");
    }

    long vatTaxAmount = (vatTax * totalAmount) / 100;
    return vatTaxAmount;
  }

  public long calculateTotalReturnAmount(long totalAmount, long totalPaymentAmount, long discount,
          long vatTaxAmount) {
    return totalPaymentAmount + discount - (totalAmount + vatTaxAmount);
  }

  public boolean enoughForSell(ProductDto productDto) {
    return productDto.isFood() || productDto.getQuantityInStock() > 0;
  }
}
