package com.sunrise.xdoc.entity.crm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "crm_promotion")
public class Promotion extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private String title;
  private String content;
  private Date fromDate;
  private Date toDate;
  private Integer percentDiscount;

  @Column(name = "title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Lob
  @Column(name = "content")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Column(name = "from_date")
  public Date getFromDate() {
    return fromDate;
  }

  public void setFromDate(Date fromDate) {
    this.fromDate = fromDate;
  }

  @Column(name = "to_date")
  public Date getToDate() {
    return toDate;
  }

  public void setToDate(Date toDate) {
    this.toDate = toDate;
  }

  @Column(name = "percent_discount")
  public Integer getPercentDiscount() {
    return percentDiscount;
  }

  public void setPercentDiscount(Integer percentDiscount) {
    this.percentDiscount = percentDiscount;
  }
}
