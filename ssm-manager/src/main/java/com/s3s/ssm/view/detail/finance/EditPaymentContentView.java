package com.s3s.ssm.view.detail.finance;

import java.util.List;
import java.util.Map;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.PaymentContentDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditPaymentContentView extends AEditServiceView<PaymentContentDto> {
  private static final long serialVersionUID = 728867266827208141L;

  public EditPaymentContentView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    List<PaymentContentDto> paymentContents = ManagerContextProvider.getInstance().getPaymentContentService()
            .findByActive(0, Integer.MAX_VALUE);
    if (!isCreateNew()) {
      paymentContents.remove(entity);
    }

    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
    // detailDataModel.addAttribute("paymentType", DetailFieldType.DROPDOWN).mandatory(true)
    // .setDataList(Arrays.asList(PaymentType.values()));
    detailDataModel.addAttribute("parent", DetailFieldType.DROPDOWN).setDataList(paymentContents);
  }

  @Override
  protected String getDefaultTitle(PaymentContentDto entity) {
    return entity.getName();
  }

  @Override
  public IViewService<PaymentContentDto> getViewService() {
    return ManagerContextProvider.getInstance().getPaymentContentService();
  }
}
