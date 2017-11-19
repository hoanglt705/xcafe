package com.s3s.ssm.service;

import java.util.Date;
import java.util.List;

import com.s3s.ssm.dto.InvoiceDto;

public interface ITimelineService {
  List<InvoiceDto> getInvoice(Date fromDate, Date toDate);
}
