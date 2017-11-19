package com.s3s.ssm.view.list.config;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.UnitOfMeasureDto;
import com.s3s.ssm.dto.UomCategoryDto;
import com.s3s.ssm.export.dto.UnitOfMeasureExporterDto;
import com.s3s.ssm.export.dto.UomCategoryExporterDto;
import com.s3s.ssm.view.detail.config.EditUnitOfMeasureView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AExportableListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListUnitOfMeasureView extends
        AExportableListEntityView<UnitOfMeasureDto, UnitOfMeasureExporterDto> {
  private static final long serialVersionUID = -2332778174386103471L;

  public ListUnitOfMeasureView() {
    super();
  }

  public ListUnitOfMeasureView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("name", ListRendererType.TEXT);
    listDataModel.addColumn("shortName", ListRendererType.TEXT);
    listDataModel.addColumn("exchangeRate", ListRendererType.NUMBER);
    listDataModel.addColumn("uomCategory", ListRendererType.TEXT);
    listDataModel.addColumn("isBaseMeasure", ListRendererType.BOOLEAN);
  }

  @Override
  protected Class<? extends AbstractEditView<UnitOfMeasureDto>> getEditViewClass() {
    return EditUnitOfMeasureView.class;
  }

  @Override
  protected void beforeDeleteRow(List<UnitOfMeasureDto> removedEntities) {
    boolean hasDefault = false;
    for (UnitOfMeasureDto dto : removedEntities) {
      if (dto.getIsBaseMeasure()) {
        hasDefault = true;
        break;
      }
    }
    if (hasDefault) {
      JOptionPane.showMessageDialog(getRootPane(),
              getMessage("label.UnitOfMeasure.cannotDelete"),
              getMessage("default.dialog.info.title"), JOptionPane.ERROR_MESSAGE);
      removedEntities.clear();
    }
  }

  @Override
  protected List<String> exportHeader() {
    return Arrays.asList("code", "name", "shortName", "exchangeRate", "isBaseMeasure", "uomCategory.code",
            "uomCategory.name");
  }

  @Override
  protected UnitOfMeasureExporterDto transferToExportData(UnitOfMeasureDto entity) {
    UnitOfMeasureExporterDto dto = new UnitOfMeasureExporterDto();
    dto.setCode(entity.getCode());
    dto.setName(entity.getName());
    dto.setShortName(entity.getShortName());
    dto.setExchangeRate(new BigDecimal(entity.getExchangeRate()));
    dto.setIsBaseMeasure(entity.getIsBaseMeasure());
    UomCategoryDto uomCategory = entity.getUomCategory();
    if (uomCategory != null) {
      UomCategoryExporterDto uomCateDto = new UomCategoryExporterDto();
      uomCateDto.setCode(uomCategory.getCode());
      uomCateDto.setName(uomCategory.getName());
      dto.setUomCateDto(uomCateDto);
    }
    return dto;
  }

  @Override
  protected UnitOfMeasureDto transferToImportData(UnitOfMeasureExporterDto exportDto) {
    UnitOfMeasureDto uom = new UnitOfMeasureDto();
    uom.setCode(exportDto.getCode());
    uom.setName(exportDto.getName());
    uom.setShortName(exportDto.getShortName());
    uom.setIsBaseMeasure(exportDto.getIsBaseMeasure());
    uom.setExchangeRate(exportDto.getExchangeRate().floatValue());
    if (exportDto.getUomCateDto() != null) {
      UomCategoryDto uomCate = new UomCategoryDto();
      uomCate.setCode(exportDto.getUomCateDto().getCode());
      uomCate.setName(exportDto.getUomCateDto().getName());
      uom.setUomCategory(uomCate);
    }
    return uom;
  }

  @Override
  protected void loadService() {
    setViewService(ManagerContextProvider.getInstance().getUnitOfMeasureService());
  }
}
