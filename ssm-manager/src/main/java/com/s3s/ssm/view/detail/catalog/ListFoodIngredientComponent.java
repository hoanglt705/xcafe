package com.s3s.ssm.view.detail.catalog;

import java.util.List;

import javax.swing.table.TableCellEditor;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.FoodIngredientDto;
import com.s3s.ssm.dto.ImportPriceDetailDto;
import com.s3s.ssm.dto.MaterialDto;
import com.s3s.ssm.dto.UnitOfMeasureDto;
import com.s3s.ssm.view.event.DetailEvent;
import com.s3s.ssm.view.factory.SCellEditorFactory;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListFoodIngredientComponent extends AListComponent<FoodIngredientDto> {
  private static final long serialVersionUID = 9143672291866681219L;

  public ListFoodIngredientComponent() {
    super();
    AnnotationProcessor.process(this);
  }

  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    List<MaterialDto> materials = ManagerContextProvider.getInstance().getMaterialService()
            .findByActive(0, Integer.MAX_VALUE);
    List<UnitOfMeasureDto> uoms = ManagerContextProvider.getInstance().getUnitOfMeasureService()
            .findByActive(0, Integer.MAX_VALUE);
    listDataModel.addColumn("material", ListRendererType.TEXT, ListEditorType.COMBOBOX)
            .setDataList(materials);
    listDataModel.addColumn("uom", ListRendererType.TEXT, ListEditorType.COMBOBOX).setDataList(uoms);
    listDataModel.addColumn("quantity", ListRendererType.NUMBER, ListEditorType.POSITIVE_NUMBER_SPINNER);
    listDataModel.addColumn("unitPrice", ListRendererType.NUMBER, ListEditorType.LONG);
    listDataModel.addColumn("subPriceTotal", ListRendererType.NUMBER, ListEditorType.LONG);
  }

  @Override
  protected void doRowUpdated(String attributeName, FoodIngredientDto entityUpdated) {
    MaterialDto materialDto = entityUpdated.getMaterial();
    if (materialDto != null) {
      if (attributeName.equals("material")) {
        List<UnitOfMeasureDto> uoms = ManagerContextProvider.getInstance().getUnitOfMeasureService()
                .findRelatingUom(materialDto.getUom());
        entityUpdated.setUom(materialDto.getUom());
        updateUomEditor(uoms, materialDto.getUom());
        Long unitPrice = getUnitPriceByUom(materialDto, entityUpdated.getUom());
        entityUpdated.setUnitPrice(unitPrice);
        entityUpdated.setQuantity(1);
        entityUpdated.setSubPriceTotal(unitPrice);
      }
      if (attributeName.equals("uom") && entityUpdated.getUom() != null) {
        Long unitPrice = getUnitPriceByUom(materialDto, entityUpdated.getUom());
        entityUpdated.setUnitPrice(unitPrice);
        entityUpdated.setQuantity(1);
        entityUpdated.setSubPriceTotal(unitPrice);
      }
      if (attributeName.equals("unitPrice") || attributeName.equals("subPriceTotal")
              || attributeName.equals("quantity")) {
        entityUpdated.setSubPriceTotal(entityUpdated.getQuantity() * entityUpdated.getUnitPrice());
      }
      EventBus.publish(new DetailEvent(getEntities()));
    }
  }

  private void updateUomEditor(List<UnitOfMeasureDto> uoms, UnitOfMeasureDto selectedValue) {
    TableCellEditor editor = SCellEditorFactory.createCellEditor(mainTable, ListEditorType.COMBOBOX,
            selectedValue, uoms.toArray(), "uom_TABLE");
    replaceCellEditor(editor, 1);
  }

  public Long getUnitPriceByUom(MaterialDto materialDto, UnitOfMeasureDto uomDto) {
    UnitOfMeasureDto materialUom = materialDto.getUom();
    if (materialUom.equals(uomDto)) {
      return getPriceOfMainSupplier(materialDto);
    }
    return (long) (getPriceOfMainSupplier(materialDto) * (uomDto.getExchangeRate() / materialUom
            .getExchangeRate()));
  }

  private long getPriceOfMainSupplier(MaterialDto materialDto) {
    if (materialDto.getImportPrices().size() == 0) {
      return 0;
    }
    for (ImportPriceDetailDto detailDto : materialDto.getImportPrices()) {
      if (detailDto.isMainSupplier()) {
        return detailDto.getPrice();
      }
    }
    return 0;
  }
}
