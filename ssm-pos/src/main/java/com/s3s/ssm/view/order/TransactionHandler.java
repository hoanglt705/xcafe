package com.s3s.ssm.view.order;

import java.util.HashMap;
import java.util.Map;

import com.s3s.ssm.dto.ProductDto;

public class TransactionHandler {
  private final Map<ProductDto, Integer> transaction = new HashMap<>();

  public boolean isEmpty() {
    return getTransaction().isEmpty();
  }

  public void clear() {
    getTransaction().clear();
  }

  public void updateTransaction(ProductDto productDto, int quantity) {
    // if (!productDto.isFood() && quantity > 0) {
    int newQuantity = 0;
    if (getTransaction().containsKey(productDto)) {
      newQuantity = getTransaction().get(productDto) + quantity;
    } else {
      newQuantity = quantity;
    }
    getTransaction().put(productDto, newQuantity);
    // }
  }

  public double getQuantity(ProductDto productDto) {
    return getTransaction().get(productDto);
  }

  public Map<ProductDto, Integer> getTransaction() {
    return transaction;
  }

}
