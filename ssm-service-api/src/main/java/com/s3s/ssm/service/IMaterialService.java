package com.s3s.ssm.service;

import java.util.List;

import com.s3s.ssm.dto.MaterialDto;
import com.s3s.ssm.dto.SupplierDto;

public interface IMaterialService extends IViewService<MaterialDto> {

  List<MaterialDto> findBySupplier(SupplierDto supplierDto);
}
