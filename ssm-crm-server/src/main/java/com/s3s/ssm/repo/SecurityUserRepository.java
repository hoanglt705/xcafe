package com.s3s.ssm.repo;

import org.springframework.data.repository.query.Param;

import com.s3s.ssm.security.entity.SecurityUser;

public interface SecurityUserRepository extends CommonRepository<SecurityUser, Long> {
  long countByUsernameAndPassword(@Param(value = "username") String username,
          @Param(value = "password") String password);

  SecurityUser findByUsername(@Param(value = "username") String username);
}
