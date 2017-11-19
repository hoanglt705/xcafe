package com.sunrise.xdoc.entity.crm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractBaseIdObject;

@Entity
@Table(name = "crm_seq", uniqueConstraints = {@UniqueConstraint(columnNames = {"label", "seq_num"})})
public class CrmSequenceNumber extends AbstractBaseIdObject {
  private static final long serialVersionUID = 1L;
  private String label;
  private Long seqNumber;

  public CrmSequenceNumber() {
    super();
  }

  public CrmSequenceNumber(String label, Long seqNumber) {
    super();
    this.label = label;
    this.seqNumber = seqNumber;
  }

  @Column(name = "label")
  @NotBlank()
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @Column(name = "seq_num")
  public Long getSeqNumber() {
    return seqNumber;
  }

  public void setSeqNumber(Long seqNumber) {
    this.seqNumber = seqNumber;
  }

}
