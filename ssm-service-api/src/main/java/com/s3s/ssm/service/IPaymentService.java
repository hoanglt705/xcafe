package com.s3s.ssm.service;

import java.util.List;

import com.s3s.ssm.dto.PaymentDto;

public interface IPaymentService extends IViewService<PaymentDto> {
  List<PaymentDto> getLatestPayment(int size);
}
