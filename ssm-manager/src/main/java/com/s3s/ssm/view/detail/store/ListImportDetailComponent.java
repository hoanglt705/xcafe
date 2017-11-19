package com.s3s.ssm.view.detail.store;

import java.util.List;
import java.util.Optional;

import javax.swing.table.TableCellEditor;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.ImportPriceDetailDto;
import com.s3s.ssm.dto.ImportStoreDetailDto;
import com.s3s.ssm.dto.MaterialDto;
import com.s3s.ssm.dto.SupplierDto;
import com.s3s.ssm.dto.UnitOfMeasureDto;
import com.s3s.ssm.util.UIConstants;
import com.s3s.ssm.view.event.DetailEvent;
import com.s3s.ssm.view.event.SupplierChangeEvent;
import com.s3s.ssm.view.factory.SCellEditorFactory;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListImportDetailComponent extends AListComponent<ImportStoreDetailDto> {
  private static final long serialVersionUID = 9143672291866681219L;

  private SupplierDto currentSupplier;

  public ListImportDetailComponent() {
    super();
    AnnotationProcessor.process(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initialPresentationView(ListDataModel listDataModel) {
    List<MaterialDto> materials = ManagerContextProvider.getInstance().getMaterialService()
            .findByActive(0, Integer.MAX_VALUE);
    List<UnitOfMeasureDto> uoms = ManagerContextProvider.getInstance().getUnitOfMeasureService()
            .findByActive(0, Integer.MAX_VALUE);
    listDataModel.addColumn("material", ListRendererType.TEXT, ListEditorType.COMBOBOX)
            .setDataList(materials);
    listDataModel.addColumn("uom", ListRendererType.TEXT).notEditable().setDataList(uoms);
    listDataModel.addColumn("quantity", ListRendererType.NUMBER, ListEditorType.TEXTFIELD)
            .width(UIConstants.QTY_COLUMN_WIDTH);
    listDataModel.addColumn("importUnitPrice", ListRendererType.NUMBER, ListEditorType.TEXTFIELD)
            .width(UIConstants.AMT_COLUMN_WIDTH);
    listDataModel.addColumn("priceSubtotal", ListRendererType.NUMBER, ListEditorType.TEXTFIELD)
            .width(UIConstants.AMT_COLUMN_WIDTH);
  }

  @EventSubscriber(eventClass = SupplierChangeEvent.class)
  public void subscribeSupplierChangeEvent(SupplierChangeEvent event) {
    currentSupplier = event.getSuppler();
    List<MaterialDto> materials = ManagerContextProvider.getInstance().getMaterialService()
            .findBySupplier(currentSupplier);
    TableCellEditor editor = SCellEditorFactory.createCellEditor(mainTable, ListEditorType.COMBOBOX, null,
            materials.toArray(), "material");
    replaceCellEditor(editor, 0);
    deleteAllRow();
  }

  @Override
  protected void doRowUpdated(String attributeName, ImportStoreDetailDto entityUpdated) {
    if (entityUpdated != null && entityUpdated.getMaterial() != null) {
      if (attributeName.equals("material")) {
        MaterialDto material = entityUpdated.getMaterial();
        entityUpdated.setUom(material.getUom());
        Optional<ImportPriceDetailDto> price1 = material.getImportPrices().stream()
                .filter(price -> price.getSupplier().equals(currentSupplier)).findFirst();
        if (price1.isPresent()) {
          entityUpdated.setImportUnitPrice(price1.get().getPrice());
        } else {
          entityUpdated.setImportUnitPrice(0l);

        }
        entityUpdated.setQuantity(1);
      }
      updatePriceSubtotal(entityUpdated);
      EventBus.publish(new DetailEvent(getEntities()));
    }
  }

  private void updatePriceSubtotal(ImportStoreDetailDto entityUpdated) {
    int quantity = entityUpdated.getQuantity();
    long importUnitPrice = entityUpdated.getImportUnitPrice();
    entityUpdated.setPriceSubtotal(quantity * importUnitPrice);
  }
}
