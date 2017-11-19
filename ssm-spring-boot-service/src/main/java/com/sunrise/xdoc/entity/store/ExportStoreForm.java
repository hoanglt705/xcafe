package com.sunrise.xdoc.entity.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;
import com.sunrise.xdoc.entity.contact.Supplier;
import com.sunrise.xdoc.entity.employee.Employee;

@Entity
@Table(name = "store_export_form")
@Inheritance(strategy = InheritanceType.JOINED)
public class ExportStoreForm extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private Date createdDate = new Date();
  private String description;
  private Employee staff;
  private Long amountTotal;
  private Supplier supplier;
  private List<ImportStoreDetail> importDetails = new ArrayList<ImportStoreDetail>();

  @Column(name = "created_date")
  @NotNull
  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  @Column(name = "description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @ManyToOne
  @JoinColumn(name = "staff_id")
  @NotNull
  public Employee getStaff() {
    return staff;
  }

  public void setStaff(Employee staff) {
    this.staff = staff;
  }

  @Column(name = "amount_total")
  public Long getAmountTotal() {
    return amountTotal;
  }

  public void setAmountTotal(Long amountTotal) {
    this.amountTotal = amountTotal;
  }

  @ManyToOne
  @JoinColumn(name = "supplier_id")
  public Supplier getSupplier() {
    return supplier;
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "importStoreForm")
  @Cascade(value = {org.hibernate.annotations.CascadeType.ALL})
  public List<ImportStoreDetail> getImportDetails() {
    return importDetails;
  }

  public void setImportDetails(List<ImportStoreDetail> importDetails) {
    this.importDetails = importDetails;
  }

}
