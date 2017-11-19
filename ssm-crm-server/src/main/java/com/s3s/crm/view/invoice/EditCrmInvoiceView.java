package com.s3s.crm.view.invoice;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.softsmithy.lib.swing.JLongField;

import com.s3s.crm.config.CrmContextProvider;
import com.s3s.crm.dto.CrmInvoiceDetailDto;
import com.s3s.crm.dto.CrmInvoiceDto;
import com.s3s.crm.dto.CustomerDto;
import com.s3s.crm.dto.CustomerTypeDto;
import com.s3s.crm.dto.PromotionDto;
import com.s3s.ssm.service.IViewService;
import com.s3s.ssm.view.AbstractView;
import com.s3s.ssm.view.detail.config.AEditServiceView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;
import com.s3s.ssm.view.event.DetailEvent;
import com.s3s.ssm.widget.JVLongField;
import com.s3s.ssm.widget.VDateSpinner;

public class EditCrmInvoiceView extends AEditServiceView<CrmInvoiceDto> {
  private static final long serialVersionUID = 1L;

  public EditCrmInvoiceView(Map<String, Object> request) {
    super(request);
    AnnotationProcessor.process(this);
  }

  @Override
  protected void initialPresentationView(DetailDataModel detailDataModel) {
    List<CustomerDto> customer = CrmContextProvider.getInstance().getCustomerService()
            .findByActive(0, Integer.MAX_VALUE);
    detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).editable(false).mandatory(true);
    detailDataModel.addAttribute("totalAmount", DetailFieldType.LONG_NUMBER).newColumn();
    detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
    detailDataModel.addAttribute("discountAmount", DetailFieldType.LONG_NUMBER).newColumn();
    detailDataModel.addAttribute("customer", DetailFieldType.DROPDOWN_AUTOCOMPLETE).setDataList(customer);
    detailDataModel.addAttribute("vatPercent", DetailFieldType.DROPDOWN_AUTOCOMPLETE).newColumn()
            .setRaw(true).setDataList(Arrays.asList("5%", "10%", "15%"));
    detailDataModel.addAttribute("customerType", DetailFieldType.LABEL).setRaw(true);
    detailDataModel.addAttribute("paymentAmount", DetailFieldType.LONG_NUMBER).newColumn();
    detailDataModel.addAttribute("promotion", DetailFieldType.LABEL).setRaw(true);
    detailDataModel.addAttribute("borrowCard", DetailFieldType.CHECKBOX);
    detailDataModel.addAttribute("invoiceDetails", DetailFieldType.LIST).componentInfo(
            createInvoiceDetailComponentInfo()).width(800);
  }

  private IComponentInfo createInvoiceDetailComponentInfo() {
    ListCrmInvoiceDetailComponent component = new ListCrmInvoiceDetailComponent();
    return new ListComponentInfo(component, "invoice");
  }

  @Override
  protected void beforeSaveOrUpdate(CrmInvoiceDto entity) {
    super.beforeSaveOrUpdate(entity);
    entity.setVatPercent(getVatPercent());
  }

  @Override
  protected CrmInvoiceDto loadForCreate(Map<String, Object> request) {
    CrmInvoiceDto form = super.loadForCreate(request);
    String code = CrmContextProvider.getInstance().getCrmSequenceNumberService().getInvoiceNextSequence();
    form.setCode(code);
    return form;
  }

  @Override
  public IViewService<CrmInvoiceDto> getViewService() {
    return CrmContextProvider.getInstance().getCrmInvoiceService();
  }

  @SuppressWarnings("rawtypes")
  @Override
  protected void customizeComponents() {
    JComboBox cbxCustomer = (JComboBox) getComponent("customer");
    cbxCustomer.addItemListener(e -> {
      CustomerDto selectedCustomer = (CustomerDto) cbxCustomer.getSelectedItem();
      CustomerTypeDto customerType = null;
      if (selectedCustomer != null) {
        customerType = selectedCustomer.getCustomerType();
      }
      generateCustomerTypeLabel(customerType);

      JLongField tfdDiscountAmount = (JLongField) getComponent("discountAmount");
      long discountAmount = 0;
      if (selectedCustomer != null) {
        CustomerTypeDto customerTypeDto = selectedCustomer.getCustomerType();
        if (customerTypeDto != null) {
          JLongField tfdTotalAmount = (JLongField) getComponent("totalAmount");
          long totalAmount = tfdTotalAmount.getLongValue();
          discountAmount = (totalAmount * customerTypeDto.getDiscountPercent()) / 100;
        }
      }
      tfdDiscountAmount.setLongValue(discountAmount);
      updatePaymentAmount();
    });
    if (request.get(AbstractView.PARAM_ACTION) == EditActionEnum.NEW) {
      generateCustomerTypeLabel(null);
    } else {
      CustomerDto customerDto = getEntity().getCustomer();
      if (customerDto != null) {
        CustomerTypeDto customerTypeDto = customerDto.getCustomerType();
        generateCustomerTypeLabel(customerTypeDto);
      }
    }

    // handle promotion
    PromotionDto promotionDto = getPromotion();
    JLabel lblPromotion = (JLabel) getComponent("promotion");
    if (promotionDto != null) {
      lblPromotion.setText(promotionDto.getTitle() + "(" + promotionDto.getPercentDiscount() + "%)");
    } else {
      lblPromotion.setText("");
    }

    JComboBox cbxVat = (JComboBox) getComponent("vatPercent");
    cbxVat.addItemListener(e -> {
      updatePaymentAmount();
    });
  }

  private PromotionDto getPromotion() {
    VDateSpinner createdDateSpinner = (VDateSpinner) getComponent("createdDate");
    Date createdDate = createdDateSpinner.getDate();
    PromotionDto promotionDto = CrmContextProvider.getInstance().getPromotionService()
            .findPromotion(createdDate);
    return promotionDto;
  }

  private void updatePaymentAmount() {
    JLongField tfdTotalAmount = (JLongField) getComponent("totalAmount");
    JLongField tfdpaymentAmount = (JLongField) getComponent("paymentAmount");
    JLongField tfdDiscountAmount = (JLongField) getComponent("discountAmount");
    int vatPercent = getVatPercent();
    long totalAmount = tfdTotalAmount.getLongValue();
    long totalDiscountAmount = calculateTotalDiscountAmount();
    long vatAmount = (totalAmount - totalDiscountAmount) * vatPercent / 100;
    tfdpaymentAmount.setLongValue(totalAmount - totalDiscountAmount + vatAmount);
    tfdDiscountAmount.setLongValue(totalDiscountAmount);
  }

  private long calculateTotalDiscountAmount() {
    JLongField tfdTotalAmount = (JLongField) getComponent("totalAmount");
    long totalAmount = tfdTotalAmount.getLongValue();
    long customerDiscount = 0l;
    JComboBox cbxCustomer = (JComboBox) getComponent("customer");
    CustomerDto selectedCustomer = (CustomerDto) cbxCustomer.getSelectedItem();
    CustomerTypeDto customerType = null;
    if (selectedCustomer != null) {
      customerType = selectedCustomer.getCustomerType();
    }

    if (selectedCustomer != null) {
      CustomerTypeDto customerTypeDto = selectedCustomer.getCustomerType();
      if (customerTypeDto != null) {
        customerDiscount = (totalAmount * customerTypeDto.getDiscountPercent()) / 100;
      }
    }

    Integer promotionPercentDiscount = getPromotion().getPercentDiscount();
    long promotionDiscount = totalAmount * promotionPercentDiscount / 100;
    return customerDiscount + promotionDiscount;
  }

  @SuppressWarnings("rawtypes")
  private int getVatPercent() {
    JComboBox cbxVat = (JComboBox) getComponent("vatPercent");
    String selectedItem = (String) cbxVat.getSelectedItem();
    if (selectedItem == null) {
      return 0;
    }
    selectedItem = StringUtils.remove(selectedItem, "%");
    return NumberUtils.toInt(selectedItem);
  }

  private void generateCustomerTypeLabel(CustomerTypeDto customerType) {
    JLabel lblCustomerType = (JLabel) getComponent("customerType");
    if (customerType != null) {
      lblCustomerType.setText(customerType.getName() + "(" + customerType.getDiscountPercent() + "%)");
    } else {
      lblCustomerType.setText("");
    }
  }

  @EventSubscriber(eventClass = DetailEvent.class)
  public void subscribeProductTypeEvent(DetailEvent evt) {
    JVLongField tfdTotalAmount = (JVLongField) getName2AttributeComponent().get("totalAmount").getComponent();
    long totalAmount = tfdTotalAmount.getLongValue();
    for (Object object : evt.getDetails()) {
      CrmInvoiceDetailDto detail = (CrmInvoiceDetailDto) object;
      totalAmount += detail.getAmount();
    }
    tfdTotalAmount.setLongValue(totalAmount);
    updatePaymentAmount();
  }
}
