package com.s3s.crm.view.promotion;

import java.util.Map;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.HTMLEditor;

import javax.swing.JPanel;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.PromotionDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditPromotionView extends AEditServiceView<PromotionDto> {
  private static final long serialVersionUID = 1L;
  HTMLEditor htmlEditor;

  public EditPromotionView(Map<String, Object> entity) {
    super(entity);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("percentDiscount", DetailFieldType.POSITIVE_NUMBER_SPINNER).newColumn();
    detailDataModel.addAttribute("title", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("sent", DetailFieldType.CHECKBOX).editable(false).newColumn();
    detailDataModel.addAttribute("fromDate", DetailFieldType.DATE);
    detailDataModel.addAttribute("toDate", DetailFieldType.DATE);
    detailDataModel.addAttribute("block", DetailFieldType.BLOCK).setRaw(true);
  }

  @Override
  public IViewService<PromotionDto> getViewService() {
    return CrmContextProvider.getInstance().getPromotionService();
  }

  @Override
  protected void addAditionalComponent(JPanel pnlEdit) {
    final JFXPanel chartPanel = new JFXPanel();
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        htmlEditor = new HTMLEditor();
        if (getEntity().getContent() != null) {
          htmlEditor.setHtmlText(getEntity().getContent());
        }
        Scene scene = new Scene(htmlEditor);
        chartPanel.setScene(scene);
      }
    });
    Platform.setImplicitExit(false);
    pnlEdit.add(chartPanel, "newline, spanx, width 800, height 420");
  }

  @Override
  protected void beforeSaveOrUpdate(PromotionDto entity) {
    entity.setContent(htmlEditor.getHtmlText());
  }

}
