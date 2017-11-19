package com.s3s.ssm.view.detail.catalog;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.MaterialDto;
import com.s3s.ssm.dto.ProductTypeDto;
import com.s3s.ssm.dto.UnitOfMeasureDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;

public class EditMaterialView extends AEditServiceView<MaterialDto> {
  private static final long serialVersionUID = 1L;
  private static String infoTab = ControlConfigUtils.getString("label.Material.infoTab");
  private static String detailTab = ControlConfigUtils.getString("label.Material.detailTab");

  public EditMaterialView(Map<String, Object> entity) {
    super(entity);
  }

  @SuppressWarnings("unused")
  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    List<ProductTypeDto> productTypes = ManagerContextProvider.getInstance().getProductTypeService()
            .findByActive(0, Integer.MAX_VALUE);
    List<UnitOfMeasureDto> uoms = ManagerContextProvider.getInstance().getUnitOfMeasureService()
            .findByActive(0, Integer.MAX_VALUE);

    detailDataModel.tab(infoTab, infoTab, null);
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
    detailDataModel.addAttribute("image", DetailFieldType.IMAGE);
    detailDataModel.addAttribute("productType", DetailFieldType.DROPDOWN).mandatory(true)
            .setDataList(productTypes);
    detailDataModel.addAttribute("uom", DetailFieldType.DROPDOWN).mandatory(true)
            .setDataList(uoms);
    detailDataModel.addAttribute("retailable", DetailFieldType.CHECKBOX);
    detailDataModel.addAttribute("sellPrice", DetailFieldType.LONG_NUMBER);

    detailDataModel.tab(detailTab, detailTab, null);
    // detailDataModel.addAttribute("beginningQuantityInStock", DetailFieldType.LONG_NUMBER);
    detailDataModel.addAttribute("quantityInStock", DetailFieldType.DOUBLE_NUMBER);
    detailDataModel.addAttribute("minimumQuantity", DetailFieldType.LONG_NUMBER);
    detailDataModel.addAttribute("importPrices", DetailFieldType.LIST).componentInfo(
            createFoodIngredientComponentInfo());
  }

  private IComponentInfo createFoodIngredientComponentInfo() {
    ListImportPriceDetailComponent component = new ListImportPriceDetailComponent();
    return new ListComponentInfo(component, "material");
  }

  @Override
  protected String getDefaultTitle(MaterialDto entity) {
    return entity.getName();
  }

  @Override
  protected void customizeComponents() {
    final JCheckBox ckbRetailable = (JCheckBox) getComponent("retailable");
    final JTextField tfdSellPrice = (JTextField) getComponent("sellPrice");
    ckbRetailable.addItemListener(new ItemListener() {
      @SuppressWarnings("unused")
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (!ckbRetailable.isSelected()) {
          tfdSellPrice.setText("");
          tfdSellPrice.setEnabled(false);
        } else {
          tfdSellPrice.setEnabled(true);
        }
      }
    });
    if (!entity.getRetailable()) {
      tfdSellPrice.setEnabled(false);
    }
  }

  @Override
  public IViewService<MaterialDto> getViewService() {
    return ManagerContextProvider.getInstance().getMaterialService();
  }
}
