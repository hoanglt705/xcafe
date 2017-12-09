package com.s3s.ssm.dto;

import java.util.Date;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class ShiftDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private Date startTime;
  private Date endTime;

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public ShiftDto() {
  }

  public ShiftDto(Long id, String code, String name) {
    this.setId(id);
    setCode(code);
    setName(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
