package com.sunrise.xdoc.entity.crm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "crm_product")
public class CrmProduct extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private byte[] image;
  private Shape shape;
  private Size size;
  private MaterialType materialType;
  private InternalMaterial internalMaterial;
  private Long sellPrice;
  private String tag;

  @Column(name = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "image", length = 5 * 1024 * 1024)
  @Lob
  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  @ManyToOne
  @JoinColumn(name = "shape_id")
  public Shape getShape() {
    return shape;
  }

  public void setShape(Shape shape) {
    this.shape = shape;
  }

  @ManyToOne
  @JoinColumn(name = "size_id")
  public Size getSize() {
    return size;
  }

  public void setSize(Size size) {
    this.size = size;
  }

  @ManyToOne
  @JoinColumn(name = "material_type_id")
  public MaterialType getMaterialType() {
    return materialType;
  }

  public void setMaterialType(MaterialType materialType) {
    this.materialType = materialType;
  }

  @Column(name = "sell_price")
  public Long getSellPrice() {
    return sellPrice;
  }

  public void setSellPrice(Long sellPrice) {
    this.sellPrice = sellPrice;
  }

  @ManyToOne
  @JoinColumn(name = "internal_material_id")
  public InternalMaterial getInternalMaterial() {
    return internalMaterial;
  }

  public void setInternalMaterial(InternalMaterial internalMaterial) {
    this.internalMaterial = internalMaterial;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

}
