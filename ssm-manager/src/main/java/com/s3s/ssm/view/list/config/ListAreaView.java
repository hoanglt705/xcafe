package com.s3s.ssm.view.list.config;

import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.AreaDto;
import com.s3s.ssm.export.dto.AreaExporterDto;
import com.s3s.ssm.view.detail.config.EditAreaView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AExportableListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListAreaView extends AExportableListEntityView<AreaDto, AreaExporterDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListAreaView() {
    super();
  }

  public ListAreaView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void loadService() {
    setViewService(ManagerContextProvider.getInstance().getAreaService());
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("name", ListRendererType.TEXT);
  }

  @Override
  protected Class<? extends AbstractEditView<AreaDto>> getEditViewClass() {
    return EditAreaView.class;
  }

  @Override
  protected List<String> exportHeader() {
    return Arrays.asList("code", "name");
  }

  @Override
  protected AreaExporterDto transferToExportData(AreaDto area) {
    AreaExporterDto dto = new AreaExporterDto();
    dto.setCode(area.getCode());
    dto.setName(area.getName());
    return dto;
  }

  @Override
  protected AreaDto transferToImportData(AreaExporterDto areaDto) {
    AreaDto area = new AreaDto();
    area.setCode(areaDto.getCode());
    area.setName(areaDto.getName());
    return area;
  }
}
