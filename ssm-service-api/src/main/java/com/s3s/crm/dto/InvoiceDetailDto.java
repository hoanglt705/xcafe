package com.s3s.crm.dto;

import com.s3s.ssm.dto.ProductDto;
import com.s3s.ssm.dto.base.AbstractCodeObject;

public class InvoiceDetailDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String invoiceCode;
  private ProductDto product;
  private Integer quantity = 0;
  private Long unitPrice = 0L;
  private Long amount = 0L;

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Long getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Long unitPrice) {
    this.unitPrice = unitPrice;
  }

  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }

  public String getInvoiceCode() {
    return invoiceCode;
  }

  public void setInvoiceCode(String invoiceCode) {
    this.invoiceCode = invoiceCode;
  }

  public ProductDto getProduct() {
    return product;
  }

  public void setProduct(ProductDto productDto) {
    this.product = productDto;
  }

  @Override
  public int hashCode() {
    return getProduct().getId().hashCode() + getInvoiceCode().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof InvoiceDetailDto)) {
      return false;
    }
    if (((InvoiceDetailDto) obj).getInvoiceCode() == null || getInvoiceCode() == null) {
      return false;
    }
    return getInvoiceCode().equals(((InvoiceDetailDto) obj).getInvoiceCode())
            && getProduct().getCode().equals(((InvoiceDetailDto) obj).getProduct().getCode());
  }
}
