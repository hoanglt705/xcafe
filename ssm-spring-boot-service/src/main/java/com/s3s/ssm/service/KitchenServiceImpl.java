package com.s3s.ssm.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.s3s.ssm.dto.WaitingFoodDto;

public class KitchenServiceImpl implements IKitchenService {
  private static Map<String, WaitingFoodDto> operatingFoodTable = new WeakHashMap<>();

  @Override
  public void put(List<WaitingFoodDto> dtos) {
    for (WaitingFoodDto dto : dtos) {
      operatingFoodTable.put(dto.getFoodName(), dto);
    }
  }

  @Override
  public Collection<WaitingFoodDto> getAllWaitingFood() {
    return operatingFoodTable.values();
  }
}
