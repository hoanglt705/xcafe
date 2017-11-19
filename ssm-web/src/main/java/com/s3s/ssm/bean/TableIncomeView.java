package com.s3s.ssm.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.model.DualListModel;

import com.s3s.ssm.config.ReportContextProvider;
import com.s3s.ssm.dto.FoodTableDto;
import com.s3s.ssm.dto.report.FoodTableIncomeDto;

@ManagedBean
@ViewScoped
public class TableIncomeView implements Serializable {
  private static final long serialVersionUID = 1L;
  private Date fromDate;
  private Date toDate;
  private List<FoodTableIncomeDto> result;
  private DualListModel<FoodTableDto> dualListModel;

  public Date getFromDate() {
    return fromDate;
  }

  public void setFromDate(Date fromDate) {
    this.fromDate = fromDate;
  }

  public Date getToDate() {
    return toDate;
  }

  public void setToDate(Date toDate) {
    this.toDate = toDate;
  }

  public DualListModel<FoodTableDto> getFoodTables() {
    List<FoodTableDto> sellableProduct = ReportContextProvider.getInstance().getFoodTableService()
            .findAll(0, Integer.MAX_VALUE);
    dualListModel = new DualListModel<FoodTableDto>(new ArrayList<FoodTableDto>(sellableProduct),
            new ArrayList<FoodTableDto>());
    return dualListModel;
  }

  public void setFoodTables(DualListModel<FoodTableDto> dualListModel) {
    this.dualListModel = dualListModel;
  }

  public void search(ActionEvent actionEven) {
    List<String> selectedProducts = new ArrayList<String>();
    for (Object productIncomeDto : dualListModel.getTarget()) {
      selectedProducts.add((String) productIncomeDto);
    }
    result = ReportContextProvider.getInstance().getReportService()
            .statisticFoodTableIncome(selectedProducts,
                    fromDate, toDate);
  }

  public List<FoodTableIncomeDto> getResult() {
    return result;
  }

  public void setResult(List<FoodTableIncomeDto> result) {
    this.result = result;
  }
}
