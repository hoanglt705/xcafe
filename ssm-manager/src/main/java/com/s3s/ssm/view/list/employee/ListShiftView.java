package com.s3s.ssm.view.list.employee;

import java.util.List;

import javax.swing.Icon;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.ShiftDto;
import com.s3s.ssm.export.dto.ShiftExporterDto;
import com.s3s.ssm.view.detail.employee.EditShiftView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AExportableListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListShiftView extends AExportableListEntityView<ShiftDto, ShiftExporterDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListShiftView() {
    super();
  }

  public ListShiftView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("name", ListRendererType.TEXT);
    listDataModel.addColumn("startTime", ListRendererType.HOUR_MIN);
    listDataModel.addColumn("endTime", ListRendererType.HOUR_MIN);
  }

  @Override
  protected Class<? extends AbstractEditView<ShiftDto>> getEditViewClass() {
    return EditShiftView.class;
  }

  @Override
  protected ShiftExporterDto transferToExportData(ShiftDto entity) {
    return null;
  }

  @Override
  protected ShiftDto transferToImportData(ShiftExporterDto exportDto) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected List<String> exportHeader() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected void loadService() {
    setViewService(ManagerContextProvider.getInstance().getShiftService());
  }
}
