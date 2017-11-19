package com.s3s.ssm.util;

import java.lang.reflect.Method;
import java.util.List;

public interface ICacheDataService {
  void registerCache(String cacheId, Object service, Method method);

  List<?> getReferenceDataList(String cacheId);

  Object getReferenceData(String cacheId);

  void refresh();
}
