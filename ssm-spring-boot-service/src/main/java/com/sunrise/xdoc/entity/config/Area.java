package com.sunrise.xdoc.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "config_area")
@Inheritance(strategy = InheritanceType.JOINED)
public class Area extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private String name;

  @Column(name = "name", unique = true, nullable = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }

}
