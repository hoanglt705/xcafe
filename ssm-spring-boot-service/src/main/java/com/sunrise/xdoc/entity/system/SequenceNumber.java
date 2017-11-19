package com.sunrise.xdoc.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractBaseIdObject;

@Entity
@Table(name = "seq", uniqueConstraints = {@UniqueConstraint(columnNames = {"label", "seq_num"})})
public class SequenceNumber extends AbstractBaseIdObject {
  private static final long serialVersionUID = 1L;
  private String label;
  private Long seqNumber;

  public SequenceNumber() {
    super();
  }

  public SequenceNumber(String label, Long seqNumber) {
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
