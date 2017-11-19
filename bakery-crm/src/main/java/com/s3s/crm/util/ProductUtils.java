package com.s3s.crm.util;

import com.s3s.crm.dto.CrmProductDto;
import com.s3s.crm.dto.InternalMaterialDto;
import com.s3s.crm.dto.MaterialTypeDto;
import com.s3s.crm.dto.ShapeDto;
import com.s3s.crm.dto.SizeDto;

public final class ProductUtils {
  private ProductUtils() {
  }

  public static String generateTag(CrmProductDto productDto) {
    return generateTag(productDto.getMaterialType(), productDto.getSize(),
            productDto.getShape(), productDto.getInternalMaterial());
  }

  public static String generateTag(MaterialTypeDto materialType, SizeDto size, ShapeDto shape,
          InternalMaterialDto internalMaterial) {
    String sizeCode = size != null ? size.getCode() : "";
    String shapeCode = shape != null ? shape.getCode() : "";
    String materialTypeCode = materialType != null ? materialType.getCode() : "";
    String internalMaterialCode = internalMaterial != null ? internalMaterial.getCode() : "";
    StringBuilder tag = new StringBuilder();
    tag.append(sizeCode);
    tag.append("-");
    tag.append(shapeCode);
    tag.append("-");
    tag.append(materialTypeCode);
    tag.append("-");
    tag.append(internalMaterialCode);
    return tag.toString();
  }
}
