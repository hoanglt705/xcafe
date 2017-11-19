package com.s3s.ssm.view.registry;

import com.s3s.ssm.service.AbstractViewService;

public class RegistryServiceImpl extends AbstractViewService<RegistryDto> {

  @SuppressWarnings("unused")
  @Override
  public RegistryDto findOne(Long id) {
    return new RegistryDto();
  }
}
