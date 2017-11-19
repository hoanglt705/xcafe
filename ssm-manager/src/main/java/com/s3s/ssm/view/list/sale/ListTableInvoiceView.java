package com.s3s.ssm.view.list.sale;

import java.util.List;

import javax.swing.Icon;
import javax.swing.SwingWorker;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.InvoiceDto;
import com.s3s.ssm.export.dto.InvoiceExporterDto;
import com.s3s.ssm.view.detail.sale.EditTableInvoiceView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AExportableListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListTableInvoiceView extends AExportableListEntityView<InvoiceDto, InvoiceExporterDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListTableInvoiceView() {
    super();
  }

  public ListTableInvoiceView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("createdDate", ListRendererType.DATE);
    listDataModel.addColumn("foodTable.name", ListRendererType.TEXT);
    listDataModel.addColumn("totalAmount", ListRendererType.NUMBER);
    listDataModel.addColumn("totalPaymentAmount", ListRendererType.NUMBER);
    listDataModel.addColumn("totalReturnAmount", ListRendererType.NUMBER);
    listDataModel.addColumn("income", ListRendererType.NUMBER);
  }

  @Override
  protected Class<? extends AbstractEditView<InvoiceDto>> getEditViewClass() {
    return EditTableInvoiceView.class;
  }

  @Override
  protected void afterDeleteRows(List<InvoiceDto> removedEntities) {
    super.afterDeleteRows(removedEntities);
    new RecoverProductTask(removedEntities).execute();
  }

  private class RecoverProductTask extends SwingWorker<Object, Object> {
    private final List<InvoiceDto> entities;

    public RecoverProductTask(List<InvoiceDto> entities) {
      this.entities = entities;
    }

    @Override
    protected Object doInBackground() throws Exception {
      // serviceProvider.getService(ISaleService.class).recoverProduct(entities);
      return null;
    }
  }

  @Override
  protected InvoiceExporterDto transferToExportData(InvoiceDto entity) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected InvoiceDto transferToImportData(InvoiceExporterDto exportDto) {
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
    setViewService(ManagerContextProvider.getInstance().getInvoiceService());
  }

}
