package com.sunrise.xdoc.entity.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "report_final_period_sale_process")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class FinalPeriodSaleProcess extends AbstractFinalPeriodProcess {
  private static final long serialVersionUID = 1L;
  private Long saleTotal;
  private Long thisYearSaleTotal;
  private Long invoiceTotal;
  private Long thisYearInvoiceTotal;

  @Column(name = "sale_total")
  public Long getSaleTotal() {
    return saleTotal;
  }

  public void setSaleTotal(Long saleTotal) {
    this.saleTotal = saleTotal;
  }

  @Column(name = "this_year_sale_total")
  public Long getThisYearSaleTotal() {
    return thisYearSaleTotal;
  }

  public void setThisYearSaleTotal(Long thisYearSaleTotal) {
    this.thisYearSaleTotal = thisYearSaleTotal;
  }

  @Column(name = "invoice_total")
  public Long getInvoiceTotal() {
    return invoiceTotal;
  }

  public void setInvoiceTotal(Long invoiceTotal) {
    this.invoiceTotal = invoiceTotal;
  }

  public Long getThisYearInvoiceTotal() {
    return thisYearInvoiceTotal;
  }

  public void setThisYearInvoiceTotal(Long thisYearInvoiceTotal) {
    this.thisYearInvoiceTotal = thisYearInvoiceTotal;
  }

}
