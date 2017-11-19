package com.s3s.ssm.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dto.SecurityUserDto;
import com.s3s.ssm.repo.SecurityRoleRepository;
import com.s3s.ssm.repo.SecurityUserRepository;
import com.s3s.ssm.security.entity.SecurityUser;

@Component("securityUserService")
@Transactional
public class SecurityUserServiceImpl implements ISecurityUserService {
  @Autowired
  private SecurityUserRepository securityUserRepository;
  @Autowired
  private SecurityRoleRepository securityRoleRepository;

  @Override
  public void saveOrUpdate(SecurityUserDto dto) {
    SecurityUser employee = null;
    if (dto.getId() != null) {
      employee = securityUserRepository.findOne(dto.getId());
    }
    if (employee == null) {
      employee = new SecurityUser();
    }
    BeanUtils.copyProperties(dto, employee);
    securityUserRepository.save(employee);
  }

  @Override
  public long count() {
    return securityUserRepository.count();
  }

  @Override
  public SecurityUserDto findOne(Long id) {
    if (securityUserRepository.exists(id)) {
      SecurityUser employee = securityUserRepository.findOne(id);
      return transformToDto(employee);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return securityUserRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long employeeId : ids) {
      if (securityUserRepository.exists(employeeId)) {
        SecurityUser employee = securityUserRepository.findOne(employeeId);
        employee.setActive(false);
        securityUserRepository.save(employee);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long employeeId : ids) {
      SecurityUser employee = securityUserRepository.findOne(employeeId);
      employee.setActive(true);
      securityUserRepository.save(employee);
    }

  }

  public static SecurityUserDto transformToDto(SecurityUser employee) {
    SecurityUserDto userDto = new SecurityUserDto();
    BeanUtils.copyProperties(employee, userDto);
    if (employee.getRole() != null) {
      userDto.setRole(SecurityRoleServiceImpl.transformToDto(employee.getRole()));
    }
    return userDto;
  }

  @Override
  public long countByActive() {
    return securityUserRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return securityUserRepository.countByActive(false);
  }

  @Override
  public List<SecurityUserDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(securityUserRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(employee -> transformToDto(employee)).collect(Collectors.toList());
  }

  @Override
  public List<SecurityUserDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(securityUserRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(employee -> transformToDto(employee)).collect(Collectors.toList());
  }

  @Override
  public List<SecurityUserDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(securityUserRepository.findAll(pageRequest).spliterator(), false)
            .map(employee -> transformToDto(employee)).collect(Collectors.toList());
  }

  @Override
  public boolean exits(String username, String password) {
    if (securityUserRepository.countByUsernameAndPassword(username, password) == 1) {
      return true;
    }
    return false;
  }

  @Override
  public SecurityUserDto findUser(String username) {
    SecurityUser user = securityUserRepository.findByUsername(username);
    return transformToDto(user);
  }

  @Override
  public boolean changePassword(String username, String oldPassword, String newPassword) {
    SecurityUser user = securityUserRepository.findByUsername(username);
    if (user == null) {
      return false;
    }
    user.setPassword(newPassword);
    securityUserRepository.save(user);
    return true;
  }
}
