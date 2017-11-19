package com.s3s.ssm.view.list.catalog;

import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;

import net.sf.jsefa.csv.CsvIOFactory;
import net.sf.jsefa.csv.CsvSerializer;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.FoodDto;
import com.s3s.ssm.export.dto.FoodExporterDto;
import com.s3s.ssm.export.dto.FoodIngredientExporterDto;
import com.s3s.ssm.export.dto.ProductExporterDto;
import com.s3s.ssm.export.dto.ProductTypeExporterDto;
import com.s3s.ssm.view.detail.catalog.EditFoodView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AExportableListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListFoodView extends AExportableListEntityView<FoodDto, FoodExporterDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListFoodView() {
    super();
  }

  public ListFoodView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("image", ListRendererType.IMAGE);
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("name", ListRendererType.TEXT);
    listDataModel.addColumn("productType.name", ListRendererType.TEXT);
    listDataModel.addColumn("unitPrice", ListRendererType.NUMBER);
    listDataModel.addColumn("sellPrice", ListRendererType.NUMBER);
  }

  @Override
  protected Class<? extends AbstractEditView<FoodDto>> getEditViewClass() {
    return EditFoodView.class;
  }

  @Override
  protected CsvSerializer getCsvSerializer() {
    return CsvIOFactory.createFactory(ProductExporterDto.class, FoodExporterDto.class,
            FoodIngredientExporterDto.class, ProductTypeExporterDto.class).createSerializer();
  }

  @Override
  protected List<String> exportHeader() {
    return Arrays.asList("", "code", "name", "productType.code", "productType.name", "sellPrice", "uom.code",
            "uom.name", "unitPrice");
  }

  @Override
  protected FoodExporterDto transferToExportData(FoodDto entity) {
    // FoodExporterDto foodExporterDto = new FoodExporterDto();
    // foodExporterDto.setCode(entity.getCode());
    // foodExporterDto.setName(entity.getName());
    //
    // ProductTypeExporterDto productTypeExporterDto = new ProductTypeExporterDto();
    // ProductType productType = entity.getProductType();
    // productTypeExporterDto.setCode(productType.getCode());
    // productTypeExporterDto.setName(productType.getName());
    // foodExporterDto.setProductTypeDto(productTypeExporterDto);
    //
    // foodExporterDto.setSellPrice(entity.getSellPrice());
    // UnitOfMeasure uom = entity.getUom();
    // foodExporterDto.setUomCode(uom.getCode());
    // foodExporterDto.setUomName(uom.getName());
    //
    // foodExporterDto.setUnitPrice(entity.getUnitPrice());
    // entity.getFoodIngredients().forEach(ingredient -> {
    // FoodIngredientExporterDto dto = new FoodIngredientExporterDto();
    // dto.setMaterialCode(ingredient.getMaterial().getCode());
    // dto.setMaterialName(ingredient.getMaterial().getName());
    //
    // UnitOfMeasure ingreUom = ingredient.getUom();
    // dto.setUomCode(ingreUom.getCode());
    // dto.setUomName(ingreUom.getName());
    //
    // dto.setQuantity(ingredient.getQuantity());
    // dto.setUnitPrice(ingredient.getUnitPrice());
    // dto.setSubPriceTotal(ingredient.getSubPriceTotal());
    // foodExporterDto.getIngredientDtos().add(dto);
    // });
    // return foodExporterDto;
    return null;
  }

  @Override
  protected FoodDto transferToImportData(FoodExporterDto exportDto) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected void loadService() {
    setViewService(ManagerContextProvider.getInstance().getFoodService());
  }

}
