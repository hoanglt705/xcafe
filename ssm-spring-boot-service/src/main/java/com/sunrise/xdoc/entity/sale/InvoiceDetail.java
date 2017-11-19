package com.sunrise.xdoc.entity.sale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.sunrise.xdoc.entity.catalog.Product;
import com.sunrise.xdoc.entity.config.UnitOfMeasure;

@Entity
@Table(name = "sale_invoice_detail")
@Inheritance(strategy = InheritanceType.JOINED)
public class InvoiceDetail extends AbstractIdOLObject {
  private static final long serialVersionUID = 1L;
  private Product product;
  private Invoice invoice;
  private UnitOfMeasure uom;
  private Integer quantity = 0;
  private Long unitPrice = 0L;
  private Long amount = 0L;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id")
  @NotNull
  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "invoice_id")
  @NotNull
  public Invoice getInvoice() {
    return invoice;
  }

  public void setInvoice(Invoice invoice) {
    this.invoice = invoice;
  }

  @Column(name = "quantity")
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  @Column(name = "unit_price")
  public Long getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Long unitPrice) {
    this.unitPrice = unitPrice;
  }

  @Column(name = "amount")
  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }

  @ManyToOne
  @JoinColumn(name = "uom_id")
  public UnitOfMeasure getUom() {
    return uom;
  }

  public void setUom(UnitOfMeasure uom) {
    this.uom = uom;
  }
}
