package com.s3s.crm.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.s3s.crm.dto.ConfigDto;
import com.s3s.crm.repo.ConfigRepository;
import com.s3s.crm.service.IConfigService;
import com.sunrise.xdoc.entity.crm.Config;

@Component("configService")
@Transactional
public class ConfigServiceImpl implements IConfigService {
  @Autowired
  private ConfigRepository configRepository;

  @Override
  public ConfigDto getConfig() {
    Config config = Lists.newArrayList(configRepository.findAll()).get(0);
    if (config != null) {
      return transformToDto(config);
    }
    return null;
  }

  @Override
  public void saveOrUpdate(ConfigDto dto) {
    Config config = null;
    if (dto.getId() != null) {
      config = configRepository.findOne(dto.getId());
    }
    if (config == null) {
      config = new Config();
    }
    BeanUtils.copyProperties(dto, config);
    configRepository.save(config);
  }

  @Override
  public ConfigDto findOne(Long id) {
    if (configRepository.exists(id)) {
      Config config = configRepository.findOne(id);
      return transformToDto(config);
    }
    return null;
  }

  protected ConfigDto transformToDto(Config config) {
    ConfigDto configDto = new ConfigDto();
    BeanUtils.copyProperties(config, configDto);
    return configDto;
  }

  @Override
  public void inactivate(long[] ids) {
    // TODO Auto-generated method stub

  }

  @Override
  public void activate(long[] ids) {
    // TODO Auto-generated method stub

  }

  @Override
  public long count() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public long countByActive() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public long countByInActive() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean exists(String code) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<ConfigDto> findByActive(int page, int size) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<ConfigDto> findByInactive(int page, int size) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<ConfigDto> findAll(int page, int size) {
    // TODO Auto-generated method stub
    return null;
  }
}
