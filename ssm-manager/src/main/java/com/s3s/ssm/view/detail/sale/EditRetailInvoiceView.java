package com.s3s.ssm.view.detail.sale;

import java.util.Collections;
import java.util.Map;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.InvoiceDto;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;

public class EditRetailInvoiceView extends AEditServiceView<InvoiceDto> {
  private static final long serialVersionUID = 1L;

  public EditRetailInvoiceView(Map<String, Object> request) {
    super(request);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).editable(false).mandatory(true);
    detailDataModel.addAttribute("totalAmount", DetailFieldType.LONG_NUMBER).newColumn();
    detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
    detailDataModel.addAttribute("totalPaymentAmount", DetailFieldType.LONG_NUMBER).newColumn();
    detailDataModel.addAttribute("staff", DetailFieldType.DROPDOWN_AUTOCOMPLETE)
            .mandatory(true).setDataList(Collections.emptyList());
    detailDataModel.addAttribute("totalReturnAmount", DetailFieldType.LONG_NUMBER).newColumn();
    detailDataModel.addAttribute("invoiceDetails", DetailFieldType.LIST).componentInfo(
            createRetailDetailComponentInfo(entity));

  }

  @Override
  protected InvoiceDto loadForCreate(Map<String, Object> request) {
    InvoiceDto form = super.loadForCreate(request);
    String code = ManagerContextProvider.getInstance().getSequenceNumberService().getInvoiceNextSequence();
    form.setCode(code);
    return form;
  }

  private IComponentInfo createRetailDetailComponentInfo(InvoiceDto entity) {
    ListInvoiceDetailComponent component = new ListInvoiceDetailComponent();
    return new ListComponentInfo(component, "invoice");
  }

}
