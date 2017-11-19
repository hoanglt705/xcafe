package com.sunrise.xdoc.entity.crm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "crm_mail")
public class CrmMail extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private String subject;
  private String htmlMessage;

  @Column(name = "subject")
  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  @Column(name = "htmlMessage")
  public String getHtmlMessage() {
    return htmlMessage;
  }

  public void setHtmlMessage(String htmlMessage) {
    this.htmlMessage = htmlMessage;
  }

}
