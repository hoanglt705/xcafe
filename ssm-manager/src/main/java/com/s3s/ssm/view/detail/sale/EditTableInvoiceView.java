package com.s3s.ssm.view.detail.sale;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;

import com.s3s.ssm.config.ManagerContextProvider;
import com.s3s.ssm.dto.EmployeeDto;
import com.s3s.ssm.dto.FoodTableDto;
import com.s3s.ssm.dto.InvoiceDetailDto;
import com.s3s.ssm.dto.InvoiceDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;
import com.s3s.ssm.view.event.DetailEvent;

public class EditTableInvoiceView extends AEditServiceView<InvoiceDto> {
  private static final long serialVersionUID = 1L;
  private JFormattedTextField tfdTotalAmount;
  private JFormattedTextField tfdTotalPaymentAmount;
  private JFormattedTextField tfdDiscount;
  @SuppressWarnings("rawtypes")
  private JComboBox cbxVatTax;
  private JFormattedTextField tfdTotalReturnAmount;

  public EditTableInvoiceView(Map<String, Object> request) {
    super(request);
    AnnotationProcessor.process(this);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    List<EmployeeDto> employees = ManagerContextProvider.getInstance().getEmployeeService()
            .findByActive(0, Integer.MAX_VALUE);
    List<FoodTableDto> foodTables = ManagerContextProvider.getInstance().getFoodTableService()
            .findAll(0, Integer.MAX_VALUE);
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).editable(false).mandatory(true);
    detailDataModel.addAttribute("totalAmount", DetailFieldType.LONG_NUMBER).newColumn().editable(false);
    detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
    detailDataModel.addAttribute("totalPaymentAmount", DetailFieldType.LONG_NUMBER).newColumn();
    detailDataModel.addAttribute("staff", DetailFieldType.DROPDOWN_AUTOCOMPLETE)
            .mandatory(true).setDataList(employees);
    detailDataModel.addAttribute("totalReturnAmount", DetailFieldType.LONG_NUMBER).newColumn();
    detailDataModel.addAttribute("foodTable", DetailFieldType.DROPDOWN_AUTOCOMPLETE)
            .mandatory(true).setDataList(foodTables);
    detailDataModel.addAttribute("discount", DetailFieldType.LONG_NUMBER).newColumn();
    detailDataModel.addRawAttribute("temp", DetailFieldType.BLOCK);
    detailDataModel.addAttribute("vatTax", DetailFieldType.DROPDOWN)
            .newColumn().setDataList(Collections.emptyList());

    detailDataModel.addAttribute("invoiceDetails", DetailFieldType.LIST).componentInfo(
            createInvoiceDetailComponentInfo());
  }

  @Override
  protected InvoiceDto loadForCreate(Map<String, Object> request) {
    InvoiceDto form = super.loadForCreate(request);
    String code = ManagerContextProvider.getInstance().getSequenceNumberService().getInvoiceNextSequence();
    form.setCode(code);
    return form;
  }

  @Override
  protected void beforeSaveOrUpdate(InvoiceDto entity) {
    super.beforeSaveOrUpdate(entity);
    long income = 0l;
    if (entity.getTotalReturnAmount() < 0) {
      income = entity.getTotalReturnAmount();
    } else {
      income = entity.getTotalPaymentAmount() - entity.getTotalReturnAmount();
    }
    entity.setIncome(income);
    // entity.setInvoiceStatus(InvoiceStatus.PAID);
  }

  @Override
  protected void afterSaveOrUpdate(InvoiceDto entity) {
    super.afterSaveOrUpdate(entity);
    ManagerContextProvider.getInstance().getInvoiceService().updateQuantityOfProductInStore(entity);
  }

  @SuppressWarnings("rawtypes")
  @Override
  protected void customizeComponents() {
    tfdTotalAmount = (JFormattedTextField) getComponent("totalAmount");
    tfdTotalPaymentAmount = (JFormattedTextField) getComponent("totalPaymentAmount");
    tfdDiscount = (JFormattedTextField) getComponent("discount");
    cbxVatTax = (JComboBox) getComponent("vatTax");
    tfdTotalReturnAmount = (JFormattedTextField) getComponent("totalReturnAmount");
    tfdTotalPaymentAmount.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        updateTotalReturnAmount();
      }

    });
    tfdDiscount.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        updateTotalReturnAmount();
      }
    });
    cbxVatTax.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        updateTotalReturnAmount();
      }
    });
  }

  private void updateTotalReturnAmount() {
    Long totalAmount = (Long) tfdTotalAmount.getValue();
    Long totalPaymentAmount = (Long) tfdTotalPaymentAmount.getValue();
    Long discount = (Long) tfdDiscount.getValue();
    int vatTax = getVatTax((String) cbxVatTax.getSelectedItem());
    Long vatTaxAmount = (vatTax * totalAmount) / 100;
    Long totalReturnAmount = totalPaymentAmount - (totalAmount - discount - vatTaxAmount);
    tfdTotalReturnAmount.setValue(totalReturnAmount);
  }

  private int getVatTax(String selectedItem) {
    return NumberUtils.toInt(StringUtils.chomp(selectedItem, "%"), 0);
  }

  private IComponentInfo createInvoiceDetailComponentInfo() {
    ListInvoiceDetailComponent component = new ListInvoiceDetailComponent();
    return new ListComponentInfo(component, "invoice");
  }

  @EventSubscriber(eventClass = DetailEvent.class)
  public void subscribeProductTypeEvent(DetailEvent evt) {
    long totalAmount = 0l;
    for (Object object : evt.getDetails()) {
      InvoiceDetailDto detail = (InvoiceDetailDto) object;
      totalAmount += detail.getAmount();
    }
    JFormattedTextField tfdTotalAmount = (JFormattedTextField) getName2AttributeComponent()
            .get("totalAmount").getComponent();
    tfdTotalAmount.setValue(totalAmount);
    updateTotalReturnAmount();
  }

  @Override
  public IViewService<InvoiceDto> getViewService() {
    return ManagerContextProvider.getInstance().getInvoiceService();
  }
}
