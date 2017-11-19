package com.s3s.ssm.view.event;

import com.s3s.ssm.view.controlpanel.TableViewInfoHandler.TableViewInformation;

public class UpdateFoodTableEvent {
  TableViewInformation information;

  public TableViewInformation getTableViewInformation() {
    return information;
  }

  public UpdateFoodTableEvent(TableViewInformation information) {
    this.information = information;
  }

}
