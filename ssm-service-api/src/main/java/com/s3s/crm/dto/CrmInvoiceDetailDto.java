package com.s3s.crm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class CrmInvoiceDetailDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private CrmInvoiceDto invoice;
  private CrmProductDto product;
  private MaterialTypeDto materialType;
  private SizeDto size;
  private ShapeDto shape;
  private InternalMaterialDto internalMaterial;
  private Integer quantity = 0;
  private Long unitPrice = 0L;
  private Long amount = 0L;

  public MaterialTypeDto getMaterialType() {
    return materialType;
  }

  public void setMaterialType(MaterialTypeDto materialType) {
    this.materialType = materialType;
  }

  public SizeDto getSize() {
    return size;
  }

  public void setSize(SizeDto size) {
    this.size = size;
  }

  public ShapeDto getShape() {
    return shape;
  }

  public void setShape(ShapeDto shape) {
    this.shape = shape;
  }

  public InternalMaterialDto getInternalMaterial() {
    return internalMaterial;
  }

  public void setInternalMaterial(InternalMaterialDto internalMaterial) {
    this.internalMaterial = internalMaterial;
  }

  public CrmInvoiceDto getInvoice() {
    return invoice;
  }

  public void setInvoice(CrmInvoiceDto invoice) {
    this.invoice = invoice;
  }

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

  public CrmProductDto getProduct() {
    return product;
  }

  public void setProduct(CrmProductDto product) {
    this.product = product;
  }

  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }
}
