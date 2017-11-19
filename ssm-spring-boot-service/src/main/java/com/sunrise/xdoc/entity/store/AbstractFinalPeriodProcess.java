package com.sunrise.xdoc.entity.store;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.s3s.ssm.entity.AbstractIdOLObject;

@MappedSuperclass
public class AbstractFinalPeriodProcess extends AbstractIdOLObject {
  private static final long serialVersionUID = 1L;
  private Date processingDate = new Date();
  private Long totalAmount;

  @Column(name = "processing_date")
  public Date getProcessingDate() {
    return processingDate;
  }

  public void setProcessingDate(Date processingDate) {
    this.processingDate = processingDate;
  }

  @Column(name = "total_amount")
  public Long getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Long totalAmount) {
    this.totalAmount = totalAmount;
  }
}
