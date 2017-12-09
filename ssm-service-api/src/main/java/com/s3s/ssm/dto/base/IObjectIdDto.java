package com.s3s.ssm.dto.base;

import java.io.Serializable;

public class IObjectIdDto implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
