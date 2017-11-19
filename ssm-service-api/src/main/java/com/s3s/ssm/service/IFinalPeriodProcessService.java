package com.s3s.ssm.service;

import com.s3s.ssm.dto.FinalPeriodTableProcessDto;
import com.s3s.ssm.dto.FinalPeriodSaleProcessDto;

public interface IFinalPeriodProcessService {
  FinalPeriodSaleProcessDto findLatestFinalPeriodSaleProcess();

  FinalPeriodTableProcessDto findLatestFinalPeriodTableProcess(String foodTableCode);
}
