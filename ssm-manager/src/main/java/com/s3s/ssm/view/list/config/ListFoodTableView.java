package com.s3s.ssm.view.list.config;

import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.AreaDto;
import com.s3s.ssm.dto.FoodTableDto;
import com.s3s.ssm.export.dto.AreaExporterDto;
import com.s3s.ssm.export.dto.FoodTableExporterDto;
import com.s3s.ssm.view.detail.config.EditFoodTableView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AExportableListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListFoodTableView extends AExportableListEntityView<FoodTableDto, FoodTableExporterDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListFoodTableView() {
    super();
  }

  public ListFoodTableView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void loadService() {
    setViewService(ManagerContextProvider.getInstance().getFoodTableService());
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("name", ListRendererType.TEXT);
    listDataModel.addColumn("area.name", ListRendererType.TEXT);
    listDataModel.addColumn("seatNum", ListRendererType.NUMBER);
  }

  @Override
  protected Class<? extends AbstractEditView<FoodTableDto>> getEditViewClass() {
    return EditFoodTableView.class;
  }

  @Override
  protected List<String> exportHeader() {
    return Arrays.asList("code", "name", "area.code", "area.name");
  }

  @Override
  protected FoodTableExporterDto transferToExportData(FoodTableDto entity) {
    FoodTableExporterDto dto = new FoodTableExporterDto();
    dto.setCode(entity.getCode());
    dto.setName(entity.getName());
    dto.setSeatNum(entity.getSeatNum());
    AreaDto area = entity.getArea();
    AreaExporterDto areaDto = new AreaExporterDto();
    areaDto.setCode(area.getCode());
    areaDto.setName(area.getName());
    dto.setArea(areaDto);
    return dto;
  }

  @Override
  protected FoodTableDto transferToImportData(FoodTableExporterDto exportDto) {
    FoodTableDto foodTable = new FoodTableDto();
    foodTable.setCode(exportDto.getCode());
    foodTable.setName(exportDto.getName());
    foodTable.setSeatNum(exportDto.getSeatNum());
    return foodTable;
  }
}
