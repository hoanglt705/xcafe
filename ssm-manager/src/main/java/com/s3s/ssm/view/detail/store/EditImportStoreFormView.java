package com.s3s.ssm.view.detail.store;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.softsmithy.lib.swing.JLongField;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.EmployeeDto;
import com.s3s.ssm.dto.ImportStoreDetailDto;
import com.s3s.ssm.dto.ImportStoreFormDto;
import com.s3s.ssm.dto.SupplierDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;
import com.s3s.ssm.view.event.DetailEvent;
import com.s3s.ssm.view.event.SupplierChangeEvent;

public class EditImportStoreFormView extends AEditServiceView<ImportStoreFormDto> {
  private static final long serialVersionUID = 1L;

  public EditImportStoreFormView(Map<String, Object> request) {
    super(request);
    AnnotationProcessor.process(this);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    List<SupplierDto> suppliers = ManagerContextProvider.getInstance().getSupplierService()
            .findByActive(0, Integer.MAX_VALUE);
    List<EmployeeDto> staffs = ManagerContextProvider.getInstance().getEmployeeService()
            .findByActive(0, Integer.MAX_VALUE);
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).editable(false).mandatory(true);
    detailDataModel.addAttribute("amountTotal", DetailFieldType.LONG_NUMBER).newColumn();
    detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
    detailDataModel.addAttribute("quantityTotal", DetailFieldType.LONG_NUMBER).newColumn();
    detailDataModel.addAttribute("staff", DetailFieldType.DROPDOWN_AUTOCOMPLETE).mandatory(true)
            .setDataList(staffs);
    detailDataModel.addAttribute("supplier", DetailFieldType.DROPDOWN_AUTOCOMPLETE).setDataList(suppliers);
    detailDataModel.addAttribute("importDetails", DetailFieldType.LIST).componentInfo(
            createImportDetailsComponentInfo());
  }

  private IComponentInfo createImportDetailsComponentInfo() {
    ListImportDetailComponent listImportDetailComponent = new ListImportDetailComponent();
    return new ListComponentInfo(listImportDetailComponent, "importStoreForm");
  }

  @Override
  protected void afterSaveOrUpdate(ImportStoreFormDto entity) {
    // ManagerContextProvider.getInstance().getStoreService().updateQuantityOfProductInStore(entity);
  }

  @Override
  protected ImportStoreFormDto loadForCreate(Map<String, Object> request) {
    ImportStoreFormDto form = super.loadForCreate(request);
    String code = ManagerContextProvider.getInstance().getSequenceNumberService()
            .getImportStoreFormNextSequence();
    form.setCode(code);
    return form;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void customizeComponents() {
    final JComboBox<SupplierDto> cbxSupplier = (JComboBox<SupplierDto>) getComponent("supplier");
    cbxSupplier.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        EventBus.publish(new SupplierChangeEvent((SupplierDto) cbxSupplier.getSelectedItem()));
        JLongField tfdTotalAmount = (JLongField) getName2AttributeComponent()
                .get("amountTotal").getComponent();
        JLongField tfdTotalQuantity = (JLongField) getName2AttributeComponent()
                .get("quantityTotal").getComponent();
        tfdTotalAmount.setLongValue(0);
        tfdTotalQuantity.setLongValue(0);
      }
    });
  }

  @EventSubscriber(eventClass = DetailEvent.class)
  public void subscribeProductTypeEvent(DetailEvent evt) {
    long totalAmount = 0l;
    long totalQuantity = 0l;
    for (Object object : evt.getDetails()) {
      ImportStoreDetailDto detail = (ImportStoreDetailDto) object;
      totalAmount += detail.getPriceSubtotal();
      totalQuantity += detail.getQuantity();
    }
    JLongField tfdTotalAmount = (JLongField) getName2AttributeComponent()
            .get("amountTotal").getComponent();
    JLongField tfdTotalQuantity = (JLongField) getName2AttributeComponent()
            .get("quantityTotal").getComponent();
    tfdTotalAmount.setLongValue(totalAmount);
    tfdTotalQuantity.setLongValue(totalQuantity);
  }

  @Override
  public IViewService<ImportStoreFormDto> getViewService() {
    return ManagerContextProvider.getInstance().getImportStoreFormService();
  }
}
