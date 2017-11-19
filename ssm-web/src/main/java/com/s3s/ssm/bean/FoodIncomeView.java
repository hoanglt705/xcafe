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
import com.s3s.ssm.dto.ProductDto;
import com.s3s.ssm.dto.report.ProductIncomeDto;

@ManagedBean
@ViewScoped
public class FoodIncomeView implements Serializable {
  private static final long serialVersionUID = 1L;
  private Date fromDate;
  private Date toDate;
  private List<ProductIncomeDto> result;
  private DualListModel<ProductDto> dualListModel;

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

  public DualListModel<ProductDto> getSellableProducts() {
    List<ProductDto> sellableProduct = ReportContextProvider.getInstance().getProductService()
            .getSellableProduct();
    dualListModel = new DualListModel<ProductDto>(new ArrayList<ProductDto>(sellableProduct),
            new ArrayList<ProductDto>());
    return dualListModel;
  }

  public void setSellableProducts(DualListModel<ProductDto> dualListModel) {
    this.dualListModel = dualListModel;
  }

  public void search(ActionEvent actionEven) {
    List<String> selectedProducts = new ArrayList<String>();
    for (Object productIncomeDto : dualListModel.getTarget()) {
      selectedProducts.add((String) productIncomeDto);
    }
    result = ReportContextProvider.getInstance().getReportService().statisticProductIncome(selectedProducts,
            fromDate, toDate);
  }

  public List<ProductIncomeDto> getResult() {
    return result;
  }

  public void setResult(List<ProductIncomeDto> result) {
    this.result = result;
  }
}
