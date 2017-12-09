package com.s3s.crm.dto;

import java.util.Date;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class PromotionDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String title;
  private String content;
  private Date fromDate;
  private Date toDate;
  private Integer percentDiscount;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getFromDate() {
    return fromDate;
  }

  public void setFromDate(Date fromDate) {
    this.fromDate = fromDate;
  }

  public Date getToDate() {
    return toDate;
  }

  public void setToDate(Date toDate) {
    this.toDate = toDate;
  }

  public Integer getPercentDiscount() {
    return percentDiscount;
  }

  public void setPercentDiscount(Integer percentDiscount) {
    this.percentDiscount = percentDiscount;
  }
}
