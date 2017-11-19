package com.s3s.ssm.util;

import com.sunrise.xdoc.entity.catalog.Product;
import com.sunrise.xdoc.entity.config.UnitOfMeasure;

public final class UomUtil {

  public double exchangeQuantity(UnitOfMeasure fromUom, UnitOfMeasure toUom, int quantity) {
    if (quantity < 1) {
      throw new IllegalArgumentException("The quantity must be  > 0");
    }
    if (fromUom == null || toUom == null) {
      throw new IllegalArgumentException("The parametter must be not null");
    }
    if (fromUom.equals(toUom)) {
      return quantity;
    }
    return quantity * (fromUom.getExchangeRate() / toUom.getExchangeRate());
  }

  public Long getSellPriceByUom(Product product, UnitOfMeasure uom) {
    UnitOfMeasure productUom = product.getUom();
    Long sellPrice = product.getSellPrice();
    if (productUom.equals(uom)) {
      return sellPrice;
    }
    return (long) (sellPrice * (uom.getExchangeRate() / productUom.getExchangeRate()));
  }
}
