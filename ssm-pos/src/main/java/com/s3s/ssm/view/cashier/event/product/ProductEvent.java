package com.s3s.ssm.view.cashier.event.product;

import com.s3s.ssm.dto.ProductDto;

public class ProductEvent {
  private final ProductDto productDto;

  public ProductEvent(ProductDto product) {
    productDto = product;
  }

  public ProductDto getProduct() {
    return productDto;
  }

}
