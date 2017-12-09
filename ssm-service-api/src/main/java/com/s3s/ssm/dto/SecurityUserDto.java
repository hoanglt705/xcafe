package com.s3s.ssm.dto;

import com.s3s.ssm.dto.base.AbstractCodeObject;

public class SecurityUserDto extends AbstractCodeObject {
  private static final long serialVersionUID = 370844580866757411L;
  private String username;
  private String password;
  private SecurityRoleDto role;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public SecurityRoleDto getRole() {
    return role;
  }

  public void setRole(SecurityRoleDto role) {
    this.role = role;
  }
}
