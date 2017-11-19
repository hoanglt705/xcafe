package com.s3s.crm.view.promotion;

import javax.swing.Icon;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.PromotionDto;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListServiceView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListPromotionView extends AListServiceView<PromotionDto> {
  private static final long serialVersionUID = 1898147147716601668L;

  public ListPromotionView() {
    super();
  }

  public ListPromotionView(Icon icon, String label) {
    super(icon, label);
  }

  @Override
  protected void loadService() {
    setViewService(CrmContextProvider.getInstance().getPromotionService());
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    listDataModel.addColumn("code", ListRendererType.TEXT);
    listDataModel.addColumn("title", ListRendererType.TEXT);
    listDataModel.addColumn("fromDate", ListRendererType.DATE);
    listDataModel.addColumn("toDate", ListRendererType.DATE);
    listDataModel.addColumn("percentDiscount", ListRendererType.NUMBER);
    listDataModel.addColumn("sent", ListRendererType.BOOLEAN);
  }

  @Override
  protected Class<? extends AbstractEditView<PromotionDto>> getEditViewClass() {
    return EditPromotionView.class;
  }
}
