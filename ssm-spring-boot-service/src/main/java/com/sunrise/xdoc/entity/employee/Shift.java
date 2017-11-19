package com.sunrise.xdoc.entity.employee;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "emp_shift")
@Inheritance(strategy = InheritanceType.JOINED)
public class Shift extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = -1980337300581609284L;
  private String name;
  private Date startTime;
  private Date endTime;

  @Column(name = "name", unique = true)
  @Length(max = 128)
  @NotNull
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "start_time", nullable = false)
  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  @Column(name = "end_time", nullable = false)
  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  @Override
  public String toString() {
    return getName();
  }

}
