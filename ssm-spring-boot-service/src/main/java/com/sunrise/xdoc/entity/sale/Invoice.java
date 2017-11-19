package com.sunrise.xdoc.entity.sale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.dto.InvoiceStatus;
import com.s3s.ssm.entity.AbstractActiveCodeOLObject;
import com.sunrise.xdoc.entity.config.FoodTable;
import com.sunrise.xdoc.entity.employee.Employee;

@Entity
@Table(name = "sale_invoice")
@Inheritance(strategy = InheritanceType.JOINED)
public class Invoice extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private Date createdDate = new Date();
  private Date endedDate = null;
  private FoodTable foodTable;
  private InvoiceStatus invoiceStatus = InvoiceStatus.BOOKING;
  private Employee staff;
  private Long totalAmount = 0L;
  private Long totalReturnAmount = 0L;
  private Long totalPaymentAmount = 0L;
  private Long discount = 0L;
  private Long vatTax = 0L;
  private Long income;
  private String note;
  private List<InvoiceDetail> invoiceDetails = new ArrayList<InvoiceDetail>();

  @Column(name = "created_date")
  @NotNull
  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  @Column(name = "ended_date")
  public Date getEndedDate() {
    return endedDate;
  }

  public void setEndedDate(Date endedDate) {
    this.endedDate = endedDate;
  }

  @ManyToOne
  @JoinColumn(name = "food_table_id")
  public FoodTable getFoodTable() {
    return foodTable;
  }

  public void setFoodTable(FoodTable foodTable) {
    this.foodTable = foodTable;
  }

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "invoice")
  public List<InvoiceDetail> getInvoiceDetails() {
    return invoiceDetails;
  }

  public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
    this.invoiceDetails = invoiceDetails;
  }

  @Column(name = "invoice_status")
  @NotNull
  @Enumerated(EnumType.STRING)
  public InvoiceStatus getInvoiceStatus() {
    return invoiceStatus;
  }

  public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
    this.invoiceStatus = invoiceStatus;
  }

  @ManyToOne
  @JoinColumn(name = "staff_id")
  public Employee getStaff() {
    return staff;
  }

  public void setStaff(Employee staff) {
    this.staff = staff;
  }

  @Column(name = "total_amount")
  public Long getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Long totalAmount) {
    this.totalAmount = totalAmount;
  }

  @Column(name = "total_payment_amount")
  public Long getTotalPaymentAmount() {
    return totalPaymentAmount;
  }

  public void setTotalPaymentAmount(Long totalPaymentAmount) {
    this.totalPaymentAmount = totalPaymentAmount;
  }

  @Column(name = "discount")
  public Long getDiscount() {
    return discount;
  }

  public void setDiscount(Long discount) {
    this.discount = discount;
  }

  @Column(name = "vat_tax")
  public Long getVatTax() {
    return vatTax;
  }

  public void setVatTax(Long vatTax) {
    this.vatTax = vatTax;
  }

  @Column(name = "note")
  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  @Column(name = "total_return_amount")
  public Long getTotalReturnAmount() {
    return totalReturnAmount;
  }

  public void setTotalReturnAmount(Long totalReturnAmount) {
    this.totalReturnAmount = totalReturnAmount;
  }

  @Column(name = "income")
  public Long getIncome() {
    if (income != null) {
      return income;
    }
    if (getTotalReturnAmount() < 0) {
      income = getTotalReturnAmount();
    } else {
      income = getTotalPaymentAmount() - getTotalReturnAmount();
    }
    return income;
  }

  public void setIncome(Long income) {
    this.income = income;
  }

}
