package com.s3s.ssm.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("cacheDataService")
public class CacheDataServiceImpl implements ICacheDataService {
  private static Log LOGGER = LogFactory.getLog(CacheDataServiceImpl.class);
  private final Map<String, Implementor> implementorMap = new WeakHashMap<String, Implementor>();

  @Override
  public List<?> getReferenceDataList(String cacheId) {
    return implementorMap.get(cacheId).getReferenceDataList();
  }

  @Override
  public Object getReferenceData(String cacheId) {
    return implementorMap.get(cacheId).getReferenceData();
  }

  @Override
  public void registerCache(String cacheId, Object service, Method method) {
    implementorMap.put(cacheId, new Implementor(service, method));
  }

  @Override
  public void refresh() {
    for (Implementor implementor : implementorMap.values()) {
      implementor.refresh();
    }
  }

  private class Implementor {
    private final Object service;
    private final Method method;
    private Object cachedData;

    public Implementor(Object service, Method method) {
      if (method == null) {
        throw new RuntimeException("Method is not register in service=" + service.getClass());
      }
      this.service = service;
      this.method = method;
    }

    public List<?> getReferenceDataList() {
      if (cachedData != null) {
        return (List<?>) cachedData;
      }
      try {
        cachedData = Collections.unmodifiableList((List<?>) method.invoke(service));
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        LOGGER.error("Can not call service to get reference data", e);
        throw new RuntimeException(e);
      }
      return (List<?>) cachedData;
    }

    public Object getReferenceData() {
      if (cachedData != null) {
        return cachedData;
      }
      try {
        cachedData = method.invoke(service);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        LOGGER.error("Can not call service to get reference data", e);
        throw new RuntimeException(e);
      }
      return cachedData;
    }

    public void refresh() {
      if (cachedData != null) {
        cachedData = null;
        if (List.class.isAssignableFrom(method.getReturnType())) {
          getReferenceDataList();
        } else {
          getReferenceData();
        }
      }
    }
  }

}
