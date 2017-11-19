package com.s3s.ssm.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.s3s.ssm.config.PosContextProvider;
import com.s3s.ssm.dto.AreaDto;
import com.s3s.ssm.dto.FoodTableDto;

@ManagedBean
@ViewScoped
public class OverviewView {
  private static final PosContextProvider posContextProvider = PosContextProvider.getInstance();
  private Map<AreaDto, List<FoodTableDto>> areaMap;
  private List<AreaDto> areas;

  @PostConstruct
  public void init() {
    setAreas(posContextProvider.getAreaService().findByActive(0, Integer.MAX_VALUE));
    setAreaMap(new HashMap<>());
    for (AreaDto areaDto : getAreas()) {
      List<FoodTableDto> footTables = posContextProvider.getFoodTableService().findAllSameArea(
              areaDto.getCode());
      getAreaMap().put(areaDto, footTables);
    }
  }

  public Map<AreaDto, List<FoodTableDto>> getAreaMap() {
    return areaMap;
  }

  public void setAreaMap(Map<AreaDto, List<FoodTableDto>> areaMap) {
    this.areaMap = areaMap;
  }

  public List<AreaDto> getAreas() {
    return areas;
  }

  public void setAreas(List<AreaDto> areas) {
    this.areas = areas;
  }

}
