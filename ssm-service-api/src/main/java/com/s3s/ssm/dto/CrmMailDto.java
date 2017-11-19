package com.s3s.ssm.dto;

public class CrmMailDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String subject;
  private String htmlMessage;

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getHtmlMessage() {
    return htmlMessage;
  }

  public void setHtmlMessage(String htmlMessage) {
    this.htmlMessage = htmlMessage;
  }

}
