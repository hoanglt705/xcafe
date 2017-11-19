package com.sunrise.xdoc.entity.crm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "crm_materia_type")
public class MaterialType extends AbstractActiveCodeOLObject {
  private static final long serialVersionUID = 1L;
  private String name;

  @Column(name = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
