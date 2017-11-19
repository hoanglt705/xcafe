package com.s3s.crm.view.customer;

import java.util.List;
import java.util.Map;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.CustomerDto;
import com.s3s.crm.dto.CustomerTypeDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditCustomerView extends AEditServiceView<CustomerDto> {
  private static final long serialVersionUID = 1L;

  public EditCustomerView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    List<CustomerTypeDto> customerTypes = CrmContextProvider.getInstance().getCustomerTypeService()
            .findByActive(0, Integer.MAX_VALUE);
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("customerType", DetailFieldType.DROPDOWN_AUTOCOMPLETE).mandatory(true)
            .setDataList(customerTypes);
    detailDataModel.addAttribute("birthday", DetailFieldType.DATE).mandatory(true);
    detailDataModel.addAttribute("email", DetailFieldType.TEXTBOX);
    detailDataModel.addAttribute("phoneNumber", DetailFieldType.TEXTBOX);
    detailDataModel.addAttribute("point", DetailFieldType.TEXTBOX).editable(false);
  }

  @Override
  public IViewService<CustomerDto> getViewService() {
    return CrmContextProvider.getInstance().getCustomerService();
  }
}
