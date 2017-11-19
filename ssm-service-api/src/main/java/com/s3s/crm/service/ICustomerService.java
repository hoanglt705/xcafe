package com.s3s.crm.service;

import java.util.List;

import com.s3s.crm.dto.CustomerDto;
import com.s3s.crm.dto.LuckyCustomerDto;
import com.s3s.crm.dto.PotentialCustomerDto;
import com.s3s.ssm.service.IViewService;

public interface ICustomerService extends IViewService<CustomerDto> {

  List<LuckyCustomerDto> findLuckyCustomerDto();

  List<PotentialCustomerDto> findPotentialCustomerDto();
}
