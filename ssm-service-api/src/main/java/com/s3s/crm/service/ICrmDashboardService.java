package com.s3s.crm.service;

import java.util.List;

import com.s3s.crm.dto.PotentialCustomerDto;

public interface ICrmDashboardService {
  List<PotentialCustomerDto> findPotentialCustomer();
}
