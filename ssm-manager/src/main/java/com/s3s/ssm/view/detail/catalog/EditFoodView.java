package com.s3s.ssm.view.detail.catalog;

import java.util.List;
import java.util.Map;

import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.softsmithy.lib.swing.JLongField;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.FoodDto;
import com.s3s.ssm.dto.FoodIngredientDto;
import com.s3s.ssm.dto.ProductTypeDto;
import com.s3s.ssm.dto.UnitOfMeasureDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;
import com.s3s.ssm.view.event.DetailEvent;

public class EditFoodView extends AEditServiceView<FoodDto> {
  private static final long serialVersionUID = 1L;

  public EditFoodView(Map<String, Object> entity) {
    super(entity);
    AnnotationProcessor.process(this);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    List<ProductTypeDto> productTypes = ManagerContextProvider.getInstance().getProductTypeService()
            .findByActive(0, Integer.MAX_VALUE);
    List<UnitOfMeasureDto> uoms = ManagerContextProvider.getInstance().getUnitOfMeasureService()
            .findByActive(0, Integer.MAX_VALUE);
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("unitPrice", DetailFieldType.LONG_NUMBER).mandatory(true).newColumn();
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("sellPrice", DetailFieldType.LONG_NUMBER).mandatory(true).newColumn();
    detailDataModel.addAttribute("productType", DetailFieldType.DROPDOWN).mandatory(true)
            .setDataList(productTypes);
    detailDataModel.addAttribute("uom", DetailFieldType.DROPDOWN).mandatory(true)
            .setDataList(uoms);
    detailDataModel.addAttribute("image", DetailFieldType.IMAGE);
    detailDataModel.addAttribute("foodIngredients", DetailFieldType.LIST).componentInfo(
            createFoodIngredientComponentInfo());
  }

  private IComponentInfo createFoodIngredientComponentInfo() {
    ListFoodIngredientComponent component = new ListFoodIngredientComponent();
    return new ListComponentInfo(component, "food");
  }

  @EventSubscriber(eventClass = DetailEvent.class)
  public void subscribeProductTypeEvent(DetailEvent evt) {
    long unitPrice = 0l;
    for (Object object : evt.getDetails()) {
      FoodIngredientDto ing = (FoodIngredientDto) object;
      unitPrice += ing.getSubPriceTotal();
    }
    JLongField tfdUnitPrice = (JLongField) getName2AttributeComponent().get("unitPrice")
            .getComponent();
    tfdUnitPrice.setLongValue(unitPrice);
  }

  @Override
  public IViewService<FoodDto> getViewService() {
    return ManagerContextProvider.getInstance().getFoodService();
  }
}
