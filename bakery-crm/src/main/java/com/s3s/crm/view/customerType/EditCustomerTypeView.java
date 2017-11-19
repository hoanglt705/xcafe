package com.s3s.crm.view.customerType;

import java.util.Map;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.CustomerTypeDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditCustomerTypeView extends AEditServiceView<CustomerTypeDto> {
  private static final long serialVersionUID = 1L;

  public EditCustomerTypeView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("minPoint", DetailFieldType.POSITIVE_NUMBER_SPINNER).mandatory(true);
    detailDataModel.addAttribute("discountPercent", DetailFieldType.POSITIVE_NUMBER_SPINNER);
    detailDataModel.addAttribute("effectiveTime", DetailFieldType.POSITIVE_NUMBER_SPINNER);
    detailDataModel.addAttribute("pointToRemind", DetailFieldType.POSITIVE_NUMBER_SPINNER);
  }

  @Override
  public IViewService<CustomerTypeDto> getViewService() {
    return CrmContextProvider.getInstance().getCustomerTypeService();
  }

  @Override
  protected String getDefaultTitle(CustomerTypeDto entity) {
    return entity.getCode();
  }

}
