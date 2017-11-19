package com.s3s.crm.service;

import com.s3s.crm.dto.ConfigDto;
import com.s3s.ssm.service.IViewService;

public interface IConfigService extends IViewService<ConfigDto> {
  ConfigDto getConfig();
}
