package com.sunrise.xdoc.entity.crm;

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

@Entity
@Table(name = "crm_invoice_detail")
@Inheritance(strategy = InheritanceType.JOINED)
public class CrmInvoiceDetail extends AbstractIdOLObject {
  private static final long serialVersionUID = 1L;
  private CrmProduct product;
  private CrmInvoice invoice;
  private Integer quantity = 0;
  private Long unitPrice = 0L;
  private Long amount = 0L;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id")
  @NotNull
  public CrmProduct getProduct() {
    return product;
  }

  public void setProduct(CrmProduct product) {
    this.product = product;
  }

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "invoice_id")
  @NotNull
  public CrmInvoice getInvoice() {
    return invoice;
  }

  public void setInvoice(CrmInvoice invoice) {
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
}
