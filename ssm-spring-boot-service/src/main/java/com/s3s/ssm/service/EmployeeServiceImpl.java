package com.s3s.ssm.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dto.EmployeeDto;
import com.s3s.ssm.dto.ShiftDto;
import com.s3s.ssm.repo.EmployeeRepository;
import com.s3s.ssm.repo.RoleRepository;
import com.s3s.ssm.repo.ShiftRepository;
import com.sunrise.xdoc.entity.employee.Employee;
import com.sunrise.xdoc.entity.employee.Shift;

@Component("employeeService")
@Transactional
public class EmployeeServiceImpl implements IEmployeeService {
  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private ShiftRepository shiftRepository;

  @Override
  public void saveOrUpdate(EmployeeDto dto) {
    Employee employee = null;
    if (dto.getId() != null) {
      employee = employeeRepository.findOne(dto.getId());
    }
    if (employee == null) {
      employee = new Employee();
    }
    BeanUtils.copyProperties(dto, employee);
    employee.setRole(roleRepository.findByCode(dto.getRole().getCode()));
    employee.getShifts().clear();
    for (ShiftDto shiftDto : dto.getShifts()) {
      Shift newShift = shiftRepository.findOne(shiftDto.getId());
      employee.getShifts().add(newShift);
    }
    employeeRepository.save(employee);
  }

  @Override
  public long count() {
    return employeeRepository.count();
  }

  @Override
  public EmployeeDto findOne(Long id) {
    if (employeeRepository.exists(id)) {
      Employee employee = employeeRepository.findOne(id);
      return transformToDto(employee);
    }
    return null;
  }

  @Override
  public boolean exists(String code) {
    return employeeRepository.findByCode(code) != null;
  }

  @Override
  public void inactivate(long[] ids) {
    for (long employeeId : ids) {
      if (employeeRepository.exists(employeeId)) {
        Employee employee = employeeRepository.findOne(employeeId);
        employee.setActive(false);
        employeeRepository.save(employee);
      }
    }
  }

  @Override
  public void activate(long[] ids) {
    for (long employeeId : ids) {
      Employee employee = employeeRepository.findOne(employeeId);
      employee.setActive(true);
      employeeRepository.save(employee);
    }

  }

  public static EmployeeDto transformToDto(Employee employee) {
    EmployeeDto employeeDto = new EmployeeDto();
    BeanUtils.copyProperties(employee, employeeDto);
    if (employee.getRole() != null) {
      employeeDto.setRole(RoleServiceImpl.transformToDto(employee.getRole()));
    }
    for (Shift shift : employee.getShifts()) {
      ShiftDto shiftDto = ShiftServiceImpl.transformToDto(shift);
      employeeDto.getShifts().add(shiftDto);
    }
    return employeeDto;
  }

  @Override
  public long countByActive() {
    return employeeRepository.countByActive(true);
  }

  @Override
  public long countByInActive() {
    return employeeRepository.countByActive(false);
  }

  @Override
  public List<EmployeeDto> findByActive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(employeeRepository.findByActive(true, pageRequest).spliterator(), false)
            .map(employee -> transformToDto(employee)).collect(Collectors.toList());
  }

  @Override
  public List<EmployeeDto> findByInactive(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(employeeRepository.findByActive(false, pageRequest).spliterator(), false)
            .map(employee -> transformToDto(employee)).collect(Collectors.toList());
  }

  @Override
  public List<EmployeeDto> findAll(int page, int size) {
    PageRequest pageRequest = new PageRequest(page, size);
    return StreamSupport.stream(employeeRepository.findAll(pageRequest).spliterator(), false)
            .map(employee -> transformToDto(employee)).collect(Collectors.toList());
  }
}
