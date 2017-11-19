package com.s3s.ssm.service;

import java.util.List;

import com.s3s.ssm.dto.ExportStoreFormDto;
import com.s3s.ssm.dto.ImportStoreFormDto;

public interface IStoreService {

  void updateQuantityOfProductInStore(ImportStoreFormDto form);

  void updateQuantityOfProductInStore(ExportStoreFormDto form);

  void recoverProductInStore(ImportStoreFormDto form);

  void recoverProductInStore(List<ImportStoreFormDto> forms);
}
