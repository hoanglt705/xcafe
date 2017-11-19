package com.s3s.crm.view.product;

import java.util.List;
import java.util.Map;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.CrmProductDto;
import com.s3s.crm.dto.InternalMaterialDto;
import com.s3s.crm.dto.MaterialTypeDto;
import com.s3s.crm.dto.ShapeDto;
import com.s3s.crm.dto.SizeDto;
import com.s3s.crm.util.ProductUtils;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditCrmProductView extends AEditServiceView<CrmProductDto> {
  private static final long serialVersionUID = 1L;

  public EditCrmProductView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    List<ShapeDto> shapes = CrmContextProvider.getInstance().getShapeService()
            .findByActive(0, Integer.MAX_VALUE);
    List<SizeDto> sizes = CrmContextProvider.getInstance().getSizeService()
            .findByActive(0, Integer.MAX_VALUE);
    List<MaterialTypeDto> materialTypes = CrmContextProvider.getInstance().getMaterialTypeService()
            .findByActive(0, Integer.MAX_VALUE);
    List<InternalMaterialDto> internalMaterials = CrmContextProvider.getInstance()
            .getInternalMaterialService()
            .findByActive(0, Integer.MAX_VALUE);

    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("image", DetailFieldType.IMAGE);
    detailDataModel.addAttribute("shape", DetailFieldType.DROPDOWN_AUTOCOMPLETE).setDataList(shapes);
    detailDataModel.addAttribute("size", DetailFieldType.DROPDOWN_AUTOCOMPLETE).setDataList(sizes);
    detailDataModel.addAttribute("materialType", DetailFieldType.DROPDOWN_AUTOCOMPLETE).setDataList(
            materialTypes);
    detailDataModel.addAttribute("internalMaterial", DetailFieldType.DROPDOWN_AUTOCOMPLETE).setDataList(
            internalMaterials);
    detailDataModel.addAttribute("sellPrice", DetailFieldType.LONG_NUMBER);
  }

  @Override
  protected void beforeSaveOrUpdate(CrmProductDto productDto) {
    productDto.setTag(ProductUtils.generateTag(productDto));
  }

  @Override
  public IViewService<CrmProductDto> getViewService() {
    return CrmContextProvider.getInstance().getCrmProductService();
  }
}
