package com.sunrise.xdoc.entity.crm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "crm_customer_card")
public class CustomerCard extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private Date createdDate;
  private Customer customer;

  @Column(name = "createdDate")
  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  @ManyToOne
  @JoinColumn(name = "customer_id")
  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

}
