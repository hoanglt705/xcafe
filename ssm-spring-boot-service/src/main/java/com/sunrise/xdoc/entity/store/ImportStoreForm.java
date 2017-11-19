package com.sunrise.xdoc.entity.store;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;
import com.sunrise.xdoc.entity.contact.Supplier;
import com.sunrise.xdoc.entity.employee.Employee;

@Entity
@Table(name = "store_import_form")
@Inheritance(strategy = InheritanceType.JOINED)
public class ImportStoreForm extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private Date createdDate = new Date();
  private String description;
  private Employee staff;
  private Long amountTotal;
  private Long quantityTotal;
  private Supplier supplier;
  private Set<ImportStoreDetail> importDetails = new HashSet<ImportStoreDetail>();

  @Column(name = "created_date", nullable = false)
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
  @JoinColumn(name = "staff_id", nullable = false)
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

  @Column(name = "quantity_total")
  public Long getQuantityTotal() {
    return quantityTotal;
  }

  public void setQuantityTotal(Long quantityTotal) {
    this.quantityTotal = quantityTotal;
  }

  @ManyToOne
  @JoinColumn(name = "supplier_id")
  public Supplier getSupplier() {
    return supplier;
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "importStoreForm", orphanRemoval = true)
  public Set<ImportStoreDetail> getImportDetails() {
    return importDetails;
  }

  public void setImportDetails(Set<ImportStoreDetail> importDetails) {
    this.importDetails = importDetails;
  }

}
