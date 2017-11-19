package com.s3s.ssm.view.edit;

public class Problem {
  private Severity severity;
  private String message;

  public Problem(Severity severity, String message) {
    this.severity = severity;
    this.message = message;
  }

  public Severity getSeverity() {
    return severity;
  }

  public void setSeverity(Severity severity) {
    this.severity = severity;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
