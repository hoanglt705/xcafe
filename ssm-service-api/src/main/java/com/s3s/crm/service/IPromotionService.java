package com.s3s.crm.service;

import java.util.Date;

import com.s3s.crm.dto.PromotionDto;
import com.s3s.ssm.service.IViewService;

public interface IPromotionService extends IViewService<PromotionDto> {

  PromotionDto findPromotion(Date date);

}
