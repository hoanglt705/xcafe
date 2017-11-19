package com.s3s.ssm.pos;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import com.s3s.ssm.dto.ProductDto;
import com.s3s.ssm.view.order.TransactionHandler;

public class TransactionHandlerTest {

  @Test
  public void testEmpty() {
    TransactionHandler transactionHandler = new TransactionHandler();
    assertTrue(transactionHandler.isEmpty());
  }

  @Test
  public void testUpdateTransaction() {
    TransactionHandler transactionHandler = new TransactionHandler();
    ProductDto tiger = new ProductDto("Tiger", "Tiger");
    ProductDto coca = new ProductDto("Coca", "Coca");
    ProductDto cafe = new ProductDto("Cafe", "Cafe");
    cafe.setFood(true);

    transactionHandler.updateTransaction(tiger, 2);
    transactionHandler.updateTransaction(coca, -3);
    transactionHandler.updateTransaction(cafe, 5);
    Map<ProductDto, Integer> transaction = transactionHandler.getTransaction();
    assertNotNull(transaction.get(tiger));
    assertNotNull(transaction.get(coca));
    assertNotNull(transaction.get(cafe));
  }

  @Test
  public void testClear() {
    TransactionHandler transactionHandler = new TransactionHandler();
    ProductDto tiger = new ProductDto("Tiger", "Tiger");
    ProductDto coca = new ProductDto("Coca", "Coca");
    transactionHandler.updateTransaction(tiger, 2);
    transactionHandler.updateTransaction(coca, 3);
    transactionHandler.clear();
    assertTrue(transactionHandler.isEmpty());
  }

}
