package com.s3s.ssm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class SecurityRoleDto extends AbstractCodeObject {
  private static final long serialVersionUID = 1L;
  private String name;
  private String note;
  private boolean settingAuth;

  public SecurityRoleDto() {
  }

  public SecurityRoleDto(Long id, String code, String name) {
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

  public boolean isSettingAuth() {
    return settingAuth;
  }

  public void setSettingAuth(boolean settingAuth) {
    this.settingAuth = settingAuth;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

}
