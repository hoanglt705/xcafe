package com.s3s.crm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class CrmProductDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private byte[] image;
  private ShapeDto shape = new ShapeDto();
  private SizeDto size = new SizeDto();
  private MaterialTypeDto materialType = new MaterialTypeDto();
  private InternalMaterialDto internalMaterial = new InternalMaterialDto();
  private Long sellPrice;
  private String tag;

  public CrmProductDto() {
  }

  public CrmProductDto(String code, String name) {
    setCode(code);
    setName(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public Long getSellPrice() {
    return sellPrice;
  }

  public void setSellPrice(Long sellPrice) {
    this.sellPrice = sellPrice;
  }

  public ShapeDto getShape() {
    return shape;
  }

  public void setShape(ShapeDto shape) {
    this.shape = shape;
  }

  public SizeDto getSize() {
    return size;
  }

  public void setSize(SizeDto size) {
    this.size = size;
  }

  public MaterialTypeDto getMaterialType() {
    return materialType;
  }

  public void setMaterialType(MaterialTypeDto materialType) {
    this.materialType = materialType;
  }

  public InternalMaterialDto getInternalMaterial() {
    return internalMaterial;
  }

  public void setInternalMaterial(InternalMaterialDto internalMaterial) {
    this.internalMaterial = internalMaterial;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }
}
