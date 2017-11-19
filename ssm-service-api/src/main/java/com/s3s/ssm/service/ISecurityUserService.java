package com.s3s.ssm.service;

import com.s3s.ssm.dto.SecurityUserDto;

public interface ISecurityUserService extends IViewService<SecurityUserDto> {
  boolean exits(String username, String password);

  SecurityUserDto findUser(String username);

  boolean changePassword(String username, String oldPassword, String newPassword);
}
