package com.s3s.ssm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.dto.AreaDto;
import com.s3s.ssm.service.IConfigService;
import com.sunrise.xdoc.entity.config.Area;

public class ConfigServiceImpl extends AbstractModuleServiceImpl implements IConfigService {

  @Override
  public List<AreaDto> getAllAreas() {
    List<AreaDto> dtoList = new ArrayList<AreaDto>();
    DetachedCriteria dc = getDaoHelper().getDao(Area.class).getCriteria();
    dc.add(Restrictions.eq("active", true));
    List<Area> list = getDaoHelper().getDao(Area.class).findByCriteria(dc);
    for (Area area : list) {
      AreaDto dto = new AreaDto();
      dto.setName(area.getName());
      dtoList.add(dto);
    }
    return dtoList;
  }

  @Override
  @PostConstruct
  public void init() {
  }

  @Override
  public String getName() {
    return "HoangLe";
  }

}
