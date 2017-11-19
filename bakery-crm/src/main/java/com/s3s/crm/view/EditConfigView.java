package com.s3s.crm.view;

import java.util.Map;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.ConfigDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditConfigView extends AEditServiceView<ConfigDto> {
  private static final long serialVersionUID = 1L;
  private static String generalTab = ControlConfigUtils.getString("label.ConfigDto.generalTab");
  private static String emailTab = ControlConfigUtils.getString("label.CompanyDto.emailTab");

  public EditConfigView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    detailDataModel.tab(generalTab, generalTab, null);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("address", DetailFieldType.TEXTAREA);
    detailDataModel.addAttribute("phone", DetailFieldType.TEXTBOX);
    detailDataModel.addAttribute("fixPhone", DetailFieldType.TEXTBOX);
    detailDataModel.addAttribute("pointValue", DetailFieldType.LONG_NUMBER);
    detailDataModel.addAttribute("rewardIntroducePercent", DetailFieldType.POSITIVE_NUMBER_SPINNER);
    detailDataModel.addAttribute("warningBirthdayBerore", DetailFieldType.POSITIVE_NUMBER_SPINNER);
    detailDataModel.addAttribute("sendMailAfterPayment", DetailFieldType.CHECKBOX);
    detailDataModel.addAttribute("prefixInvoiceCode", DetailFieldType.TEXTBOX);
    detailDataModel.tab(emailTab, emailTab, null);
    detailDataModel.addAttribute("emailHostName", DetailFieldType.TEXTBOX).editable(false);
    detailDataModel.addAttribute("smtpPort", DetailFieldType.TEXTBOX).editable(false);
    detailDataModel.addAttribute("emailUsername", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("emailPassword", DetailFieldType.PASSWORD).mandatory(true);
  }

  @Override
  public IViewService<ConfigDto> getViewService() {
    return CrmContextProvider.getInstance().getConfigService();
  }

}
