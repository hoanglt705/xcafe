package com.s3s.ssm.view.list.config;

import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.UomCategoryDto;
import com.s3s.ssm.export.dto.UomCategoryExporterDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.EditUomCategoryView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AExportableListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListUomCategoryView extends AExportableListEntityView<UomCategoryDto, UomCategoryExporterDto> {
  private static final long serialVersionUID = 959503437038525184L;

  public ListUomCategoryView() {
    super();
  }

  public ListUomCategoryView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("name", ListRendererType.TEXT);
    listDataModel.addColumn("parent", ListRendererType.TEXT);
  }

  @Override
  protected Class<? extends AbstractEditView<UomCategoryDto>> getEditViewClass() {
    return EditUomCategoryView.class;
  }

  @Override
  protected UomCategoryExporterDto transferToExportData(UomCategoryDto entity) {
    UomCategoryExporterDto dto = new UomCategoryExporterDto();
    dto.setCode(entity.getCode());
    dto.setName(entity.getName());
    UomCategoryDto parent = entity.getParent();
    if (parent != null) {
      dto.setParentCode(parent.getCode());
      dto.setParentName(parent.getName());
    }
    return dto;
  }

  @Override
  protected UomCategoryDto transferToImportData(UomCategoryExporterDto exportDto) {
    UomCategoryDto uomCategory = new UomCategoryDto();
    uomCategory.setCode(exportDto.getCode());
    uomCategory.setName(exportDto.getName());

    UomCategoryDto parentUomCategory = new UomCategoryDto();
    parentUomCategory.setCode(exportDto.getCode());
    parentUomCategory.setName(exportDto.getName());

    uomCategory.setParent(parentUomCategory);
    return uomCategory;
  }

  @Override
  protected List<String> exportHeader() {
    return Arrays.asList("code", "name", "parentUomCategory.code", "parentUomCategory.name");
  }

  @Override
  public IViewService<UomCategoryDto> getViewService() {
    return ManagerContextProvider.getInstance().getUomCategoryService();
  }
}
