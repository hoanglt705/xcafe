package com.s3s.ssm.util;

import com.sunrise.xdoc.entity.catalog.Food;
import com.sunrise.xdoc.entity.catalog.FoodIngredient;
import com.sunrise.xdoc.entity.catalog.Material;
import com.sunrise.xdoc.entity.sale.Invoice;
import com.sunrise.xdoc.entity.sale.InvoiceDetail;

public class InvoiceUtil {
  public double findMaterialQuantityInInvoice(Invoice invoice, Material material) {
    if (invoice == null) {
      throw new IllegalArgumentException("The invoice must be  not null");
    }
    if (material == null) {
      throw new IllegalArgumentException("The material must be  not null");
    }
    UomUtil uomUtil = new UomUtil();
    double quantityInInvoice = 0d;
    for (InvoiceDetail detail : invoice.getInvoiceDetails()) {
      if (detail.getProduct() instanceof Food) {
        Food food = (Food) detail.getProduct();
        for (FoodIngredient ingredient : food.getFoodIngredients()) {
          if (material.equals(ingredient.getMaterial())) {
            quantityInInvoice += detail.getQuantity()
                    * uomUtil.exchangeQuantity(ingredient.getUom(), material.getUom(),
                            ingredient.getQuantity());
          }
        }
      } else { // Material
        if (material.equals(detail.getProduct())) {
          quantityInInvoice += detail.getQuantity();
        }
      }
    }
    return quantityInInvoice;
  }
}
