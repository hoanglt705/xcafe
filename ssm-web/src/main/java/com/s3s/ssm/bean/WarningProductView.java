package com.s3s.ssm.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.s3s.ssm.config.ReportContextProvider;
import com.s3s.ssm.dto.WarningProductDto;

@ManagedBean
@ViewScoped
public class WarningProductView implements Serializable {
  private static final long serialVersionUID = 1L;

  public List<WarningProductDto> getWarningProducts() {
    return ReportContextProvider.getInstance().getReportService()
            .statisticWarningProduct(0, Integer.MAX_VALUE);
  }
}
