package com.s3s.crm.view.customerCard;

import java.util.List;
import java.util.Map;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.CustomerCardDto;
import com.s3s.crm.dto.CustomerDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditCustomerCardView extends AEditServiceView<CustomerCardDto> {
  private static final long serialVersionUID = 1L;

  public EditCustomerCardView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    List<CustomerDto> customers = CrmContextProvider.getInstance().getCustomerService()
            .findByActive(0, Integer.MAX_VALUE);
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
    detailDataModel.addAttribute("customer", DetailFieldType.DROPDOWN_AUTOCOMPLETE).mandatory(true)
            .setDataList(customers);
  }

  @Override
  public IViewService<CustomerCardDto> getViewService() {
    return CrmContextProvider.getInstance().getCustomerCardService();
  }

  @Override
  protected String getDefaultTitle(CustomerCardDto entity) {
    return entity.getCode();
  }

}
